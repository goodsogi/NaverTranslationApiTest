package com.plusapp.googletranslationapitest;

import java.util.Map;


import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by OWNER on 2016-12-09.
 */

public class NaverTranslationHelper {


    //baseUrl은 /로 끝나야 함.
    private final static String API_HOST = "https://openapi.naver.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    //api호출같이 하나의 값을 받는 것은 Observable보다 Single사용 권장
    public Single<NaverTranslationModel> translate(Map<String, String> fields) {
        return getService(NaverTranslationService.class)
                .translate(fields)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public  Call<ResponseBody> checkTest(String naviKey) {
        return getService(NaverTranslationService.class)
                .checkTest(naviKey)
                ;
    }

    private <S> S getService(Class<S> serviceClass) {

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }





}
