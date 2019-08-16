package com.loction.xokhttp.builder;

import android.text.TextUtils;

import com.loction.xokhttp.callback.XCallBack;
import com.loction.xokhttp.response.IResponse;

import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by localadmin on 2017/11/11.
 */

public class PostRequestBuilder extends BaseRequestParamsBuilder<PostRequestBuilder> {


    public PostRequestBuilder(OkHttpClient xOkhttpClient, Object tag) {
        super(xOkhttpClient, tag);
    }

    @Override
  protected   void onEnqueue(IResponse iResponse) {
        Request.Builder builder = new Request.Builder();
        if (TextUtils.isEmpty(url)) {
            new RuntimeException("url == null");
        }
        builder.url(url);

        appendHeaders(builder, headers);
        if (tag != null) {
            builder.tag(tag);
        }
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }


        if (!TextUtils.isEmpty(json)) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; " +
                    "charset=utf-8"), json);
            builder.post(body);
        } else {
            FormBody.Builder fromBuilder = new FormBody.Builder();
            appendParams(fromBuilder, params);
            builder.post(fromBuilder.build());
        }

        xOkhttpClient.newCall(builder.build())
                .enqueue(new XCallBack(iResponse));


    }


    private void appendParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            final Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.add(key, params.get(key));
            }
        }


    }


}
