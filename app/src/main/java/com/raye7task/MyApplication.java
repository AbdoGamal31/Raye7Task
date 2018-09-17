package com.raye7task;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

  private static Context context;
  public static final String BASE_URL = "http://newsapi.org/";
  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }

  public static Context getContext() {
    return context;
  }
}