package com.vxiaokang.video.bean;


import java.util.List;

/**
 * s视频数据
 */
public class VideoDataBean {

    private String code;
    private String msg;
    private String total;
    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        private String videoId;
        private String userId;
        private String title;
        private String keywords;
        private String description;
        private String coverImage;
        private String link;
        private String sourceLink;
        private String upVote;
        private String shareNum;
        private String shareFlag;
        private String freeFlag;
        private String userName;
        private String avatar;
        private String collectionFlag;
        private String adFlag;
        private String adType;
        private String duration;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getUpVote() {
            return upVote;
        }

        public void setUpVote(String upVote) {
            this.upVote = upVote;
        }

        public String getShareNum() {
            return shareNum;
        }

        public void setShareNum(String shareNum) {
            this.shareNum = shareNum;
        }

        public String getShareFlag() {
            return shareFlag;
        }

        public void setShareFlag(String shareFlag) {
            this.shareFlag = shareFlag;
        }

        public String getFreeFlag() {
            return freeFlag;
        }

        public void setFreeFlag(String freeFlag) {
            this.freeFlag = freeFlag;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCollectionFlag() {
            return collectionFlag;
        }

        public void setCollectionFlag(String collectionFlag) {
            this.collectionFlag = collectionFlag;
        }

        public String getAdFlag() {
            return adFlag;
        }

        public void setAdFlag(String adFlag) {
            this.adFlag = adFlag;
        }

        public String getAdType() {
            return adType;
        }

        public void setAdType(String adType) {
            this.adType = adType;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
