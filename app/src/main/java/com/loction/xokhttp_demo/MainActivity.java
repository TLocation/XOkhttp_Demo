package com.loction.xokhttp_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loction.xokhttp.XOkhttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XOkhttpClient xOkhttpClient = new XOkhttpClient.Builder()
                .builder();



    }
}
