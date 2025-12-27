package com.example.mymess.data.di;

import com.example.mymess.data.local.MessDao;
import com.example.mymess.data.local.MyMessDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class DataModule_Companion_ProvideMessDaoFactory implements Factory<MessDao> {
  private final Provider<MyMessDatabase> databaseProvider;

  public DataModule_Companion_ProvideMessDaoFactory(Provider<MyMessDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MessDao get() {
    return provideMessDao(databaseProvider.get());
  }

  public static DataModule_Companion_ProvideMessDaoFactory create(
      Provider<MyMessDatabase> databaseProvider) {
    return new DataModule_Companion_ProvideMessDaoFactory(databaseProvider);
  }

  public static MessDao provideMessDao(MyMessDatabase database) {
    return Preconditions.checkNotNullFromProvides(DataModule.Companion.provideMessDao(database));
  }
}
