package com.loction.xokhttp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.IResponse;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XOkhttpClient xOkhttpClient = new XOkhttpClient.Builder()
                .builder();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "txl");
            jsonObject.put("age", "12");
        } catch (JSONException e) {
            return;
        }
        /**
         * userinfo
         * suername:xxx
         * pwd :sxx
         */
        xOkhttpClient
                .post()
                .url("http://www.baidu.com")
                .putBodyJson(jsonObject)
                .putHeaderJson("user","123")
                .putHeaderJson("ll","Nihap")
                .enqueue(new IResponse() {
                    @Override
                    public void onSuccful(Response response) {

                    }

                    @Override
                    public void onFail(int errorCode, String errorMessage) {

                    }
                });



    }
}
