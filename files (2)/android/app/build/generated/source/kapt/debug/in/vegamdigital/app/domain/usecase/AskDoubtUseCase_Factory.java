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
public final class AskDoubtUseCase_Factory implements Factory<AskDoubtUseCase> {
  private final Provider<StudentRepository> repositoryProvider;

  public AskDoubtUseCase_Factory(Provider<StudentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AskDoubtUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AskDoubtUseCase_Factory create(Provider<StudentRepository> repositoryProvider) {
    return new AskDoubtUseCase_Factory(repositoryProvider);
  }

  public static AskDoubtUseCase newInstance(StudentRepository repository) {
    return new AskDoubtUseCase(repository);
  }
}
