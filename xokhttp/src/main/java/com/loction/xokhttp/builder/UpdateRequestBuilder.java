package com.loction.xokhttp.builder;

import com.loction.xokhttp.response.IResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

/**
 * Created by localadmin on 2017/11/11.
 */

public class UpdateRequestBuilder extends BaseRequestParamsBuilder<UpdateRequestBuilder> {

    public UpdateRequestBuilder(OkHttpClient xOkhttpClient) {
        super(xOkhttpClient);
    }

    @Override
    public void enqueue(IResponse iResponse) {


    }

    private void appendParams(MultipartBody.Builder builder, Map<String, String> params) {


    }


}
