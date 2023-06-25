package com.vxiaokang.video.bean.resp;


import com.vxiaokang.video.bean.AdBean;

/**
 * 返回接口
 */
public class RespAdBean {
    private String code; //0 表示成功  其他表示失败
    private String msg; //信息
    private String total; //总条数
    private String rows; //数据集
    private AdBean data;  //数据对象

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

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public Object getData() {
        return data;
    }

    public void setData(AdBean data) {
        this.data = data;
    }



}
