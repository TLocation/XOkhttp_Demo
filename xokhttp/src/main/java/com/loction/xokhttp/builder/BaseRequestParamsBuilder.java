package com.loction.xokhttp.builder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by localadmin on 2017/11/9.
 */

public abstract class BaseRequestParamsBuilder<T extends BaseRequestParamsBuilder> extends
        BaseRequestBuilder<BaseRequestParamsBuilder> {
    protected Map<String, String> params;
    protected String json;

    public T putJson(String json) {
        this.json = json;
        return (T) this;
    }

    public BaseRequestParamsBuilder(OkHttpClient xOkhttpClient) {
        super(xOkhttpClient);
    }


    /***
     * 添加参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return (T) this;
    }

    public T params(Map<String, String> params) {

        this.params = params;
        return (T) this;
    }
}
