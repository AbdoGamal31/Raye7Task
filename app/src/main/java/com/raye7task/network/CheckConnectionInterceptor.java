package com.raye7task.network;

import com.raye7task.MyApplication;

import okhttp3.Interceptor;

import static com.raye7task.network.NetworkUtilities.isNetworkAvailable;

public class CheckConnectionInterceptor {


    public static final Interceptor CHECK_NETWORK_CONNECTION_INTERCEPTOR = chain -> {
        Boolean isConnected = isNetworkAvailable(MyApplication.getContext());
        if (!isConnected) {
            throw NetworkError.createOfflineError();
        } else {
            return chain.proceed(chain.request());
        }
    };
}
