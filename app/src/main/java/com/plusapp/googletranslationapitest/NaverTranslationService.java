package com.plusapp.googletranslationapitest;


import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by goodsogi on 2016/12/09.
 */
interface NaverTranslationService {

    String NAVER_CLIENT_ID = "3kNdQVGqpH8xg4fbC4zC";
    String NAVER_CLIENT_SECRET = "AJwOouOTOJ";

    @Headers({"X-Naver-Client-Id: " + NAVER_CLIENT_ID,
            "X-Naver-Client-Secret: " + NAVER_CLIENT_SECRET})
    @FormUrlEncoded
    @POST("v1/language/translate" )
    Single<NaverTranslationModel> translate(@FieldMap(encoded = true) Map<String, String> fields);


    @GET("jnavi/CheckIfKeyIsEnabled.jsp?" )
    Call<ResponseBody> checkTest(@Query("navi_key") String naviKey);

}
