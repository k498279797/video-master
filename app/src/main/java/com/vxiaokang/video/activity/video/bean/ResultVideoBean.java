package com.vxiaokang.video.activity.video.bean;

public class ResultVideoBean {
	private ResultVideoItemBean data;
	private String msg;
	private String status;
	public ResultVideoItemBean getData() {
		return data;
	}
	public void setData(ResultVideoItemBean data) {
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
