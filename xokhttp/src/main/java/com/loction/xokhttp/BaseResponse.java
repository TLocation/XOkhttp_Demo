package com.loction.xokhttp;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/8/27 23:21
 * description：
 */

public class BaseResponse<T> {

	/**
	 * errorCode  为0成功
	 *
	 */
	private int errorCode;
	private String errorMsg;
	private T data;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
