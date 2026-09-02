package `in`.vegamdigital.app.domain.repository

import `in`.vegamdigital.app.domain.model.Dashboard
import `in`.vegamdigital.app.domain.model.Doubt
import `in`.vegamdigital.app.domain.model.Job
import `in`.vegamdigital.app.domain.model.Student
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    val dashboard: Flow<Dashboard>
    val signedIn: Flow<Boolean>
    val currentStudent: Flow<Student?>
    suspend fun login(code: String, password: String): Result<Unit>
    suspend fun logout()
    suspend fun askDoubt(question: String, description: String)
    suspend fun answerDoubt(doubtId: Long, answer: String)
    suspend fun pullDoubts()
    fun stopPullingDoubts()
    suspend fun refreshDoubts()
    suspend fun postJob(job: Job)
    suspend fun sendReferral(name: String, phone: String, note: String)
    suspend fun createStudent(student: Student, password: String): Result<Unit>
    val adminLogs: Flow<List<AdminLog>>
    suspend fun refreshAdminLogs()
}

data class AdminLog(
    val studentCode: String,
    val name: String,
    val password: String,
    val batch: String,
    val date: String
)
