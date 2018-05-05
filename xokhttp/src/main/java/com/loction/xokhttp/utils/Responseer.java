package com.loction.xokhttp.utils;

import okhttp3.Response;

/**
 * 项目名称: WanAndroid
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 14:38
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Responseer<T> {
    private T t;
    private Response response;
    private int code;

    public Responseer(T t, Response response) {
        this.t = t;
        this.response = response;
        this.code = response.code();
    }

    public T body() {
        return t;
    }

    public Response response() {
        return response;
    }

    public int code() {
        return code;
    }
}
