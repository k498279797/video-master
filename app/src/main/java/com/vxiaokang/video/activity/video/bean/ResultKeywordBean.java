package com.vxiaokang.video.activity.video.bean;
/**
 * 关键字搜索
 * @author Administrator
 *
 */
public class ResultKeywordBean { 
	private ResultKeywordItemBean data;
	private String msg;
	private String status;
	public ResultKeywordItemBean getData() {
		return data;
	}
	public void setData(ResultKeywordItemBean data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
