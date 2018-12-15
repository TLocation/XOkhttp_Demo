package com.loction.xokhttp.response;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.utils.Responseer;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 项目名称: XOkhttp
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 14:48
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class RawResponseHandler implements IResponse {
    @Override
    public void onSuccessful(final Response response) {
        final ResponseBody body = response.body();
        final String bodyStr;
        try {
            bodyStr = body.string();
        } catch (IOException e) {
            e.printStackTrace();
            XOkhttpClient.handler.post(new Runnable() {
                @Override
                public void run() {
                    onFail(response.code(), "error read msg");
                }
            });
            return;
        } finally {
            response.close();
        }
        XOkhttpClient.handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccful(bodyStr);
            }
        });


    }

    public abstract void onSuccful(String bodyStr);
}
