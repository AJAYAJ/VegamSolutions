package in.vegamdigital.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.data.local.VegamDao;
import in.vegamdigital.app.data.remote.FirebaseAuthGateway;
import in.vegamdigital.app.data.remote.FirestoreGateway;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class StudentRepositoryImpl_Factory implements Factory<StudentRepositoryImpl> {
  private final Provider<VegamDao> daoProvider;

  private final Provider<FirebaseAuthGateway> authProvider;

  private final Provider<FirestoreGateway> firestoreProvider;

  public StudentRepositoryImpl_Factory(Provider<VegamDao> daoProvider,
      Provider<FirebaseAuthGateway> authProvider, Provider<FirestoreGateway> firestoreProvider) {
    this.daoProvider = daoProvider;
    this.authProvider = authProvider;
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public StudentRepositoryImpl get() {
    return newInstance(daoProvider.get(), authProvider.get(), firestoreProvider.get());
  }

  public static StudentRepositoryImpl_Factory create(Provider<VegamDao> daoProvider,
      Provider<FirebaseAuthGateway> authProvider, Provider<FirestoreGateway> firestoreProvider) {
    return new StudentRepositoryImpl_Factory(daoProvider, authProvider, firestoreProvider);
  }

  public static StudentRepositoryImpl newInstance(VegamDao dao, FirebaseAuthGateway auth,
      FirestoreGateway firestore) {
    return new StudentRepositoryImpl(dao, auth, firestore);
  }
}
