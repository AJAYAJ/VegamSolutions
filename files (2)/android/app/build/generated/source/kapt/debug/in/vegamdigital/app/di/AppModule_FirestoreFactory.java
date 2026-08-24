package in.vegamdigital.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.data.remote.FirestoreGateway;
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
public final class AppModule_FirestoreFactory implements Factory<FirestoreGateway> {
  @Override
  public FirestoreGateway get() {
    return firestore();
  }

  public static AppModule_FirestoreFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirestoreGateway firestore() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.firestore());
  }

  private static final class InstanceHolder {
    private static final AppModule_FirestoreFactory INSTANCE = new AppModule_FirestoreFactory();
  }
}
