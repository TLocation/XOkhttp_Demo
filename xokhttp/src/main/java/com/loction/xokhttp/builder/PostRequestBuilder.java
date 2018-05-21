package com.loction.xokhttp.builder;

import android.text.TextUtils;
import android.util.Log;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.callback.XCallBack;
import com.loction.xokhttp.response.IResponse;
import com.loction.xokhttp.utils.MD5Encrypt;

import java.util.HashMap;
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

    public PostRequestBuilder(OkHttpClient xOkhttpClient) {
        super(xOkhttpClient);
    }

    public PostRequestBuilder putJson(String json) {
        this.json = json;

        return this;
    }


    @Override
    public void enqueue(IResponse iResponse) {
        Request.Builder builder = new Request.Builder();
        String wangshangyuanyang = MD5Encrypt.encopt(bodyJson.toString(), XOkhttpClient.KEY_ENCOUPT);
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(XOkhttpClient.KEY_ENCOUPT, wangshangyuanyang);
        Log.e("TAG", "json===>" + bodyJson.toString());
        Log.e("TAG", "params===>" + params.toString());
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
