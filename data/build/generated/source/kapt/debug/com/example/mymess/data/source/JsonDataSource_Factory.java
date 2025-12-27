package com.example.mymess.data.source;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class JsonDataSource_Factory implements Factory<JsonDataSource> {
  private final Provider<Context> contextProvider;

  public JsonDataSource_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public JsonDataSource get() {
    return newInstance(contextProvider.get());
  }

  public static JsonDataSource_Factory create(Provider<Context> contextProvider) {
    return new JsonDataSource_Factory(contextProvider);
  }

  public static JsonDataSource newInstance(Context context) {
    return new JsonDataSource(context);
  }
}
