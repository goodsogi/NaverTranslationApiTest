package com.plusapp.googletranslationapitest;

import android.content.Context;

/**
 * Created by bagjeong-gyu on 2017. 10. 16..
 */

public class PlusApiManager {

    private Context mContext;

    public PlusApiManager(Context context) {
        mContext = context;
    }


    protected void showNoInternetToast() {
       PlusToaster.show(mContext, mContext.getString(R.string.no_internet_alert_text));
    }


}
