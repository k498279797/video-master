package com.vxiaokang.video.request;


import com.blankj.utilcode.util.SPUtils;
import com.vxiaokang.video.BuildConfig;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.constants.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 配置信息请求接口
 */
public class RequestConfig {
    public static void getData(String keyword, StringCallback callback) {
        OkHttpUtils.get().url(Constants.config)
                .addParams("app_id", ConfigKey.MY_APP_ID)
                .addParams("config_key", keyword != null ? keyword : "")
                .addParams("app_code", BuildConfig.VERSION_NAME)
                .addParams("channel_number", SPUtils.getInstance().getString(ConfigKey.APP_CHANNEL_NO,""))
                .addHeader("token", SPUtils.getInstance().getString("login_token", "")).build().execute(callback);
    }
}
