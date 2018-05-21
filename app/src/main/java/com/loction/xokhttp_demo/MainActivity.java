package com.loction.xokhttp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loction.xokhttp.XOkhttpClient;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XOkhttpClient xOkhttpClient = new XOkhttpClient.Builder()
                .builder();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("12", "1");
            jsonObject.put("12", "1");
            jsonObject.put("12", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        xOkhttpClient.



    }
}
