package com.vxiaokang.video.activity.video.meijugua.bean;

import com.blankj.utilcode.util.SPUtils;

public class MeijuguaConstant {
    /**
     *
     *  http://www.meijugua.com
     *
     */

    //类型
    public static String[] tabName = new String[]{"全部"};
    public static String[] tabId = new String[]{"type1"};
    //排序
    public static String[] orderName = new String[]{"排序","最新","最热","好评"};
    public static String[] orderId = new String[]{"addtime","addtime","hits","gold"};
    //年份 year
    public static String[] yearName = new String[]{"年份","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2010-2000","90年代","80年代","70年代","更早"};
    public static String[] yearId = new String[]{"",      "2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","20002010","19901999","19801989","19701979","18001969"};
    //语言 lang
    public static String[] languageName = new String[]{"语言","国语","粤语","英语","日语","韩语","泰语","法语"};
    public static String[] languageId = new String[]{"","国语","粤语","英语","日语","韩语","泰语","法语"};
    //地区 area
    public static String[] regionName = new String[]{"地区","大陆","香港","台湾","日本","韩国","欧美","泰国","德国","印度","意大利","西班牙","加拿大","其他"};
    public static String[] regionId = new String[]{"","大陆","香港","台湾","日本","韩国","欧美","泰国","德国","印度","意大利","西班牙","加拿大","其他"};
    //类型 class
    public static String[] typeName = new String[]{"类型","解密","乡村","都市","少儿","对话","搞笑","恐怖","宫廷","剧情","言情","家庭","励志","偶像","时装","年代","悬疑","古装","热血","同人","耽美"};
    public static String[] typeId = new String[]{"","解密","乡村","都市","少儿","对话","搞笑","恐怖","宫廷","剧情","言情","家庭","励志","偶像","时装","年代","悬疑","古装","热血","同人","耽美"};

    // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){
        return SPUtils.getInstance().getString("meijugua_domain_url", "http://www.meijugua.com");
    }

    public static void setReqDomain(String value){
        SPUtils.getInstance().put("meijugua_domain_url", value);
    }
}
