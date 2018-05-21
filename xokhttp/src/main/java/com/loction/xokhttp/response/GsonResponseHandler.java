package com.loction.xokhttp.response;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.utils.MD5Encrypt;
import com.loction.xokhttp.utils.Responseer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by localadmin on 2017/11/13.
 */

public abstract class GsonResponseHandler<T> implements IResponse {
    private Type mType;
    private String bodyStr;

    public GsonResponseHandler() {

        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    public abstract void onSuccful(Responseer<T> responseer);

    @Override
    public void onSuccful(final Response response) {
        final ResponseBody body = response.body();

        try {
            bodyStr = body.string();
            bodyStr = MD5Encrypt.ss(bodyStr, XOkhttpClient.KEY_ENCOUPT);
        } catch (IOException e) {
            e.printStackTrace();
            XOkhttpClient.handler.post(new Runnable() {
                @Override
                public void run() {
                    onFail(response.code(), "error read msg");
                }
            });
            return;
        } finally {
            response.close();
        }

        XOkhttpClient.handler.post(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                /**
                 * 优化回调
                 */
                onSuccful(new Responseer<T>((T) gson.fromJson(bodyStr, mType), response));
            }
        });
    }

    @Override
    public void onFail(int errorCode, String errorMessage) {

    }
}
