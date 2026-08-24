package in.vegamdigital.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import in.vegamdigital.app.data.local.VegamDao;
import in.vegamdigital.app.data.local.VegamDatabase;
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
public final class AppModule_DaoFactory implements Factory<VegamDao> {
  private final Provider<VegamDatabase> databaseProvider;

  public AppModule_DaoFactory(Provider<VegamDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VegamDao get() {
    return dao(databaseProvider.get());
  }

  public static AppModule_DaoFactory create(Provider<VegamDatabase> databaseProvider) {
    return new AppModule_DaoFactory(databaseProvider);
  }

  public static VegamDao dao(VegamDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.dao(database));
  }
}
