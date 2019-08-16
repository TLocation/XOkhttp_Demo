package com.loction.xokhttp.builder;

import android.text.TextUtils;

import com.loction.xokhttp.callback.ProgressRequestBody;
import com.loction.xokhttp.callback.XCallBack;
import com.loction.xokhttp.response.IResponse;
import com.loction.xokhttp.response.UploadResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by localadmin on 2017/11/11.
 */

public class UpdateRequestBuilder extends BaseRequestParamsBuilder<UpdateRequestBuilder> {

	private Map<String, File> files;

	public UpdateRequestBuilder(OkHttpClient xOkhttpClient, Object tag) {
		super(xOkhttpClient, tag);
	}


	public UpdateRequestBuilder addFile(String filekey, File file) {
		if (files == null) {
			files = new HashMap<>();
		}
		if (file == null || !file.exists()) {
			return this;
		}
		files.put(filekey, file);
		return this;
	}


	@Override
	public void onEnqueue(IResponse iResponse) {

		Request.Builder builder = new Request.Builder();
		if (TextUtils.isEmpty(url)) {
			new RuntimeException("url == null");
		}
		builder.url(url);
		if (tag != null) {
			builder.tag(tag);
		}
		if (cacheControl != null) {
			builder.cacheControl(cacheControl);
		}
		MultipartBody.Builder multipartBody = new MultipartBody.Builder();
		//form 表单上传
		multipartBody.setType(MultipartBody.FORM);
		appendParams(multipartBody, params);
		appendFile(multipartBody, files,iResponse);
		if(iResponse instanceof UploadResponse){
			builder.post(new ProgressRequestBody(multipartBody.build(), (UploadResponse) iResponse));
		}else{
			builder.post(multipartBody.build());
		}
		xOkhttpClient.newCall(builder.build()).enqueue(new XCallBack(iResponse));
	}

	private void appendParams(MultipartBody.Builder builder, Map<String, String> params) {
		if (params == null || params.isEmpty()) {
			return;
		}
		Set<String> keys = params.keySet();
		for (String key : keys) {
			String value = params.get(key);
			builder.addFormDataPart(key, value);
		}
	}

	private void appendFile(MultipartBody.Builder builder, Map<String, File> files,IResponse iResponse) {
		if (files == null || files.isEmpty()) {
			return;
		}
		Set<String> keys = files.keySet();
		for (String key : keys) {
			File file = files.get(key);
			RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
			builder.addFormDataPart(key, file.getName(), requestBody);
		}
	}

}
