package com.example.mymess.data.repository;

import com.example.mymess.data.local.MessDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class RoomMessRepository_Factory implements Factory<RoomMessRepository> {
  private final Provider<MessDao> daoProvider;

  public RoomMessRepository_Factory(Provider<MessDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public RoomMessRepository get() {
    return newInstance(daoProvider.get());
  }

  public static RoomMessRepository_Factory create(Provider<MessDao> daoProvider) {
    return new RoomMessRepository_Factory(daoProvider);
  }

  public static RoomMessRepository newInstance(MessDao dao) {
    return new RoomMessRepository(dao);
  }
}
