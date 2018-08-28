package com.loction.xokhttp.builder;

import android.os.Handler;
import android.text.TextUtils;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.IResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by localadmin on 2017/11/9.
 */

public abstract class BaseRequestBuilder<T extends BaseRequestBuilder> {
    /**
     * 请求头集合
     */
    protected Map<String, String> headers;
    /**
     * 请求地址url
     */
    protected String url;
    /**
     * 请求标记 tag
     */
    protected Object tag;
    /**
     * XOkHttpClient对象
     */
    protected OkHttpClient xOkhttpClient;

    /**
     * 单个请求缓存模式
     */
    protected CacheControl cacheControl;

    public BaseRequestBuilder(OkHttpClient xOkhttpClient) {
        this.xOkhttpClient = xOkhttpClient;
    }

    /**
     * 添加请求头
     *
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return (T) this;
    }

    /**
     * 添加请求头集合
     *
     * @param headers
     * @return
     */
    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    /**
     * 添加访问地址 url
     *
     * @param url
     * @return
     */
    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    /**
     * 添加标记 tag
     *
     * @param tag
     * @return
     */
    public T tag(Object tag) {
        this.tag = tag;

        return (T) this;
    }

    /**
     * 设置缓存模式为只读缓存
     *
     * @return
     */
    public T setToCache() {

        cacheControl = CacheControl.FORCE_CACHE;
        return (T) this;
    }

    /**
     * 设置缓存模式为只读网络
     *
     * @return
     */
    public T setToNetwork() {
        cacheControl = CacheControl.FORCE_NETWORK;
        return (T) this;
    }

    /**
     * 设置缓存时间
     * 当两次请求时间超过这个时间便会从网络获取 否则从缓存获取
     *
     * @param time
     * @param timeUnit
     * @return
     */
    public T setCacheTime(int time, TimeUnit timeUnit) {
        cacheControl = new CacheControl.Builder()
                .maxAge(time, timeUnit).build();
        return (T) this;
    }


    /**
     * path请求参数
     * @param key
     * @param value
     * @return
     */
    public T addPathParam(String key,String value){
        if(TextUtils.isEmpty(url)){
            throw  new NullPointerException("url==null");
        }
        url = url.replace("{"+key+"}",value);
        return (T) this;
    }
    /**
     * 内部调用拼接参数
     *
     * @param requestBuilder
     * @param headers
     */
    protected void appendHeaders(Request.Builder requestBuilder, Map<String, String> headers) {
        if(headers==null||headers.isEmpty()){
            return;
        }
        final Set<String> keys = headers.keySet();
        for (String key : keys) {
            requestBuilder.addHeader(key, headers.get(key));
        }
    }

    public abstract void enqueue(IResponse iResponse);

}
