package com.loction.xokhttp.down;

import java.io.File;

/**
 * 项目名称: XOkhttp
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/5 0005 15:51
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public interface DownLoadTaskListener {
    /**
     * 任务开始
     *
     * @param taskId        taskId
     * @param completeBytes 断点续传 已经完成的Bytes
     * @param totalBytes    总大小
     */
    void onStart(String taskId, long completeBytes, long totalBytes);

    /**
     * 任务下载1中
     *
     * @param taskId       taskId
     * @param currentBytes 当前已经下载的bytes
     * @param totalBytes   总大小
     */
    void onProgress(String taskId, long currentBytes, long totalBytes);

    /**
     * 任务暂停
     *
     * @param taskId       taskId
     * @param currentBytes 当前已经下载的bytes
     * @param totalBytes   总大小
     */
    void onPause(String taskId, long currentBytes, long totalBytes);


    /**
     * 任务完成
     *
     * @param taskId taskId
     * @param file   下载好的文件
     */
    void onFinsh(String taskId, File file);

    /**
     * 下载失败
     *
     * @param taskId       taskId
     * @param errorMessage 失败原因
     */
    void onFailure(String taskId, String errorMessage);
}
