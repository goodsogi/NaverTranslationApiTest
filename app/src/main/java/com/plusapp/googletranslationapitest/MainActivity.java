package com.plusapp.googletranslationapitest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


public class MainActivity extends RxAppCompatActivity implements GetTranslationListener {

    public void start(View view) {
        String originalText = ((EditText) findViewById(R.id.text_input)).getText().toString();

        NaverTranslationManager naverTranslationManager = new NaverTranslationManager(this);
        naverTranslationManager.translate(originalText);
    }

    @Override
    public void onGetTranslation(NaverTranslationModel naverTranslationModel) {
        TextView tranlatedTextView = (TextView) findViewById(R.id.translated_text_view);
        tranlatedTextView.setText(naverTranslationModel.getMessage().getResult().getTranslatedText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
