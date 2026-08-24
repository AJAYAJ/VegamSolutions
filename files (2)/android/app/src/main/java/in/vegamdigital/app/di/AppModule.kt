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
import okhttp3.OkHttpClient
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
    @Provides @Singleton fun sessionStore(@ApplicationContext context: Context) = SupabaseSessionStore(context)
    @Provides @Singleton fun httpClient(session: SupabaseSessionStore): OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val token = session.accessToken ?: `in`.vegamdigital.app.BuildConfig.SUPABASE_ANON_KEY
            val request = chain.request().newBuilder()
                .header("apikey", `in`.vegamdigital.app.BuildConfig.SUPABASE_ANON_KEY)
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .build()
            chain.proceed(request)
        }.build()
    @Provides @Singleton fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(`in`.vegamdigital.app.BuildConfig.SUPABASE_URL.trimEnd('/') + "/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
    @Provides @Singleton fun api(retrofit: Retrofit): SupabaseApi = retrofit.create(SupabaseApi::class.java)
}
