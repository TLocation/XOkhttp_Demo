package com.loction.xokhttp.response;

import java.io.File;

/**
 * 项目名称: XOkhttp
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 15:58
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class DownResponseHandler {

    public void onStart(long totalBytes) {
    }

    public void onCancle() {
    }

    public abstract void onFinsh(File downLoadFile);

    public abstract void onProgress(long currentBytes, long totalBytes);

    public abstract void onFailure(String errorMessage);
}
