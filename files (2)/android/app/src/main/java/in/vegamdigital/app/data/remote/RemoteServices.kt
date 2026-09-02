package `in`.vegamdigital.app.data.remote

import android.content.Context
import com.google.gson.annotations.SerializedName
import `in`.vegamdigital.app.BuildConfig
import `in`.vegamdigital.app.domain.model.Answer
import `in`.vegamdigital.app.domain.model.Doubt
import `in`.vegamdigital.app.domain.model.Job
import `in`.vegamdigital.app.domain.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

data class PasswordRequest(val email: String, val password: String)
data class RefreshRequest(@SerializedName("refresh_token") val refreshToken: String)
data class AuthUser(val id: String)
data class AuthResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    val user: AuthUser
)

data class ProfileDto(
    val id: String,
    @SerializedName("student_code") val studentCode: String,
    @SerializedName("full_name") val fullName: String,
    val course: String,
    val branch: String,
    val batch: String,
    @SerializedName("roll_number") val rollNumber: String,
    val location: String,
    @SerializedName("is_admin") val isAdmin: Boolean = false
)

data class CreateStudentRequest(
    val email: String,
    val password: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("student_code") val studentCode: String,
    val course: String,
    val branch: String,
    val batch: String,
    @SerializedName("roll_number") val rollNumber: String,
    val location: String
)

data class AnswerDto(
    val id: Long,
    @SerializedName("author_name") val authorName: String,
    val body: String,
    @SerializedName("created_at") val createdAt: String? = null
)

data class DoubtDto(
    val id: Long,
    val question: String,
    val description: String? = null,
    @SerializedName("author_name") val authorName: String,
    @SerializedName("created_at") val createdAt: String? = null,
    val answers: List<AnswerDto> = emptyList()
)

data class NewDoubt(
    @SerializedName("student_id") val studentId: String,
    @SerializedName("author_name") val authorName: String,
    val question: String,
    val description: String
)

data class NewAnswer(
    @SerializedName("doubt_id") val doubtId: Long,
    @SerializedName("student_id") val studentId: String,
    @SerializedName("author_name") val authorName: String,
    val body: String
)

data class NewJob(
    @SerializedName("student_id") val studentId: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val experience: String,
    val description: String,
    @SerializedName("contact_name") val contactName: String,
    val phone: String
)

data class NewReferral(
    @SerializedName("student_id") val studentId: String,
    val name: String,
    val phone: String,
    val note: String
)

data class AdminLogDto(
    @SerializedName("student_code") val studentCode: String,
    @SerializedName("full_name") val fullName: String,
    val password: String,
    val batch: String,
    @SerializedName("admin_id") val adminId: String? = null,
    @SerializedName("created_at") val createdAt: String? = null
)

interface SupabaseApi {
    @POST("auth/v1/token")
    suspend fun signIn(@Query("grant_type") grantType: String = "password", @Body body: PasswordRequest): AuthResponse

    @POST("auth/v1/token")
    suspend fun refresh(@Query("grant_type") grantType: String = "refresh_token", @Body body: RefreshRequest): AuthResponse

    @POST("auth/v1/logout") suspend fun signOut()

    @GET("rest/v1/profiles")
    suspend fun profile(@Query("id") id: String, @Query("select") select: String = "*"): List<ProfileDto>

    @GET("rest/v1/doubts")
    suspend fun doubts(
        @Query("select") select: String = "*,answers(*)",
        @Query("order") order: String = "created_at.desc",
        @Query("answers.order") answersOrder: String = "created_at.asc"
    ): List<DoubtDto>

    @POST("rest/v1/doubts") suspend fun addDoubt(@Body body: NewDoubt)
    @POST("rest/v1/answers") suspend fun addAnswer(@Body body: NewAnswer)
    @POST("rest/v1/jobs") suspend fun addJob(@Body body: NewJob)
    @POST("rest/v1/referrals") suspend fun addReferral(@Body body: NewReferral)
    @POST("rest/v1/admin_logs") suspend fun addAdminLog(@Body body: AdminLogDto)
    @GET("rest/v1/admin_logs")
    suspend fun adminLogs(
        @Query("select") select: String = "student_code,full_name,password,batch,admin_id,created_at",
        @Query("order") order: String = "created_at.desc"
    ): List<AdminLogDto>

    @POST("functions/v1/create-student")
    suspend fun createStudent(@Body body: CreateStudentRequest)
}

@Singleton
class SupabaseSessionStore @Inject constructor(context: Context) {
    private val preferences = context.getSharedPreferences("supabase_session", Context.MODE_PRIVATE)
    private val _signedIn = MutableStateFlow(!preferences.getString(ACCESS_TOKEN, null).isNullOrBlank())
    val signedIn: StateFlow<Boolean> = _signedIn

