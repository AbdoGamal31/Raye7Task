package com.raye7task.network.module;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.raye7task.network.CashingInterceptor.REWRITE_CACHE_CONTROL_INTERCEPTOR;
import static com.raye7task.network.CheckConnectionInterceptor.CHECK_NETWORK_CONNECTION_INTERCEPTOR;

@Module
public class NetworkModule {
  private String urlPath;
  private Context context;

  public NetworkModule(String urlPath) {
    this.urlPath = urlPath;
  }

  @Provides
  @Singleton
  Cache provideHttpCache(Context context) {
    this.context = context;
    File httpCacheDirectory = new File(context.getCacheDir(), "responses");
    int cacheSize = 10 * 1024 * 1024; // 10 MiB
    Cache cache = new Cache(httpCacheDirectory, cacheSize);
    return cache;
  }

  @Singleton
  @Provides
  public Gson provideGson() {
    GsonBuilder builder = new GsonBuilder();
    builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    return builder.create();
  }

  @Provides
  @Singleton
  OkHttpClient provideOkhttpClient(Cache cache) {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    client.addInterceptor(CHECK_NETWORK_CONNECTION_INTERCEPTOR);
    client.cache(cache);
    client.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
    return client.build();
  }

  @Singleton
  @Provides
  public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
            .baseUrl(urlPath)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
  }
}
