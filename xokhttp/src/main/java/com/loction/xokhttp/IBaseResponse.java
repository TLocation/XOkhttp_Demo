package com.loction.xokhttp;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/8/27 23:21
 * description：
 */

public interface IBaseResponse<T> {
	T getData();

	int getStatusCode();

	String getErrorMsg();

	boolean isOk();



}
