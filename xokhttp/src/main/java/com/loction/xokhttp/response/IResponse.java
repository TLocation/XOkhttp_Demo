package com.loction.xokhttp.response;

import okhttp3.Response;

/**
 * Created by localadmin on 2017/11/9.
 */

public interface IResponse {
    void onSuccful(Response response);

    void onFail(int errorCode, String errorMessage);
}
