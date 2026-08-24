package in.vegamdigital.app.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.domain.repository.StudentRepository;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LoginUseCase_Factory implements Factory<LoginUseCase> {
  private final Provider<StudentRepository> repositoryProvider;

  public LoginUseCase_Factory(Provider<StudentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public LoginUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static LoginUseCase_Factory create(Provider<StudentRepository> repositoryProvider) {
    return new LoginUseCase_Factory(repositoryProvider);
  }

  public static LoginUseCase newInstance(StudentRepository repository) {
    return new LoginUseCase(repository);
  }
}
