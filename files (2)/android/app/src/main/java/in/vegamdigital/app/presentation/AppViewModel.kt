package `in`.vegamdigital.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.vegamdigital.app.domain.model.Dashboard
import `in`.vegamdigital.app.domain.model.Job
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
    val message: String? = null
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
    val uiState: StateFlow<AppUiState> = combine(repository.signedIn, repository.dashboard, busy, message) { signed, dashboard, loading, msg ->
        AppUiState(signed, dashboard, loading, msg)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppUiState())

    fun login(code: String, password: String) = launchAction("Welcome back") { loginUseCase(code, password).getOrThrow() }
    fun logout() = viewModelScope.launch { repository.logout() }
    fun askDoubt(question: String, details: String, done: () -> Unit) = launchAction("Question posted", done) { askDoubtUseCase(question, details) }
    fun answer(doubtId: Long, answer: String) = launchAction("Answer posted") { repository.answerDoubt(doubtId, answer) }
    fun postJob(job: Job, done: () -> Unit) = launchAction("Job submitted for approval", done) { postJobUseCase(job) }
    fun refer(name: String, phone: String, note: String) = launchAction("Referral sent") { repository.sendReferral(name, phone, note) }
    fun clearMessage() { message.value = null }

    private fun launchAction(success: String, done: () -> Unit = {}, action: suspend () -> Unit) = viewModelScope.launch {
        busy.value = true
        runCatching { action() }
            .onSuccess { message.value = success; done() }
            .onFailure { message.value = it.message ?: "Something went wrong" }
        busy.value = false
    }
}
