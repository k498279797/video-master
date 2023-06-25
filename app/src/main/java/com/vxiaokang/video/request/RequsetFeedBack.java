package com.vxiaokang.video.request;


import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.vxiaokang.video.bean.req.ReqFeedBack;
import com.vxiaokang.video.constants.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.MediaType;

/**
 * 意見反饋
 */
public class RequsetFeedBack {
    public static void addData(ReqFeedBack params, StringCallback callback) {
        OkHttpUtils.postString().url(Constants.feedbackAdd)
                .content(new Gson().toJson(params))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("token", SPUtils.getInstance().getString("login_token", "")).build().execute(callback);
    }
}
