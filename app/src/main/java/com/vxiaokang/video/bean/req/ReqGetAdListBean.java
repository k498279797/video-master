package com.vxiaokang.video.bean.req;

/**
 * 栏目推荐
 */
public class ReqGetAdListBean {
    private String code;
    private String pageIndex; // 页码 1
    private String pageSize; // 条数  10

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPageIndex() {
        if(null == pageIndex){
            return "1";
        }
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        if(null == pageSize){
            return "10";
        }
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