    val accessToken: String? get() = preferences.getString(ACCESS_TOKEN, null)
    val refreshToken: String? get() = preferences.getString(REFRESH_TOKEN, null)
    val userId: String? get() = preferences.getString(USER_ID, null)

    fun save(auth: AuthResponse) {
        preferences.edit()
            .putString(ACCESS_TOKEN, auth.accessToken)
            .putString(REFRESH_TOKEN, auth.refreshToken)
            .putString(USER_ID, auth.user.id)
            .apply()
        _signedIn.value = true
    }

    fun clear() {
        preferences.edit { clear() }
        _signedIn.value = false
    }

    private companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_ID = "user_id"
    }
}

@Singleton
class SupabaseGateway @Inject constructor(
    private val api: SupabaseApi,
    private val session: SupabaseSessionStore
) {
    val signedIn: StateFlow<Boolean> = session.signedIn
    private val refreshMutex = Mutex()

    suspend fun signIn(studentCode: String, password: String): Student {
        checkConfigured()
        val code = studentCode.trim().uppercase()
        require(code.isNotBlank() && password.isNotBlank()) { "Enter your student code and password" }
        val auth = try {
            api.signIn(body = PasswordRequest(studentEmail(code), password))
        } catch (error: HttpException) {
            when (error.code()) {
                400, 401 -> throw IllegalArgumentException("Student code or password is incorrect")
                404 -> throw IllegalStateException("Supabase Auth endpoint was not found. Check SUPABASE_URL.")
                else -> throw error
            }
        }
        session.save(auth)
        return runCatching { loadProfile() }.getOrElse {
            session.clear()
            throw IllegalStateException("No student profile is linked to this login", it)
        }
    }

    suspend fun restore(): Student? {
        if (!session.signedIn.value) return null
        return runCatching { authorized { loadProfileDirect() } }.getOrElse {
            if (it is HttpException && it.code() == 401) session.clear()
            null
        }
    }

    suspend fun signOut() {
        try { if (session.signedIn.value) authorized { api.signOut() } } finally { session.clear() }
    }

    suspend fun getDoubts(): List<Doubt> = authorized { api.doubts() }.map { doubt ->
        Doubt(
            id = doubt.id,
            question = doubt.question,
            description = doubt.description.orEmpty(),
            author = doubt.authorName,
            postedAgo = "Recently",
            answers = doubt.answers.map { Answer(it.authorName, it.body, "Recently") }
        )
    }

    suspend fun addDoubt(question: String, description: String, author: String) = authorized {
        api.addDoubt(NewDoubt(requireUserId(), author, question, description))
    }

    suspend fun addAnswer(doubtId: Long, answer: String, author: String) = authorized {
        api.addAnswer(NewAnswer(doubtId, requireUserId(), author, answer))
    }

    suspend fun addJob(job: Job, author: String) = authorized {
        api.addJob(NewJob(requireUserId(), job.title, job.company, job.location, job.salary,
            job.experience, job.description, author, job.phone))
    }

    suspend fun addReferral(name: String, phone: String, note: String) = authorized {
        api.addReferral(NewReferral(requireUserId(), name, phone, note))
    }

    suspend fun createStudent(request: CreateStudentRequest) = authorized {
        api.createStudent(request)
    }

    suspend fun addAdminLog(studentCode: String, fullName: String, password: String, batch: String) = authorized {
        api.addAdminLog(AdminLogDto(studentCode, fullName, password, batch, session.userId))
    }

    suspend fun getAdminLogs(): List<AdminLogDto> = authorized { api.adminLogs() }

    private suspend fun loadProfile(): Student = authorized { loadProfileDirect() }

    private suspend fun loadProfileDirect(): Student {
        val row = api.profile("eq.${requireUserId()}").singleOrNull()
            ?: error("Student profile not found")
        return Student(row.studentCode, row.fullName, row.course, row.branch, row.batch,
            row.rollNumber, row.location, row.isAdmin)
    }

    private suspend fun <T> authorized(block: suspend () -> T): T {
        checkConfigured()
        return try { block() } catch (error: HttpException) {
            if (error.code() != 401 || session.refreshToken.isNullOrBlank()) throw error
            refreshMutex.withLock {
                val refreshed = api.refresh(body = RefreshRequest(session.refreshToken!!))
                session.save(refreshed)
            }
            block()
        }
    }

    private fun requireUserId() = session.userId ?: error("Please log in again")

    private fun checkConfigured() {
        check(BuildConfig.SUPABASE_ANON_KEY.isNotBlank() && !BuildConfig.SUPABASE_URL.contains("example.supabase.co")) {
            "Supabase is not configured. Add SUPABASE_URL and SUPABASE_ANON_KEY to local.properties."
        }
    }

    private fun studentEmail(code: String) = "${code.lowercase()}@students.vegamdigital.in"
}
