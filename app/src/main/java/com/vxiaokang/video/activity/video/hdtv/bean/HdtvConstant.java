package com.vxiaokang.video.activity.video.hdtv.bean;

import com.blankj.utilcode.util.SPUtils;

public class HdtvConstant {
    /**
     *
     *  http://88hd.com
     *
     */

    //类型
    public static String[] tabName = new String[]{"全部","电影","电视剧","综艺","动漫","动作片"
                                                ,"喜剧片","爱情片","科幻片","恐怖片","剧情片","战争片"
                                                ,"纪录片","冒险片","悬疑片","犯罪片","惊悚片","动画片","微电影","其他片"
                                                ,"国产剧","港台剧","日韩剧","欧美剧","其他剧"
                                                ,"国产动漫","日本动漫","欧美动漫","其他动漫"};
    public static String[] tabId = new String[]{"","1","2","3","4","5","6","7","8","9","10"
                                                    ,"11","17","18","19","20","26","27","16"
                                                   ,"25","12","13","14","15"
                                                   ,"29","30","31","32","33"};
    //排序
    public static String[] orderName = new String[]{"排序","最新","最热","推荐"};
    public static String[] orderId = new String[]{"time","time","hits","score"};
    //年份
    public static String[] yearName = new String[]{"年份","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995"};
    public static String[] yearId = new String[]{"on","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995"};
    //语言
    public static String[] languageName = new String[]{"语言","国语","粤语","英语","日语","韩语","泰语","法语"};
    public static String[] languageId = new String[]{"","国语","粤语","英语","日语","韩语","泰语","法语"};
    //地区
    public static String[] regionName = new String[]{"地区","大陆","香港","台湾","日本","韩国","欧美","泰国","其他"};
    public static String[] regionId = new String[]{"","大陆","香港","台湾","日本","韩国","欧美","泰国","其他"};
    //类型
    public static String[] typeName = new String[]{"类型","解密","乡村","都市","少儿","对话","搞笑","恐怖","宫廷","剧情","言情","家庭","励志","偶像","时装","年代","悬疑","古装","热血","同人","耽美"};
    public static String[] typeId = new String[]{"","解密","乡村","都市","少儿","对话","搞笑","恐怖","宫廷","剧情","言情","家庭","励志","偶像","时装","年代","悬疑","古装","热血","同人","耽美"};

    // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){
        // https://www.88hd.com https://www.88hd.org  http://88ys.cn
        //return SPUtils.getInstance().getString("hdtv_domain_url", "http://m.88hd.com");
        return SPUtils.getInstance().getString("hdtv_domain_url2", "http://88ys.cn");
    }

    public static void setReqDomain(String value){
        SPUtils.getInstance().put("hdtv_domain_url2", value);
    }
}
