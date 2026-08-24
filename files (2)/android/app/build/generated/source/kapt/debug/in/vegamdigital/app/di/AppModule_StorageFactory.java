package in.vegamdigital.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.data.remote.FirebaseStorageGateway;
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
public final class AppModule_StorageFactory implements Factory<FirebaseStorageGateway> {
  @Override
  public FirebaseStorageGateway get() {
    return storage();
  }

  public static AppModule_StorageFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseStorageGateway storage() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.storage());
  }

  private static final class InstanceHolder {
    private static final AppModule_StorageFactory INSTANCE = new AppModule_StorageFactory();
  }
}
