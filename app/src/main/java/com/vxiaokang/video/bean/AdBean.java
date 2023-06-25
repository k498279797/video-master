package com.vxiaokang.video.bean;


import java.util.List;

/**
 * 推广位置
 */
public class AdBean {
    private String adId;
    private String adName;
    private String items;
    private String itemsId;
    private String itemsName;
    private List<AdMaterialBean> dataList;

    public String getAdId() {
        return adId;
    }
    public void setAdId(String adId) {
        this.adId = adId;
    }
    public String getAdName() {
        return adName;
    }
    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getItems() {
        return items;
    }
    public void setItems(String items) {
        this.items = items;
    }
    public String getItemsId() {
        return itemsId;
    }
    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }
    public String getItemsName() {
        return itemsName;
    }
    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }
    public List<AdMaterialBean> getDataList() {
        return dataList;
    }
    public void setDataList(List<AdMaterialBean> dataList) {
        this.dataList = dataList;
    }
}
