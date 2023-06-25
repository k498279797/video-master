package com.vxiaokang.video.bean.req;

/**
 * 视频请求参数
 */
public class ReqVideoBean {
    private String appId; // 应用id
    private String categoryId; // 类别
    private String orderBy; // 排序，0 创建时间 ,1 按排序值,2点赞数量,3分享数量,4收藏时间
    private String pageIndex; // 页码 1
    private String pageSize; // 条数  10
    private String searchVal; // 查询条件，主题，关键字，标签，描述
    private String videoType; //默认0 资源类型:0 普通 1 推广 2 图片资源
    private String userId;

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCategoryId() {
        if(null == categoryId){
            return "";
        }
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getOrderBy() {
        if(null == orderBy){
            return "";
        }
        return orderBy;
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    public String getPageIndex() {
        if(null == pageIndex){
            return "1";
        }
        return pageIndex;
    }
    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }
    public String getPageSize() {
        if(null == pageSize){
            return "30";
        }
        return pageSize;
    }
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
    public String getSearchVal() {
        if(null == searchVal){
            return "";
        }
        return searchVal;
    }
    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }
    public String getVideoType() {
        if(null == videoType){
            return "";
        }
        return videoType;
    }
    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }
    public String getUserId() {
        if(null == videoType){
            return "";
        }
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
