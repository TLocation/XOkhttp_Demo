package com.loction.xokhttp_demo;

import com.loction.xokhttp.IBaseResponse;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/9/3 18:29
 * description：
 */

public class BaseResponse<T> implements IBaseResponse<T> {
  private int errorCode;
  private String errorMsg;
  private T data;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public int getStatusCode() {
		return errorCode;
	}

	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

	@Override
	public boolean isOk() {
		return errorCode==0;
	}
}
