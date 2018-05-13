package com.loction.xokhttp.down;

import com.loction.xokhttp.XOkhttpClient;
import com.loction.xokhttp.response.DownResponseHandler;

import java.io.File;

import okhttp3.Call;

/**
 * 项目名称: XOkhttp
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 15:46
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class DownTask {
    private XOkhttpClient xOkhttpClient;
    private String mTaskId;//taskid
    private String mUrl;//下载地址
    private String mFilePath;//保存文件地址
    private long mCompleteBytes;//断点续传已经完成的字节数
    private long mCurrentBytes;//当前总共下载的byte
    private long mTotalBytes;//文件总大小
    private int mStatus;//Task状态
    private long mNextSaveBytes = 0l;//距离下次保存下载进度的bytes
    private Call mCall;//本次请求
    private DownLoadTaskListener downLoadTaskListener;//task下载事件
    private DownResponseHandler downResponseHandler;//下载监听


    public DownTask() {
        mTaskId = "";
        mUrl ="";
        mFilePath = "";
        mCompleteBytes = 0L;
        mCurrentBytes = 0L;
        mTotalBytes = 0L;
        mStatus = DownloadStatus.STATUS_DEFAULT;       //初始默认状态
        mNextSaveBytes = 0L;
        downResponseHandler = new DownResponseHandler() {
            @Override
            public void onStart(long totalBytes) {
                mTotalBytes = mCompleteBytes + totalBytes;
                downLoadTaskListener.onStart(mTaskId, mCompleteBytes, mTotalBytes);
            }

            @Override
            public void onFinsh(File downLoadFile) {
                mStatus = DownloadStatus.STATUS_FINISH;
                mCurrentBytes = mTotalBytes;
                mCompleteBytes = mTotalBytes;
                downLoadTaskListener.onFinsh(mTaskId, downLoadFile);
            }

            @Override
            public void onProgress(long currentBytes, long totalBytes) {
                if(mStatus == DownloadStatus.STATUS_DOWNLOADING) {
                    mNextSaveBytes += mCompleteBytes + currentBytes - mCurrentBytes;        //叠加每次增加的bytes
                    mCurrentBytes = mCompleteBytes + currentBytes;      //当前已经下载好的bytes
                    downLoadTaskListener.onProgress(mTaskId, mCurrentBytes, mTotalBytes);
                } else if(mStatus == DownloadStatus.STATUS_PAUSE) {
                    mCompleteBytes = mCurrentBytes;
                    if(!mCall.isCanceled()) {
                        mCall.cancel();
                    }
                } else {
                    mCompleteBytes = mCurrentBytes;
                    if(!mCall.isCanceled()) {
                        mCall.cancel();
                    }
                }
            }

            @Override
            public void onCancle() {
                downLoadTaskListener.onPause(mTaskId, mCurrentBytes, mTotalBytes);
            }

            @Override
            public void onFailure(String errorMessage) {
                mStatus = DownloadStatus.STATUS_FAIL;

                downLoadTaskListener.onFailure(mTaskId, errorMessage);
            }
        };

    }
}
