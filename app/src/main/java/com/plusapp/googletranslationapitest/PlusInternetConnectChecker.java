package com.plusapp.googletranslationapitest;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by bagjeong-gyu on 2017. 2. 9..
 */

public class PlusInternetConnectChecker {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
