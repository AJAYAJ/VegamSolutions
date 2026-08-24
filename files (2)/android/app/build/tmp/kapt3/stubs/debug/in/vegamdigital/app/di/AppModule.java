package in.vegamdigital.app.di;

import android.content.Context;
import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import in.vegamdigital.app.data.local.VegamDao;
import in.vegamdigital.app.data.local.VegamDatabase;
import in.vegamdigital.app.data.remote.*;
import in.vegamdigital.app.data.repository.StudentRepositoryImpl;
import in.vegamdigital.app.domain.repository.StudentRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0012\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\r\u001a\u00020\u000eH\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0007J\b\u0010\u0011\u001a\u00020\u0012H\u0007J\b\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0013\u001a\u00020\u0014H\u0007\u00a8\u0006\u0015"}, d2 = {"Lin/vegamdigital/app/di/AppModule;", "", "()V", "api", "Lin/vegamdigital/app/data/remote/VegamApi;", "retrofit", "Lretrofit2/Retrofit;", "auth", "Lin/vegamdigital/app/data/remote/FirebaseAuthGateway;", "dao", "Lin/vegamdigital/app/data/local/VegamDao;", "database", "Lin/vegamdigital/app/data/local/VegamDatabase;", "context", "Landroid/content/Context;", "fcm", "Lin/vegamdigital/app/data/remote/FcmGateway;", "firestore", "Lin/vegamdigital/app/data/remote/FirestoreGateway;", "storage", "Lin/vegamdigital/app/data/remote/FirebaseStorageGateway;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final in.vegamdigital.app.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.local.VegamDatabase database(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.local.VegamDao dao(@org.jetbrains.annotations.NotNull()
    in.vegamdigital.app.data.local.VegamDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.remote.FirebaseAuthGateway auth() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.remote.FirestoreGateway firestore() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.remote.FirebaseStorageGateway storage() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.remote.FcmGateway fcm() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit retrofit() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final in.vegamdigital.app.data.remote.VegamApi api(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
}