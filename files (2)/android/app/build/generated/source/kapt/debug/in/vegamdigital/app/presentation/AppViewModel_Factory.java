package in.vegamdigital.app.presentation;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.domain.repository.StudentRepository;
import in.vegamdigital.app.domain.usecase.AskDoubtUseCase;
import in.vegamdigital.app.domain.usecase.LoginUseCase;
import in.vegamdigital.app.domain.usecase.PostJobUseCase;
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
public final class AppViewModel_Factory implements Factory<AppViewModel> {
  private final Provider<StudentRepository> repositoryProvider;

  private final Provider<LoginUseCase> loginUseCaseProvider;

  private final Provider<AskDoubtUseCase> askDoubtUseCaseProvider;

  private final Provider<PostJobUseCase> postJobUseCaseProvider;

  public AppViewModel_Factory(Provider<StudentRepository> repositoryProvider,
      Provider<LoginUseCase> loginUseCaseProvider,
      Provider<AskDoubtUseCase> askDoubtUseCaseProvider,
      Provider<PostJobUseCase> postJobUseCaseProvider) {
    this.repositoryProvider = repositoryProvider;
    this.loginUseCaseProvider = loginUseCaseProvider;
    this.askDoubtUseCaseProvider = askDoubtUseCaseProvider;
    this.postJobUseCaseProvider = postJobUseCaseProvider;
  }

  @Override
  public AppViewModel get() {
    return newInstance(repositoryProvider.get(), loginUseCaseProvider.get(), askDoubtUseCaseProvider.get(), postJobUseCaseProvider.get());
  }

  public static AppViewModel_Factory create(Provider<StudentRepository> repositoryProvider,
      Provider<LoginUseCase> loginUseCaseProvider,
      Provider<AskDoubtUseCase> askDoubtUseCaseProvider,
      Provider<PostJobUseCase> postJobUseCaseProvider) {
    return new AppViewModel_Factory(repositoryProvider, loginUseCaseProvider, askDoubtUseCaseProvider, postJobUseCaseProvider);
  }

  public static AppViewModel newInstance(StudentRepository repository, LoginUseCase loginUseCase,
      AskDoubtUseCase askDoubtUseCase, PostJobUseCase postJobUseCase) {
    return new AppViewModel(repository, loginUseCase, askDoubtUseCase, postJobUseCase);
  }
}
