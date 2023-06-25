package com.vxiaokang.video.activity.video.kdy.bean;

import com.blankj.utilcode.util.SPUtils;

public class KdyConstant {
    /**
     *
     *  https://www.1080kdy.com
     *
     */

    //类型
    public static String[] tabName = new String[]{"全部","电影","电视剧","综艺","动漫","资讯","动作","国产剧","港台剧","日韩剧","欧美剧","海外剧","泰国剧","国产动漫","日韩动漫","欧美动漫","港台动漫","海外动漫","喜剧片","爱情片","科幻片","恐怖片","剧情片","战争片","纪录片","悬疑片","犯罪片","奇幻片","动画片","冒险片","惊悚片"};
    public static String[] tabId = new String[]{"1","1","2","3","4","5","6","13","14","15","16","32","33","24","25","26","35","36","7","8","9","10","11","12","27","28","29","30","31","34","37"};
    //排序
    public static String[] orderName = new String[]{"排序","最新","最热","好评"};
    public static String[] orderId = new String[]{"","time","hits","score"};
    //年份 year
    public static String[] yearName = new String[]{"年份","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010"};
    public static String[] yearId = new String[]{"",      "2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010"};
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
        return SPUtils.getInstance().getString("kdy_domain_url", "https://www.1080kdy.com");
    }

    public static void setReqDomain(String value){
        SPUtils.getInstance().put("kdy_domain_url", value);
    }
}
