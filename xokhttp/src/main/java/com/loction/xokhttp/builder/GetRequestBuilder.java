package com.loction.xokhttp.builder;

import android.text.TextUtils;

import com.loction.xokhttp.callback.XCallBack;
import com.loction.xokhttp.response.IResponse;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by localadmin on 2017/11/10.
 */

public class GetRequestBuilder extends BaseRequestParamsBuilder<GetRequestBuilder> {


    public GetRequestBuilder(OkHttpClient xOkhttpClient, Object tag) {
        super(xOkhttpClient, tag);
    }

    @Override
    public void onEnqueue(IResponse iResponse) {
        if (TextUtils.isEmpty(url)) {
            new RuntimeException("url == null");
        }
        Request.Builder builder = new Request.Builder();
        if (params != null && !params.isEmpty()) {
            url = appendParams(url, params);
        }
        builder.url(url).get();

        appendHeaders(builder, headers);
        if (tag != null) {
            builder.tag(tag);
        }
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }
        xOkhttpClient.newCall(builder.build())
                .enqueue(new XCallBack(iResponse));


    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();

    }


}
