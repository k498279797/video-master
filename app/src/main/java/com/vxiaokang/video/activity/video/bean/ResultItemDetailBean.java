package com.vxiaokang.video.activity.video.bean;

public class ResultItemDetailBean {
	//网站
	private String id;
	private String title;
	private String type; // 1
	private String image;
	private String url;
	
	//明星
	private String name;
	private String avatar;
	private String cover_img;
	
	private String is_vip; //1
	private String image2;
	private String index_nums; // 1 
	private String plays; // 54.1万
	private String time_len;// 1:55:08
	//https://v4.cpwsgujranwala.com/20220712/ffYkua0A/2000kb/hls/index.m3u8
	private String media;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCover_img() {
		return cover_img;
	}

	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}

	public String getIs_vip() {
		return is_vip;
	}

	public void setIs_vip(String is_vip) {
		this.is_vip = is_vip;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getIndex_nums() {
		return index_nums;
	}

	public void setIndex_nums(String index_nums) {
		this.index_nums = index_nums;
	}

	public String getPlays() {
		return plays;
	}

	public void setPlays(String plays) {
		this.plays = plays;
	}

	public String getTime_len() {
		return time_len;
	}

	public void setTime_len(String time_len) {
		this.time_len = time_len;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}
}
