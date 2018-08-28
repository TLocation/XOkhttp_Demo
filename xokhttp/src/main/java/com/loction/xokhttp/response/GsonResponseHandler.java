package com.loction.xokhttp.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loction.xokhttp.BaseResponse;
import com.loction.xokhttp.XOkhttpClient;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by localadmin on 2017/11/13.
 */

public abstract class GsonResponseHandler<T> implements IResponse,ParameterizedType  {
    private String bodyStr;

    public GsonResponseHandler() {


    }

    public abstract void onSuccful(T response);

    @Override
    public void onSuccful(final Response response) {
        final ResponseBody body = response.body();

        try {
            bodyStr = body.string();
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
        final Type gsonType = this;
        XOkhttpClient.handler.post(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                /**
                 * 优化回调
                 */
//                onSuccful(new Responseer<T>((T) gson.fromJson(bodyStr, mType), response));
                TypeToken<BaseResponse<T>> typeToken = new TypeToken<BaseResponse<T>>() {
                };
                BaseResponse<T> baseResponse = gson.fromJson(bodyStr, gsonType);
                onSuccful(baseResponse.getData());
            }
        });
    }

    @Override
    public void onFail(int errorCode, String errorMessage) {

    }



    @Override
    public Type[] getActualTypeArguments() {
        Class clz = this.getClass();
        //这里必须注意在外面使用new GsonResponsePasare<GsonResponsePasare.DataInfo>(){};实例化时必须带上{},否则获取到的superclass为Object
        Type superclass = clz.getGenericSuperclass(); //getGenericSuperclass()获得带有泛型的父类
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments();
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
    @Override
    public Type getRawType() {
        return BaseResponse.class;
    }
}
