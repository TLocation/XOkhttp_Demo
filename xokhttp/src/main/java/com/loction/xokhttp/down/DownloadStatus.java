package com.loction.xokhttp.down;

/**
 * 项目名称: XOkhttp
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 15:57
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class DownloadStatus {
    public static final int STATUS_DEFAULT = -1;        //初始状态
    public static final int STATUS_WAIT = 0;            //队列等待中
    public static final int STATUS_PAUSE = 1;           //暂停
    public static final int STATUS_DOWNLOADING = 2;     //下载中
    public static final int STATUS_FINISH = 3;          //下载完成
    public static final int STATUS_FAIL = 4;            //下载失败
}
