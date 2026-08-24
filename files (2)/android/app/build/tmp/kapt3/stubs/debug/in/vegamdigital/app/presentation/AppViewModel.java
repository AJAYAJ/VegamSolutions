package in.vegamdigital.app.presentation;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import in.vegamdigital.app.domain.model.Dashboard;
import in.vegamdigital.app.domain.model.Job;
import in.vegamdigital.app.domain.repository.StudentRepository;
import in.vegamdigital.app.domain.usecase.AskDoubtUseCase;
import in.vegamdigital.app.domain.usecase.LoginUseCase;
import in.vegamdigital.app.domain.usecase.PostJobUseCase;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\u000fJ$\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u0006\u0010\u001f\u001a\u00020\u001eJC\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u000f2\u000e\b\u0002\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u001c\u0010\"\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0$\u0012\u0006\u0012\u0004\u0018\u00010%0#H\u0002\u00a2\u0006\u0002\u0010&J\u0016\u0010\'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020\u000fJ\u0006\u0010*\u001a\u00020\u0016J\u001c\u0010+\u001a\u00020\u00162\u0006\u0010,\u001a\u00020-2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dJ\u001e\u0010.\u001a\u00020\u00162\u0006\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u000fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u00062"}, d2 = {"Lin/vegamdigital/app/presentation/AppViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lin/vegamdigital/app/domain/repository/StudentRepository;", "loginUseCase", "Lin/vegamdigital/app/domain/usecase/LoginUseCase;", "askDoubtUseCase", "Lin/vegamdigital/app/domain/usecase/AskDoubtUseCase;", "postJobUseCase", "Lin/vegamdigital/app/domain/usecase/PostJobUseCase;", "(Lin/vegamdigital/app/domain/repository/StudentRepository;Lin/vegamdigital/app/domain/usecase/LoginUseCase;Lin/vegamdigital/app/domain/usecase/AskDoubtUseCase;Lin/vegamdigital/app/domain/usecase/PostJobUseCase;)V", "busy", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "message", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lin/vegamdigital/app/presentation/AppUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "answer", "Lkotlinx/coroutines/Job;", "doubtId", "", "askDoubt", "question", "details", "done", "Lkotlin/Function0;", "", "clearMessage", "launchAction", "success", "action", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/Job;", "login", "code", "password", "logout", "postJob", "job", "Lin/vegamdigital/app/domain/model/Job;", "refer", "name", "phone", "note", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AppViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.domain.repository.StudentRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.domain.usecase.LoginUseCase loginUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.domain.usecase.AskDoubtUseCase askDoubtUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.domain.usecase.PostJobUseCase postJobUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> busy = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> message = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<in.vegamdigital.app.presentation.AppUiState> uiState = null;
    
    @javax.inject.Inject()
    public AppViewModel(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.repository.StudentRepository repository, @org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.usecase.LoginUseCase loginUseCase, @org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.usecase.AskDoubtUseCase askDoubtUseCase, @org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.usecase.PostJobUseCase postJobUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<in.vegamdigital.app.presentation.AppUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job login(@org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job logout() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job askDoubt(@org.jetbrains.annotations.NotNull()
    java.lang.String question, @org.jetbrains.annotations.NotNull()
    java.lang.String details, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> done) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job answer(long doubtId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job postJob(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Job job, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> done) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job refer(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String note) {
        return null;
    }
    
    public final void clearMessage() {
    }
    
    private final kotlinx.coroutines.Job launchAction(java.lang.String success, kotlin.jvm.functions.Function0<kotlin.Unit> done, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> action) {
        return null;
    }
}