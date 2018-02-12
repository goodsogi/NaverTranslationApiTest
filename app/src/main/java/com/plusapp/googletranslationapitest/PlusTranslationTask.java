package com.plusapp.googletranslationapitest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bagjeong-gyu on 2017. 9. 13..
 */

class PlusTranslationTask extends AsyncTask<String, Object, String> {


    private final String clientId = "3kNdQVGqpH8xg4fbC4zC";
    private final String clientSecret = "AJwOouOTOJ";

    private final GetTranslationListener mListener;

    PlusTranslationTask(GetTranslationListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        String originalText = params[0];



        try {


            String text = URLEncoder.encode(originalText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=en&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;

    }



    @Override
    protected void onPostExecute(String translatedText) {
        super.onPostExecute(translatedText);

        mListener.onGetTranslation(null);
    }
}