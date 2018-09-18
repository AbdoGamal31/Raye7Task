package com.raye7task.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtilities {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectMgr != null) {
            activeNetworkInfo = connectMgr.getActiveNetworkInfo();
        }
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

}
