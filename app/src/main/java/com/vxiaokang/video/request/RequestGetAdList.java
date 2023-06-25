package com.vxiaokang.video.request;

import com.vxiaokang.video.bean.req.ReqGetAdListBean;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.constants.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 栏目数据推广
 */
public class RequestGetAdList {
    public static void getData(ReqGetAdListBean bean, StringCallback callback){
        OkHttpUtils.get().url(Constants.AD_CODE_LIST)
                .addParams("app_id", ConfigKey.MY_APP_ID)
                .addParams("code", bean.getCode())
                .addParams("page_index",bean.getPageIndex())
                .addParams("page_size",bean.getPageSize())
                .addHeader("token", "").build().execute(callback);
    }

}
