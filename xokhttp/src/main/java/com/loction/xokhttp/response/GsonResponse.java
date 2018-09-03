package com.loction.xokhttp.response;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.loction.xokhttp.XOkhttpClient;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.logging.Handler;

import okhttp3.Response;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/8/31 13:08
 * description：
 */

public abstract class GsonResponse<T> implements IResponse {
	private final Type mType;
	public GsonResponse() {
		Type myclass = getClass().getGenericSuperclass();
		if (myclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		ParameterizedType parameter = (ParameterizedType) myclass;
		mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
	}
abstract void onSuccful(T response);
	@Override
	public void onSuccful(Response response) {
        if(response.isSuccessful()){
			try {
				final String string = response.body().string();
				XOkhttpClient.handler.post(new Runnable() {
					@Override
					public void run() {
						Gson gson = new Gson();
						onSuccful((T) gson.fromJson(string,mType));
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
				XOkhttpClient.handler.post(new Runnable() {

					@Override
					public void run() {

					}
				});
			}
		}
	}


}
