package `in`.vegamdigital.app.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.vegamdigital.app.data.local.VegamDao
import `in`.vegamdigital.app.data.local.VegamDatabase
import `in`.vegamdigital.app.data.remote.*
import `in`.vegamdigital.app.data.repository.StudentRepositoryImpl
import `in`.vegamdigital.app.domain.repository.StudentRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton abstract fun repository(impl: StudentRepositoryImpl): StudentRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton fun database(@ApplicationContext context: Context): VegamDatabase =
        Room.databaseBuilder(context, VegamDatabase::class.java, "vegam.db").fallbackToDestructiveMigration().build()
    @Provides fun dao(database: VegamDatabase): VegamDao = database.dao()
    @Provides @Singleton fun auth(): FirebaseAuthGateway = DummyFirebaseAuthGateway()
    @Provides @Singleton fun firestore(): FirestoreGateway = DummyFirestoreGateway()
    @Provides @Singleton fun storage(): FirebaseStorageGateway = DummyFirebaseStorageGateway()
    @Provides @Singleton fun fcm(): FcmGateway = DummyFcmGateway()
    @Provides @Singleton fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.vegamdigital.in/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    @Provides @Singleton fun api(retrofit: Retrofit): VegamApi = retrofit.create(VegamApi::class.java)
}
