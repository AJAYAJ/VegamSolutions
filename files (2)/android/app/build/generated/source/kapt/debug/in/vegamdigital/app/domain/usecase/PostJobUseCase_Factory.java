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
public final class PostJobUseCase_Factory implements Factory<PostJobUseCase> {
  private final Provider<StudentRepository> repositoryProvider;

  public PostJobUseCase_Factory(Provider<StudentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PostJobUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static PostJobUseCase_Factory create(Provider<StudentRepository> repositoryProvider) {
    return new PostJobUseCase_Factory(repositoryProvider);
  }

  public static PostJobUseCase newInstance(StudentRepository repository) {
    return new PostJobUseCase(repository);
  }
}
