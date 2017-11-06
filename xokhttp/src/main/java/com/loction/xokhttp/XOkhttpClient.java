package com.loction.xokhttp;

import android.os.Build;

import com.loction.xokhttp.utils.HttpsUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CompletionService;
import java.util.concurrent.TimeUnit;

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


    public static class Builder {
        private final String METHOD_GET = "GET";
        private final String METHOD_POST = "POST";
        private OkHttpClient.Builder okhttpBuilder;

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
    }
}