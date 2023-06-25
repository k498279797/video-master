package com.vxiaokang.video.request;

import com.blankj.utilcode.util.SPUtils;
import com.vxiaokang.video.bean.req.ReqAddDevice;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.constants.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 设备信息上报
 */
public class RequsetDevice {
    public static void addData(ReqAddDevice params, StringCallback callback) {
        OkHttpUtils.post().url(Constants.deviceAdd)
                .addParams("app_id", ConfigKey.MY_APP_ID)
                .addParams("channel_id", params.getChannelId())
                .addParams("device_name", params.getDeviceName())
                .addParams("device_no", params.getDeviceNo())
                .addHeader("token", SPUtils.getInstance().getString("login_token", "")).build().execute(callback);
    }
}
