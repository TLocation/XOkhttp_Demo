package com.loction.xokhttp_demo;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/11/11 15:56
 * description：
 */

public class UploadResponse {

	/**
	 * ret : 200
	 * data : {"err_code":0,"err_msg":"","url":"http://cdn7.okayapi.com/20181111150907_02b6874c17d20a6b658eafbb039617f3.jpg"}
	 * msg : 当前请求接口：App.CDN.UploadImg
	 */

	private int ret;
	private DataBean data;
	private String msg;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class DataBean {
		/**
		 * err_code : 0
		 * err_msg :
		 * url : http://cdn7.okayapi.com/20181111150907_02b6874c17d20a6b658eafbb039617f3.jpg
		 */

		private int err_code;
		private String err_msg;
		private String url;

		public int getErr_code() {
			return err_code;
		}

		public void setErr_code(int err_code) {
			this.err_code = err_code;
		}

		public String getErr_msg() {
			return err_msg;
		}

		public void setErr_msg(String err_msg) {
			this.err_msg = err_msg;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
