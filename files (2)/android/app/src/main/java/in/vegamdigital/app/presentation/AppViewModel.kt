package `in`.vegamdigital.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.vegamdigital.app.domain.model.Dashboard
import `in`.vegamdigital.app.domain.model.Job
import `in`.vegamdigital.app.domain.model.Student
import `in`.vegamdigital.app.domain.repository.AdminLog
import `in`.vegamdigital.app.domain.repository.StudentRepository
import `in`.vegamdigital.app.domain.usecase.AskDoubtUseCase
import `in`.vegamdigital.app.domain.usecase.LoginUseCase
import `in`.vegamdigital.app.domain.usecase.PostJobUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppUiState(
    val signedIn: Boolean? = null,
    val dashboard: Dashboard? = null,
    val busy: Boolean = false,
    val message: String? = null,
    val isAdmin: Boolean = false,
    val adminLogs: List<AdminLog> = emptyList()
)

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: StudentRepository,
    private val loginUseCase: LoginUseCase,
    private val askDoubtUseCase: AskDoubtUseCase,
    private val postJobUseCase: PostJobUseCase
) : ViewModel() {
    private val busy = MutableStateFlow(false)
    private val message = MutableStateFlow<String?>(null)
    val uiState: StateFlow<AppUiState> = combine(
        repository.signedIn,
        repository.dashboard,
        repository.currentStudent,
        repository.adminLogs,
        busy,
        message
    ) { args ->
        val signed = args[0] as? Boolean
        val dashboard = args[1] as? Dashboard
        val student = args[2] as? Student
        val logs = args[3] as List<AdminLog>
        val loading = args[4] as Boolean
        val msg = args[5] as? String
        AppUiState(signed, dashboard, loading, msg, student?.isAdmin ?: false, logs)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppUiState())

    fun login(code: String, password: String) = launchAction("Welcome back") { loginUseCase(code, password).getOrThrow() }
    fun logout() = viewModelScope.launch { repository.logout() }
    fun askDoubt(question: String, details: String, done: () -> Unit) = launchAction("Question posted", done) { askDoubtUseCase(question, details) }
    fun answer(doubtId: Long, answer: String) = launchAction("Answer posted") { repository.answerDoubt(doubtId, answer) }
    fun startDoubtPolling() = viewModelScope.launch { repository.pullDoubts() }
    fun stopDoubtPolling() = repository.stopPullingDoubts()
    fun refreshDoubts() = launchAction("Doubt refreshed") { repository.refreshDoubts() }
    fun postJob(job: Job, done: () -> Unit) = launchAction("Job submitted for approval", done) { postJobUseCase(job) }
    fun refer(name: String, phone: String, note: String) = launchAction("Referral sent") { repository.sendReferral(name, phone, note) }
    
    fun createStudent(student: Student, password: String, done: () -> Unit) = launchAction("Student account created successfully", done) {
        repository.createStudent(student, password).getOrThrow()
    }

    fun refreshAdminLogs() = launchAction("Creation logs refreshed") {
        repository.refreshAdminLogs()
    }

    fun clearMessage() { message.value = null }

    private fun launchAction(success: String, done: () -> Unit = {}, action: suspend () -> Unit) = viewModelScope.launch {
        busy.value = true
        runCatching { action() }
            .onSuccess { message.value = success; done() }
            .onFailure { message.value = it.message ?: "Something went wrong" }
        busy.value = false
    }
}
