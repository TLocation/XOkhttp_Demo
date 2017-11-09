package com.loction.xokhttp.builder;

import com.loction.xokhttp.XOkhttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by localadmin on 2017/11/9.
 */

public abstract class BaseRequestParamsBuilder<T extends BaseRequestParamsBuilder> extends BaseRequestBuilder<BaseRequestParamsBuilder> {
    protected Map<String, String> params;

    public BaseRequestParamsBuilder(XOkhttpClient xOkhttpClient) {
        super(xOkhttpClient);
    }

    public T addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return (T) this;
    }
   public T params(Map<String,String> params){

       this.params = params;
       return (T) this;
   }
}
