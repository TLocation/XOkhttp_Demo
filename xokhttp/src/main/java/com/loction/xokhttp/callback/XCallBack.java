package com.loction.xokhttp.callback;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.IResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by localadmin on 2017/11/10.
 */

public class XCallBack implements Callback {
    private IResponse iResponse;

    public XCallBack(IResponse iResponse) {
        this.iResponse = iResponse;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        XOkhttpClient.handler.post(new Runnable() {
            @Override
            public void run() {
                iResponse.onFail(0, e.getMessage());
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {

            iResponse.onSuccful(response);
        } else {
            XOkhttpClient.handler.post(new Runnable() {
                @Override
                public void run() {

                    iResponse.onFail(response.code(), response.message());

                }
            });
        }


    }
}
