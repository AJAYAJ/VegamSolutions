package `in`.vegamdigital.app.domain.repository

import `in`.vegamdigital.app.domain.model.Dashboard
import `in`.vegamdigital.app.domain.model.Doubt
import `in`.vegamdigital.app.domain.model.Job
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    val dashboard: Flow<Dashboard>
    val signedIn: Flow<Boolean>
    suspend fun login(code: String, password: String): Result<Unit>
    suspend fun logout()
    suspend fun askDoubt(question: String, description: String)
    suspend fun answerDoubt(doubtId: Long, answer: String)
    suspend fun postJob(job: Job)
    suspend fun sendReferral(name: String, phone: String, note: String)
}
