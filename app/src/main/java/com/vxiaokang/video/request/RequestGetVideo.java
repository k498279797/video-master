package com.vxiaokang.video.request;
import com.vxiaokang.video.bean.req.ReqVideoBean;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.constants.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * 视频数据请求
 */
public class RequestGetVideo {
    public static void getData(ReqVideoBean bean, StringCallback callback){
        OkHttpUtils.get().url(Constants.videoData)
                .addParams("app_id", ConfigKey.MY_APP_ID)
                .addParams("category_id", bean.getCategoryId())
                .addParams("order_by",bean.getOrderBy())
                .addParams("page_index",bean.getPageIndex())
                .addParams("page_size",bean.getPageSize())
                .addParams("search_val",bean.getSearchVal())
                .addParams("user_id",bean.getUserId())
                .addHeader("token", "").build().execute(callback);
    }
}
