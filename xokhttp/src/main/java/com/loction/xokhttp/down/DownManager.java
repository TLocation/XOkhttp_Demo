package com.loction.xokhttp.down;

import com.loction.xokhttp.XOkhttpClient;

import java.util.List;

/**
 * 项目名称: XOkhttp
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 15:39
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public abstract class DownManager {
    private final String TAG = DownManager.class.getSimpleName();
    private boolean DEBUG = false;
    private XOkhttpClient xOkhttpClient;
    private int mMaxDownloadIngNum;  //最大同时下载数量
    private int mCurrDownloadIngNum;//当前同时下载数量
    private  int mSaveProgressBytes;//每下载一次后保存一次下载进度
    private List<String> mDownLoadtaskQuene;  //下载队列 （taskId)
//    private HashMap<String,>

}
