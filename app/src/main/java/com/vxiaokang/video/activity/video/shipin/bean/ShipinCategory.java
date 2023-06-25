package com.vxiaokang.video.activity.video.shipin.bean;

public class ShipinCategory {
    private String categoryId;
    private String categoryName;
    private String link;
    public ShipinCategory(){}
    public ShipinCategory(String categoryId,String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
