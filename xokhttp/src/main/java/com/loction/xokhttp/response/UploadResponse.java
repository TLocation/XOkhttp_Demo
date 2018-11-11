package com.loction.xokhttp.response;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.utils.Responseer;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/11 15:24
 * description：
 */

public abstract class UploadResponse<T> implements IResponse {




	public abstract void onProgress(long uploadLength, long contentLength);

	public abstract void uploadDone(long contentLength);

	@Override
	public void onSuccful(Response response) {
		GsonResponse<T> gsonResponseHandler = new GsonResponse<T>() {
			@Override
			public void onSuccful(T response) {
				onUploadSuccful(response);
			}

			@Override
			public void onFail(int errorCode, String errorMessage) {
				UploadResponse.this.onFail(errorCode,errorMessage);
			}
		};
		gsonResponseHandler.onSuccful(response);
	}

	public abstract void onUploadSuccful(T response);
}
