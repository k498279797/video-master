package com.vxiaokang.video.bean;

import java.util.List;

public class IndexVideoBean {
    private String title;
    private String url;
    private String sourceId;
    private String desc;
    private List<VideoInfoBean> videoList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<VideoInfoBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoInfoBean> videoList) {
        this.videoList = videoList;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
