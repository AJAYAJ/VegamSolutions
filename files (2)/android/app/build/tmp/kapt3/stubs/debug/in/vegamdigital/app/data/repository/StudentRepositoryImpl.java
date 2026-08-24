package in.vegamdigital.app.data.repository;

import in.vegamdigital.app.data.local.DoubtEntity;
import in.vegamdigital.app.data.local.JobEntity;
import in.vegamdigital.app.data.local.SessionEntity;
import in.vegamdigital.app.data.local.VegamDao;
import in.vegamdigital.app.data.remote.FirebaseAuthGateway;
import in.vegamdigital.app.data.remote.FirestoreGateway;
import in.vegamdigital.app.domain.model.*;
import in.vegamdigital.app.domain.repository.StudentRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u00142\u0006\u0010&\u001a\u00020\'H\u0096@\u00a2\u0006\u0002\u0010(J\u001e\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020\'2\u0006\u0010+\u001a\u00020\'H\u0096@\u00a2\u0006\u0002\u0010,J,\u0010-\u001a\b\u0012\u0004\u0012\u00020$0.2\u0006\u0010/\u001a\u00020\'2\u0006\u00100\u001a\u00020\'H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b1\u0010,J\u000e\u00102\u001a\u00020$H\u0096@\u00a2\u0006\u0002\u00103J\u0016\u00104\u001a\u00020$2\u0006\u00105\u001a\u00020\u0019H\u0096@\u00a2\u0006\u0002\u00106J&\u00107\u001a\u00020$2\u0006\u00108\u001a\u00020\'2\u0006\u00109\u001a\u00020\'2\u0006\u0010:\u001a\u00020\'H\u0096@\u00a2\u0006\u0002\u0010;J\u001a\u0010<\u001a\u00020\u0017*\u00020=2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00150\nH\u0002J\f\u0010<\u001a\u00020\u0019*\u00020?H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u0011\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\n0\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\rX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0010R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006@"}, d2 = {"Lin/vegamdigital/app/data/repository/StudentRepositoryImpl;", "Lin/vegamdigital/app/domain/repository/StudentRepository;", "dao", "Lin/vegamdigital/app/data/local/VegamDao;", "auth", "Lin/vegamdigital/app/data/remote/FirebaseAuthGateway;", "firestore", "Lin/vegamdigital/app/data/remote/FirestoreGateway;", "(Lin/vegamdigital/app/data/local/VegamDao;Lin/vegamdigital/app/data/remote/FirebaseAuthGateway;Lin/vegamdigital/app/data/remote/FirestoreGateway;)V", "courses", "", "Lin/vegamdigital/app/domain/model/Course;", "dashboard", "Lkotlinx/coroutines/flow/Flow;", "Lin/vegamdigital/app/domain/model/Dashboard;", "getDashboard", "()Lkotlinx/coroutines/flow/Flow;", "localAnswers", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "", "Lin/vegamdigital/app/domain/model/Answer;", "seedDoubts", "Lin/vegamdigital/app/domain/model/Doubt;", "seedJobs", "Lin/vegamdigital/app/domain/model/Job;", "seniors", "Lin/vegamdigital/app/domain/model/Senior;", "signedIn", "", "getSignedIn", "student", "Lin/vegamdigital/app/domain/model/Student;", "updates", "Lin/vegamdigital/app/domain/model/Update;", "answerDoubt", "", "doubtId", "answer", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "askDoubt", "question", "description", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lkotlin/Result;", "code", "password", "login-0E7RQCE", "logout", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "postJob", "job", "(Lin/vegamdigital/app/domain/model/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendReferral", "name", "phone", "note", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toDomain", "Lin/vegamdigital/app/data/local/DoubtEntity;", "answers", "Lin/vegamdigital/app/data/local/JobEntity;", "app_debug"})
public final class StudentRepositoryImpl implements in.vegamdigital.app.domain.repository.StudentRepository {
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.data.local.VegamDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.data.remote.FirebaseAuthGateway auth = null;
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.data.remote.FirestoreGateway firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.Long, java.util.List<in.vegamdigital.app.domain.model.Answer>>> localAnswers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> signedIn = null;
    @org.jetbrains.annotations.NotNull()
    private final in.vegamdigital.app.domain.model.Student student = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<in.vegamdigital.app.domain.model.Course> courses = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<in.vegamdigital.app.domain.model.Job> seedJobs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<in.vegamdigital.app.domain.model.Doubt> seedDoubts = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<in.vegamdigital.app.domain.model.Senior> seniors = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<in.vegamdigital.app.domain.model.Update> updates = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<in.vegamdigital.app.domain.model.Dashboard> dashboard = null;
    
    @javax.inject.Inject()
    public StudentRepositoryImpl(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.data.local.VegamDao dao, @org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.data.remote.FirebaseAuthGateway auth, @org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.data.remote.FirestoreGateway firestore) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getSignedIn() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<in.vegamdigital.app.domain.model.Dashboard> getDashboard() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object askDoubt(@org.jetbrains.annotations.NotNull()
    java.lang.String question, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object answerDoubt(long doubtId, @org.jetbrains.annotations.NotNull()
    java.lang.String answer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object postJob(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.domain.model.Job job, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object sendReferral(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String phone, @org.jetbrains.annotations.NotNull()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final in.vegamdigital.app.domain.model.Doubt toDomain(in.vegamdigital.app.data.local.DoubtEntity $this$toDomain, java.util.List<in.vegamdigital.app.domain.model.Answer> answers) {
        return null;
    }
    
    private final in.vegamdigital.app.domain.model.Job toDomain(in.vegamdigital.app.data.local.JobEntity $this$toDomain) {
        return null;
    }
}