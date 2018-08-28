package com.loction.xokhttp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.GsonResponseHandler;
import com.loction.xokhttp.response.IResponse;
import com.loction.xokhttp.response.RawResponseHandler;
import com.loction.xokhttp.utils.Responseer;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 公共参数
         */
//        HashMap<String, String> params = new HashMap<>();
//        params.put("username","tianxiaolong");
//        params.put("password","tianxiaolong");
        XOkhttpClient xOkhttpClient = new XOkhttpClient.Builder()
//                .addParams(params)
                .setLog()
                .builder();

        /**
         * userinfo
         * suername:xxx
         * pwd :sxx
         */
//        xOkhttpClient
//                .get()
//                .url("http://www.wanandroid.com/banner/json")
//                .enqueue(new GsonResponseHandler<List<AttrBean>>() {
//                    @Override
//                    public void onSuccful(List<AttrBean> response) {
//                        Log.d("haha", response.toString());
//
//                    }
//                });


        xOkhttpClient.get()
                .url("http://www.wanandroid.com/article/list/{page}/json")
                .addPathParam("page","0")
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccful(Responseer<String> responseer) {
                          LogUtils.d(responseer.body());
                    }

                    @Override
                    public void onFail(int errorCode, String errorMessage) {

                    }
                });



      xOkhttpClient
              .post()
              .url("http://www.wanandroid.com/user/login")
//              .addParam("username","tianxiaolong")
//              .addParam("password","tianxiaolong")
              .enqueue(new GsonResponseHandler<LoginResponse>() {
                  @Override
                  public void onSuccful(LoginResponse response) {
                      Log.d("haha", response.toString());
                  }
              });

    }
}
