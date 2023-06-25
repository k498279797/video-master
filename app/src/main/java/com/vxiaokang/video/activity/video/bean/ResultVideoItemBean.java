package com.vxiaokang.video.activity.video.bean;

import java.util.List;

public class ResultVideoItemBean {
	private String total;
	private String page;
	private Boolean more;
	private String name;
	private List<ResultVideoItemInfoBean> list;
	private ResultVideoItemInfoBean info;
	private ResultVideoItemInfoBean media;
	
	public String getTotal() { 
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public Boolean getMore() {
		return more;
	}
	public void setMore(Boolean more) {
		this.more = more;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ResultVideoItemInfoBean> getList() {
		return list;
	}
	public void setList(List<ResultVideoItemInfoBean> list) {
		this.list = list;
	}
	public ResultVideoItemInfoBean getInfo() {
		return info;
	}
	public void setInfo(ResultVideoItemInfoBean info) {
		this.info = info;
	}
	public ResultVideoItemInfoBean getMedia() {
		return media;
	}
	public void setMedia(ResultVideoItemInfoBean media) {
		this.media = media;
	}
	
}
