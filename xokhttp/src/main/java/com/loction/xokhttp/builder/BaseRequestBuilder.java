package com.loction.xokhttp.builder;

import android.os.Handler;
import android.text.TextUtils;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.callback.XCallBack;
import com.loction.xokhttp.response.IResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
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


    protected IResponse response;

    public BaseRequestBuilder(OkHttpClient xOkhttpClient,Object tag) {
        this.xOkhttpClient = xOkhttpClient;
        this.tag = tag;
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
        url = replacePathParams(url,key,value);
        return (T) this;
    }

    /**
     * add url with path params
     * eg https://www.wanandroid,com/json/{page}/10
     *   to https://www.wanandroid,com/json/1/10
     * @return replace url
     * @throws NullPointerException when url or key or value is null
     */
    private String replacePathParams(String url,String key,String value){
        if(isEmpty(url) || isEmpty(key) || isEmpty(value)){
            throw new NullPointerException("url or key or value is null");
        }
        return url.replace("{" + key +"}" , value);
    }

    private boolean isEmpty(String msg){
        return TextUtils.isEmpty(msg);
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

     protected abstract void onEnqueue(IResponse iResponse);


     public final  void enqueue(IResponse  iResponse){
              this.response = iResponse;
              onEnqueue(this.response);
     }

     public void requestCall(Call call){
         call.enqueue(new XCallBack(this.response));
     }

}
