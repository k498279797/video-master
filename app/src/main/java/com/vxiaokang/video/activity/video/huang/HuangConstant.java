package com.vxiaokang.video.activity.video.huang;

import com.blankj.utilcode.util.SPUtils;

public class HuangConstant {
    /**
     *  acc.dasaidao.com
     *  app.rk5ck5dzx.com
     *
     *  https://appz.dtkjxwjvf6.com
     *  https://app.ivlutt.com
     *  https://app.gggkkg.com
     *  https://app.ei751.com
     *
     */
   // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){ 
        return SPUtils.getInstance().getString("domain_url", "appz.dtkjxwjvf6.com");
    }

    public static void setReqDomain(String value){
         SPUtils.getInstance().put("domain_url", value);
    }



}
