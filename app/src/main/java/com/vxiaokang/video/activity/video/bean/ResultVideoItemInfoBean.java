package com.vxiaokang.video.activity.video.bean;

import java.util.List;
import java.util.Map;

public class ResultVideoItemInfoBean {
	private String id;
	private String name;
	private String likes;
	private String dislikes;
	private String plays;
	private String time_len;
	private String img3;
	private String image; 
	
	private String img;
	private String playurl;


	private List<Map<String,String>> media_url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public String getDislikes() {
		return dislikes;
	}
	public void setDislikes(String dislikes) {
		this.dislikes = dislikes;
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
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public List<Map<String, String>> getMedia_url() {
		return media_url;
	}
	public void setMedia_url(List<Map<String, String>> media_url) {
		this.media_url = media_url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public String getPlayurl() {
		return playurl;
	}

	public void setPlayurl(String playurl) {
		this.playurl = playurl;
	}
}
