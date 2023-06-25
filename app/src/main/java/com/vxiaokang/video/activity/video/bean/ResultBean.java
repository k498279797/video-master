package com.vxiaokang.video.activity.video.bean;

import java.util.List;


public class ResultBean {
	private String status;
	private String msg;
	private List<ResultItemBean> data;

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

	public List<ResultItemBean> getData() {
		return data;
	}

	public void setData(List<ResultItemBean> data) {
		this.data = data;
	}
}
