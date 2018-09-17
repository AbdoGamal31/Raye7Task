package com.raye7task.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.raye7task.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CashingInterceptor {

    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
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
        }
    };

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectMgr != null) {
            activeNetworkInfo = connectMgr.getActiveNetworkInfo();
        }
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
