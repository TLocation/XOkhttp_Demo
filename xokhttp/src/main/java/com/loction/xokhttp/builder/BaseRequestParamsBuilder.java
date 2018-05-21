package com.loction.xokhttp.builder;

import com.loction.xokhttp.utils.MD5Utils;

import org.json.JSONException;
import org.json.JSONObject;

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

    private final String KEY_CHECK = "check";
    private final String KEY_HEADER = "header";
    private final String KEY_DATA = "data";

    protected JSONObject bodyJson;
    protected JSONObject headerJson;

    public T putJson(String json) {
        this.json = json;
        return (T) this;
    }

    public T putBodyJson(JSONObject jsonObject) {
        if (bodyJson == null) {
            bodyJson = new JSONObject();
            headerJson = new JSONObject();
            try {
                bodyJson.put(KEY_HEADER, headerJson);
                bodyJson.put(KEY_DATA, jsonObject);
            } catch (JSONException e) {
                throw new RuntimeException("body拼接异常");
            }
        }
        String encrypt = MD5Utils.encrypt(jsonObject.toString());
        try {
            headerJson.put(KEY_CHECK, encrypt);
        } catch (JSONException e) {
            throw new RuntimeException("check参数拼接异常");
        }

        return (T) this;
    }

    public T putHeaderJson(String key, String Value) {
        try {
            headerJson.put(key, Value);
        } catch (JSONException e) {
            throw new RuntimeException("header参数拼接异常");
        }
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
