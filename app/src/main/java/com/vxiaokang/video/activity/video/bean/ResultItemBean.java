package com.vxiaokang.video.activity.video.bean;

import java.util.List;



public class ResultItemBean {
	// ad240  starSideslip 热门明星   
	// horizontalChartSmall
	private String type;
	private String modularId; 
	private String sourceId;
	private String showNumber;
	private String name;
	private String desc;
	private String sourceAddr; // video_special
	
	private List<ResultItemDetailBean> data;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModularId() {
		return modularId;
	}

	public void setModularId(String modularId) {
		this.modularId = modularId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(String showNumber) {
		this.showNumber = showNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public void setSourceAddr(String sourceAddr) {
		this.sourceAddr = sourceAddr;
	}

	public List<ResultItemDetailBean> getData() {
		return data;
	}

	public void setData(List<ResultItemDetailBean> data) {
		this.data = data;
	}
}
