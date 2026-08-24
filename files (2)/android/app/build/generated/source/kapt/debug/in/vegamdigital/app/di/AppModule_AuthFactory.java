package in.vegamdigital.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.data.remote.FirebaseAuthGateway;
import javax.annotation.processing.Generated;

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
public final class AppModule_AuthFactory implements Factory<FirebaseAuthGateway> {
  @Override
  public FirebaseAuthGateway get() {
    return auth();
  }

  public static AppModule_AuthFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAuthGateway auth() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.auth());
  }

  private static final class InstanceHolder {
    private static final AppModule_AuthFactory INSTANCE = new AppModule_AuthFactory();
  }
}
