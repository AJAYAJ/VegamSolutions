package in.vegamdigital.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_RetrofitFactory implements Factory<Retrofit> {
  @Override
  public Retrofit get() {
    return retrofit();
  }

  public static AppModule_RetrofitFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Retrofit retrofit() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.retrofit());
  }

  private static final class InstanceHolder {
    private static final AppModule_RetrofitFactory INSTANCE = new AppModule_RetrofitFactory();
  }
}
