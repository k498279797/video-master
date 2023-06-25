package com.vxiaokang.video.bean.req;

import android.text.TextUtils;

/**
 * 设备上报统计
 */
public class ReqAddDevice {
    private String appId;
    private String channelId;
    private String deviceName;
    private String deviceNo;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannelId() {
        if(TextUtils.isEmpty(channelId)){
            channelId = "chess0001";
        }
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDeviceName() {
        if(TextUtils.isEmpty(deviceName)){
            deviceName = "未知";
        }
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNo() {
        if(TextUtils.isEmpty(deviceName)){
            deviceName = "00000000";
        }
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
