package com.loction.xokhttp.callback;

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
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
