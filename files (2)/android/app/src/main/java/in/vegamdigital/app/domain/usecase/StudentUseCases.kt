package `in`.vegamdigital.app.domain.usecase

import `in`.vegamdigital.app.domain.model.Job
import `in`.vegamdigital.app.domain.repository.StudentRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(code: String, password: String) = repository.login(code.trim(), password)
}

class AskDoubtUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(question: String, details: String) = repository.askDoubt(question.trim(), details.trim())
}

class PostJobUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(job: Job) = repository.postJob(job)
}
