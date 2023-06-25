package com.vxiaokang.video.activity.video.pkmp.bean;

import com.blankj.utilcode.util.SPUtils;
import com.vxiaokang.video.activity.video.shipin.bean.ShipinCategory;

import java.util.ArrayList;
import java.util.List;

public class PkmpConstant {
    /**
     *
     *  https://www.pkmp4.com
     *
     */

    //类型
    public static String[] tabName = new String[]{"全部","电影","剧集","综艺","动漫"};
    public static String[] tabId_bak = new String[]{"/vt/1-%s.html","/vt/1-%s.html","/vt/2-%s.html","/vt/3-%s.html","/vt/4-%s.html"};
    public static String[] tabId = new String[]{"/ms/1-%s-%s-%s-%s----%s---%s.html","/ms/1-%s-%s-%s-%s----%s---%s.html","/ms/2-%s-%s-%s-%s----%s---%s.html","/ms/2-%s-%s-%s-%s----%s---%s.html","/ms/4-%s-%s-%s-%s----%s---%s.html"};

    //地区
    public static String[] regionName = new String[]{"地区","大陆","香港","台湾","日本","韩国","泰国","美国","英国","法国","德国","印度","丹麦","瑞典","荷兰","加拿大","俄罗斯","意大利","比利时","西班牙","澳大利亚","其他"};
    public static String[] regionId = new String[]{"","大陆","香港","台湾","日本","韩国","泰国","美国","英国","法国","德国","印度","丹麦","瑞典","荷兰","加拿大","俄罗斯","意大利","比利时","西班牙","澳大利亚","其他"};

    //排序
    public static String[] orderName = new String[]{"排序","按时间","按人气","按评分"};
    public static String[] orderId = new String[]{"time","time","hits","score"};

    //类型
    public static String[] typeName = new String[]{"类型","剧情","科幻","动作"
            ,"喜剧","爱情","冒险","儿童","歌舞","音乐","奇幻","动画","恐怖","惊悚","丧尸","战争","传记","纪录","犯罪","悬疑","西部","灾难","古装","武侠","家庭","短片","校园","文艺","运动","青春","同性","励志","人性","美食","女性","治愈","历史"};
    public static String[] typeId = new String[]{"","剧情","科幻","动作"
            ,"喜剧","爱情","冒险","儿童","歌舞","音乐","奇幻","动画","恐怖","惊悚","丧尸","战争","传记","纪录","犯罪","悬疑","西部","灾难","古装","武侠","家庭","短片","校园","文艺","运动","青春","同性","励志","人性","美食","女性","治愈","历史"};
    //语言
    public static String[] languageName = new String[]{"语言","国语","粤语","英语","法语","日语","韩语","泰语","德语","俄语","闽南语","丹麦语","波兰语","瑞典语","印地语","挪威语","意大利语","西班牙语","无对白","其他"};
    public static String[] languageId = new String[]{"","国语","粤语","英语","法语","日语","韩语","泰语","德语","俄语","闽南语","丹麦语","波兰语","瑞典语","印地语","挪威语","意大利语","西班牙语","无对白","其他"};

    //年份
    public static String[] yearName = new String[]{"年份","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006"};
    public static String[] yearId = new String[]{   "",   "2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006"};




   // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){
        return SPUtils.getInstance().getString("pkmp_domain_url", "https://www.pkmp4.com");
    }

    public static void setReqDomain(String value){
         SPUtils.getInstance().put("pkmp_domain_url", value);
    }

}
