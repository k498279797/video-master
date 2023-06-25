package com.vxiaokang.video.bean.resp;

import com.vxiaokang.video.bean.VideoInfoBean;

import java.util.List;

/**
 * s视频数据
 */
public class RespVideoBean {
    private String code;
    private String msg;
    private String total;
    private List<VideoInfoBean> rows;

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

    public List<VideoInfoBean> getRows() {
        return rows;
    }

    public void setRows(List<VideoInfoBean> rows) {
        this.rows = rows;
    }


}
