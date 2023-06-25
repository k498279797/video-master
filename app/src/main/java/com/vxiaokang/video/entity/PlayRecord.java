package com.vxiaokang.video.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * 记录
 */
@Entity
public class PlayRecord {
    @Id(autoincrement = true)
    private Long id;
    private Long type;
    private String videoKey;
    private String content; //url地址
    private String title;
    private String keywords;
    private String description;
    private String coverImage;
    private int position;
    private String link;
    private String sourceLink;
    private Date createDate;
    private Date update;
    private String remark; //备注
    private String param1; //
    private String param2;  //
    private String param3; //

    @Generated(hash = 481160970)
    public PlayRecord(Long id, Long type, String videoKey, String content,
            String title, String keywords, String description, String coverImage,
            int position, String link, String sourceLink, Date createDate,
            Date update, String remark, String param1, String param2,
            String param3) {
        this.id = id;
        this.type = type;
        this.videoKey = videoKey;
        this.content = content;
        this.title = title;
        this.keywords = keywords;
        this.description = description;
        this.coverImage = coverImage;
        this.position = position;
        this.link = link;
        this.sourceLink = sourceLink;
        this.createDate = createDate;
        this.update = update;
        this.remark = remark;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    @Generated(hash = 1408486030)
    public PlayRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }
}