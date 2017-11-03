package com.loction.xokhttp;

import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by localadmin on 2017/10/27.
 */

public class XOkhttpClient {


    public static class Builder {

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
         * @see #addParams(HashMap)
         * 如果只添加请求头 调用
         * @see #addHeards(HashMap)
         * 否则会跑出
         * @throws RuntimeException
         * @param params
         * @param heards
         * @return
         */
        public Builder addParams(HashMap<String, String> params, HashMap<String, String> heards) {

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder =chain.request().newBuilder();




                    return null;
                }
            };
            return this;
        }

        /**
         * 添加公共的请求参数
         * 注释查看
         * @see #addParams(HashMap, HashMap)
         * @param params
         * @return
         */
        public Builder addParams(HashMap<String,String> params){
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {



                    return null;
                }
            };
            return this;
        }

        /**
         * 添加公共的请求头
         * 注释查看
         * @see #addParams(HashMap, HashMap)
         * @param params  请求头集合
         * @return
         */
        public Builder addHeards(HashMap<String,String> params){
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {



                    return null;
                }
            };
            return this;
        }


    }
}
