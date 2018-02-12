package com.plusapp.googletranslationapitest;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bagjeong-gyu on 2017. 2. 6..
 */

public class PlusToaster {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }
}


