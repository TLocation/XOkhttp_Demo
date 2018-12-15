package com.loction.xokhttp.response;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.loction.xokhttp.XOkhttpClient;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

	abstract void onSuccessful(T response);

	@Override
	public void onSuccessful(final Response response) {
		if (response.isSuccessful()) {
			try {
				final String string = response.body().string();
				XOkhttpClient.handler.post(new Runnable() {
					@Override
					public void run() {
						Gson gson = new Gson();
						onSuccessful((T) gson.fromJson(string, mType));
					}
				});
			} catch (final IOException e) {
				e.printStackTrace();
				XOkhttpClient.handler.post(new Runnable() {

					@Override
					public void run() {
             onFail(0,e.getMessage());
					}
				});
			}
		}else{
			XOkhttpClient.handler.post(new Runnable() {
				@Override
				public void run() {
				onFail(response.code(),response.message());
				}
			});
		}
	}


}
