package com.plusapp.googletranslationapitest;

import android.content.Context;
import android.util.Log;


import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by bagjeong-gyu on 2017. 1. 10..
 */

public class NaverTranslationManager extends PlusApiManager {

    private final Context mContext;
    private final GetTranslationListener mListener;
    private final NaverTranslationHelper mNaverTranslationHelper;

    private CompositeDisposable disposables = new CompositeDisposable();

    public NaverTranslationManager(Context context) {
        super(context);

        mContext = context;
        mListener = (GetTranslationListener) mContext;
        mNaverTranslationHelper = new NaverTranslationHelper();
    }

    public void translate(String originalText) {

        if (!PlusInternetConnectChecker.isConnected(mContext)) {
            showNoInternetToast();
            mListener.onGetTranslation(null);
            return;
        }

        //RxJava2에서 subscription이 용어가 혼동될 수 있다는 이유로 disposable로 변경됨
        if(disposables.size() != 0) {
            disposables.dispose();
        }

        Map<String, String> fields = getFields(originalText);

        try {

            disposables.add(
                    //onNext 다음에 onCompleted가 호출됨
                    mNaverTranslationHelper.translate(fields)
                            .compose(((RxAppCompatActivity) mContext).<NaverTranslationModel>bindUntilEvent(ActivityEvent.PAUSE)) //lifecycle 처리. 제대로 작동함. RxAppCompatActivity를 확장해야 함.
                            .subscribe(new Consumer<NaverTranslationModel>() {
                                @Override
                                public void accept(NaverTranslationModel naverTranslationModel) throws Exception {
                                    mListener.onGetTranslation(naverTranslationModel);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.d(this.getClass().getSimpleName(), throwable.getLocalizedMessage());
                                    //null을 넘기지 않으면 계속 로딩바가 돌아서 null을 넘김
                                    mListener.onGetTranslation(null);
                                }
                            })
            );
        } catch (Exception e) {

            mListener.onGetTranslation(null);
        }


//        Call<ResponseBody> result = checkNaviKeyHelper.checkTest(naviKey);
//
//        result.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.d("aaa", "aaa");
//                    System.out.println(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable e) {
//                e.printStackTrace();
//            }
//
//
//        });
    }

    private Map<String, String> getFields(String originalText) {

        Map<String, String> fields = new HashMap<String, String>();
        fields.put("text", originalText);
        fields.put("source", "ko");
        //getLanguage 맞나??, 영어 일어 중국어만 지원
        //target이 ko이면 http 400 bad request오류 발생
        //fields.put("target", Locale.getDefault().getLanguage());
        fields.put("target", "en");

        return fields;
    }
}
