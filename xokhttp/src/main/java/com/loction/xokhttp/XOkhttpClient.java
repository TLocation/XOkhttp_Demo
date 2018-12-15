package com.loction.xokhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.loction.xokhttp.builder.GetRequestBuilder;
import com.loction.xokhttp.builder.PostRequestBuilder;
import com.loction.xokhttp.builder.UpdateRequestBuilder;
import com.loction.xokhttp.cookie.CookiesManager;
import com.loction.xokhttp.utils.HttpsUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by localadmin on 2017/10/27.
 */

public class XOkhttpClient {
    private static XOkhttpClient xOkhttpClient;
    private OkHttpClient mOkHttpClient;
    public static Handler handler;
    private  Class<? extends IBaseResponse> baseClass;


    private   Context mContext;


    public Class<? extends IBaseResponse> getBaseClass() {
        return baseClass;
    }

     void setBaseClass(Class<? extends IBaseResponse> baseClass) {
        this.baseClass = baseClass;
    }

    public Context getmContext() {
        return mContext;
    }

     void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private XOkhttpClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        handler = new Handler(Looper.getMainLooper());
    }
     static XOkhttpClient  initClient(OkHttpClient okHttpClient){
    if (xOkhttpClient == null) {
        synchronized (XOkhttpClient.class) {
            if (xOkhttpClient == null) {
                xOkhttpClient = new XOkhttpClient(okHttpClient);
            }
        }
    }
    return xOkhttpClient;
}

    public static XOkhttpClient getXOkHttp() {
        if(xOkhttpClient==null){
            throw new NullPointerException("you need initialize XokhttpClient");
        }
        return xOkhttpClient;
    }




    public GetRequestBuilder get() {
        return new GetRequestBuilder(mOkHttpClient);
    }

    public PostRequestBuilder post() {
        return new PostRequestBuilder(mOkHttpClient);
    }


    public UpdateRequestBuilder upload(){return new UpdateRequestBuilder(mOkHttpClient);}

    public void cancelTag(Object tag) {
        if (tag == null) return;
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
    /**
     * 构建者类
     */
    public static class Builder {
        private final String METHOD_GET = "GET";
        private final String METHOD_POST = "POST";
        private OkHttpClient.Builder okhttpBuilder;
        private Context context;
        private   Class<? extends IBaseResponse> baseclazz;

        public Builder(OkHttpClient.Builder builder) {
            if (okhttpBuilder == null) {
                okhttpBuilder = builder;
            }
        }
        public Builder() {
            if (okhttpBuilder == null) {
                okhttpBuilder = new OkHttpClient.Builder();
            }
        }
        /**
         * 设置连接超时
         *
         * @param time     时间
         * @param timeUnit 时间单位
         * @return
         */
        public Builder setConnectTime(long time, TimeUnit timeUnit) {
            okhttpBuilder.connectTimeout(time, timeUnit);
            return this;
        }

        /**
         * 设置读写超时
         *
         * @param time     时间
         * @param timeUnit 时间单位
         * @return
         */
        public Builder setReadTime(long time, TimeUnit timeUnit) {
            okhttpBuilder.readTimeout(time, timeUnit);
            return this;
        }

        /**
         * 设置写入超时
         *
         * @param time     时间
         * @param timeUnit 时间单位
         * @return
         */
        public Builder setwriteTime(long time, TimeUnit timeUnit) {
            okhttpBuilder.writeTimeout(time, timeUnit);
            return this;
        }

        /**
         * 设置开启log  不调用默认关闭
         *
         * @return
         */
        public Builder setLog() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttpBuilder.addInterceptor(logging);
            return this;
        }

        /**
         * 添加公共参数 请求头
         * 多个公共参数的方法只能调用一次
         * 如果只添加参数 调用
         *
         * @param params
         * @param heards
         * @return
         * @throws RuntimeException
         * @see #addParams(HashMap)
         * 如果只添加请求头 调用
         * @see #addHeards(HashMap)
         * 否则会抛出 RuntimeException异常
         */
        public Builder addParams(final HashMap<String, String> params, final HashMap<String, String> heards) {

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request request = chain.request();
                    final String method = request.method();
                    final Request.Builder requestBuilder = request.newBuilder();
                    Builder.this.addParams(request, method, requestBuilder, params);

                    /**
                     * 添加请求头
                     */
                    if (heards != null && !heards.isEmpty()) {
                        final Set<String> headkeys = heards.keySet();
                        for (String headkey : headkeys) {
                            requestBuilder.addHeader(headkey, heards.get(headkey));
                        }
                    }

                    return chain.proceed(requestBuilder.build());
                }
            };
            okhttpBuilder.addInterceptor(interceptor);
            return this;
        }


        /**
         * 添加公共的请求参数
         * 注释查看
         *
         * @param params
         * @return
         * @see #addParams(HashMap, HashMap)
         */
        public Builder addParams(final HashMap<String, String> params) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request request = chain.request();
                    final String method = request.method();
                    final Request.Builder requestBuilder = request.newBuilder();
                    Builder.this.addParams(request, method, requestBuilder, params);

                    return chain.proceed(requestBuilder.build());
                }
            };
            okhttpBuilder.addInterceptor(interceptor);
            return this;
        }


        public Builder setBaseClass(Class<? extends IBaseResponse> baseClass){
            this.baseclazz = baseClass;
            return this;
        }
        /**
         * 添加公共的请求头
         * 注释查看
         *
         * @param heards 请求头集合
         * @return
         * @see #addParams(HashMap, HashMap)
         */
        public Builder addHeards(final HashMap<String, String> heards) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request request = chain.request();
                    final String method = request.method();
                    final Request.Builder requestBuilder = request.newBuilder();
                    /**
                     * 添加请求头
                     */
                    if (heards != null && !heards.isEmpty()) {
                        final Set<String> headkeys = heards.keySet();
                        for (String headkey : headkeys) {
                            requestBuilder.addHeader(headkey, heards.get(headkey));

                        }
                    }

                    return chain.proceed(requestBuilder.build());
                }
            };
            okhttpBuilder.addInterceptor(interceptor);
            return this;
        }

        /**
         * 设置https请求
         *
         * @param https 证书流
         * @return
         */
        public Builder setHttps(InputStream[] https) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(https, null, null);
            okhttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            return this;
        }


        /**
         * 持久化cookie
         *
         * 存储使用getSharedPreferences存储
         * @param cookiesManager Xokhttp内部的Cookie管理者
         *
         * @return
         */
        private Builder setCookie(Context context,CookiesManager cookiesManager) {
            this.context =context;
            okhttpBuilder.cookieJar(cookiesManager);
            return this;
        }

        /**
         * 连接失败后是否重新连接
         *
         * @param isConnect true 重新连接 false或者不调用不重新连接
         * @return
         */
        public Builder setConnectionFail(boolean isConnect) {
            okhttpBuilder.retryOnConnectionFailure(isConnect);
            return this;
        }


        public Builder setCache(Context context, int cacheSize, int timeSize) {
            /**
             * 创建缓存文件夹
             */
            File cacheFile = new File(context.getExternalCacheDir().toString(), "cache");
            /**
             * 设置缓存大小
             */
            Cache cache = new Cache(cacheFile, cacheSize);
            final String maxAge = "max-age=" + timeSize;
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Response proceed = chain.proceed(chain.request());
                    return proceed.newBuilder().removeHeader("pragma")
                            .header("Cache-Control", maxAge).build();
                }
            };
            okhttpBuilder.cache(cache)
                    .addInterceptor(interceptor);
            return this;
        }


        /**
         * 添加参数内部调用方法
         *
         * @param request        请求体
         * @param method         方法名
         * @param requestBuilder 请求体的构建对象
         * @param params         参数集合
         */
        private void addParams(Request request, String method, Request.Builder requestBuilder, HashMap<String, String> params) {
            /**
             * 判断请求方式
             */
            switch (method) {
                /**
                 * 当为get请求时 添加queryParams
                 */
                case METHOD_GET:
                    final HttpUrl.Builder builder = request.url().newBuilder();
                    if (params != null && !params.isEmpty()) {
                        final Set<String> keys = params.keySet();
                        for (String key : keys) {
                            builder.addQueryParameter(key, params.get(key));
                        }
                    }
                    requestBuilder.url(builder.build());
                    break;
                /**
                 * 当为post请求时
                 */
                case METHOD_POST:
                    final RequestBody body = request.body();

                    if (body != null && body instanceof FormBody && params != null && !params.isEmpty()) {
                        FormBody formBody = (FormBody) body;
                        Map<String, String> formBodyParamsMap = new HashMap<>();
                        int size = formBody.size();
                        for (int i = 0; i < size; i++) {
                            formBodyParamsMap.put(formBody.name(i), formBody.value(i));
                        }
                        params.putAll(formBodyParamsMap);
                        FormBody.Builder bodyBuilder = new FormBody.Builder();
                        final Set<String> keySet = params.keySet();
                        for (String key : keySet) {
                            bodyBuilder.add(key, params.get(key));

                        }
                        requestBuilder.method(request.method(), bodyBuilder.build());
                    } else if (body != null && body instanceof MultipartBody && params != null && !params.isEmpty()) {
                        MultipartBody multipartBody = (MultipartBody) body;
                        MultipartBody.Builder multBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        final Set<String> set = params.keySet();
                        for (String key : set) {
                            multBuilder.addFormDataPart(key, params.get(key));
                        }
                        List<MultipartBody.Part> parts = multipartBody.parts();
                        for (MultipartBody.Part part : parts) {
                            multBuilder.addPart(part);
                        }
                        requestBuilder.post(multBuilder.build());
                    }

                    break;
                default:
            }
        }

        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request();

                return null;
            }
        };

        public XOkhttpClient builder() {
            XOkhttpClient xOkhttpClient = initClient(okhttpBuilder.build());
            if(context!=null){
                xOkhttpClient.setmContext(context);
            }
            if(baseclazz!=null){
                xOkhttpClient.setBaseClass(baseclazz);
            }
            return xOkhttpClient;
        }
    }
}
