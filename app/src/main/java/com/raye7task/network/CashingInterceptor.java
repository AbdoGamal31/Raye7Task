package com.raye7task.network;

import android.content.Context;

import com.raye7task.MyApplication;

import okhttp3.Interceptor;
import okhttp3.Response;

import static com.raye7task.network.NetworkUtilities.isNetworkAvailable;

public class CashingInterceptor {

    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Context context = MyApplication.getContext();
        Response originalResponse = chain.proceed(chain.request());
        if (isNetworkAvailable(context)) {
            int maxAge = 60; // read from cache for 1 minute
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

}
