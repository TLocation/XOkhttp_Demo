package com.loction.xokhttp.response;

import com.loction.xokhttp.XOkhttpClient;

import okhttp3.Response;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/11 15:24
 * description：
 */

public abstract class UploadResponse<T> implements IResponse {


	/**
	 * 正常上传
	 *
	 * @param uploadLength  已上传大小
	 * @param contentLength 总大小
	 */
	public abstract void onProgress(long uploadLength, long contentLength);

	/**
	 * 上传完成
	 *
	 * @param contentLength
	 */
	public abstract void uploadDone(long contentLength);

	@Override
	public void onSuccessful(final Response response) {
		if (response.isSuccessful()) {

			successfulPost(response);
		} else {
			XOkhttpClient.handler.post(new Runnable() {
				@Override
				public void run() {
					onFail(response.code(), response.message());
				}
			});
		}
	}

	private void successfulPost(Response response) {
		GsonResponse<T> gsonResponseHandler = new GsonResponse<T>() {
			@Override
			public void onSuccessful(T response) {
				onUploadSuccessful(response);
			}

			@Override
			public void onFail(int errorCode, String errorMessage) {
				UploadResponse.this.onFail(errorCode, errorMessage);
			}
		};
		gsonResponseHandler.onSuccessful(response);
	}

	public abstract void onUploadSuccessful(T response);
}
