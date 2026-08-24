package in.vegamdigital.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.data.remote.FcmGateway;
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
public final class AppModule_FcmFactory implements Factory<FcmGateway> {
  @Override
  public FcmGateway get() {
    return fcm();
  }

  public static AppModule_FcmFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FcmGateway fcm() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.fcm());
  }

  private static final class InstanceHolder {
    private static final AppModule_FcmFactory INSTANCE = new AppModule_FcmFactory();
  }
}
