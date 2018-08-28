package com.loction.xokhttp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.GsonResponseHandler;
import com.loction.xokhttp.response.IResponse;
import com.loction.xokhttp.utils.Responseer;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.temporal.ValueRange;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XOkhttpClient xOkhttpClient = new XOkhttpClient.Builder()
                .setLog()
                .builder();

        /**
         * userinfo
         * suername:xxx
         * pwd :sxx
         */
        xOkhttpClient
                .get()
                .url("http://www.wanandroid.com/banner/json")
                .enqueue(new GsonResponseHandler<List<AttrBean>>() {
                    @Override
                    public void onSuccful(List<AttrBean> response) {
                        Log.d("haha", response.toString());

                    }
                });

      xOkhttpClient
              .post()
              .url("http://www.wanandroid.com/user/login")
              .addParam("username","tianxiaolong")
              .addParam("password","tianxiaolong")
              .enqueue(new GsonResponseHandler<LoginResponse>() {
                  @Override
                  public void onSuccful(LoginResponse response) {
                      Log.d("haha", response.toString());
                  }
              });

    }
}
