package com.loction.xokhttp;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/8/27 23:21
 * description：
 */

public interface IBaseResponse<T> {
	/**
	 * 正真实体类
	 * @return
	 */
	T getData();

	/**
	 * 状态码
	 * @return
	 */
	int getStatusCode();

	/**
	 * 异常信息
	 * @return
	 */
	String getErrorMsg();

	/**
	 * 请求是否成功
	 * @return
	 */
	boolean isOk();



}
