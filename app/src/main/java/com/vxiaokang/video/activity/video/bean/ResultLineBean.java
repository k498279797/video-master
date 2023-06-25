package com.vxiaokang.video.activity.video.bean;

/**
 * 请求线路
 * @author Administrator
 *
 */
public class ResultLineBean {
	private String status;
	private String msg;
	private ResultLineItemBean data;
	public String getStatus() {
		return status; 
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ResultLineItemBean getData() {
		return data;
	}
	public void setData(ResultLineItemBean data) {
		this.data = data;
	}
	
}
