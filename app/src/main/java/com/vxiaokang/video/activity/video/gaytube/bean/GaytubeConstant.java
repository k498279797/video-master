package com.vxiaokang.video.activity.video.gaytube.bean;

import com.blankj.utilcode.util.SPUtils;

public class GaytubeConstant {
    /**
     *
     *  https://gaytubewatch.com
     */

    //类型
    public static String[] tabName = new String[]{"全部","Gaymen","Itsgonnahurt","Religion","Gay Big Cock Party","Bulldogpit","Gay Fetish Locator","Hunk And Hairy","Gayamateur","Thighs","Emobf","Stud In Ass","Picture Gay Sex Man","Balls Deep","Codycummings","Femdom Porn","Hung Guy Guys","Boyfriends"};
    public static String[] tabId = new String[]{"/","/porn_clips/Gaymen/","/porn_clips/Itsgonnahurt/","/porn_clips/Religion/","/porn_clips/Gay%20Big%20Cock%20Party/","/porn_clips/Bulldogpit/","/porn_clips/Gay%20Fetish%20Locator/","/porn_clips/Hunk%20And%20Hairy/","/porn_clips/Gayamateur/","/porn_clips/Thighs/","/porn_clips/Emobf/","/porn_clips/Stud%20In%20Ass/","/porn_clips/Picture%20Gay%20Sex%20Man/","/porn_clips/Balls%20Deep/","/porn_clips/Codycummings/","/porn_clips/Femdom%20Porn/","/porn_clips/Hung%20Guy%20Guys/","/porn_clips/Boyfriends/"};
    //排序
    public static String[] orderName = new String[]{"标签","無碼","H動畫","自拍","偷拍","中文字幕","酒店","KTV","教室","辦公室","野外","洗手間","車震","家裡","少婦","學生","老師","小姐","OL","嫩模","網紅","主播","媽媽","姐姐","巨乳","美乳","巨臀","美背","三點粉","多汁","處女","美腿","巨屌","SM","捆綁","戀足","強姦","同性","迷姦","近親相姦","制服誘惑","絲襪","護士","Cosplay","空姐","情趣內衣","后入","無套內射","自慰","口爆","深喉","潮吹","群P","乳交","顏射","肛交","素人","清純可愛","白虎","女優","人獸","蘿莉","明星","情趣玩具","人妖","另類","按摩","露出"};
    public static String[] orderId = new String[]{"","/videos/tag/nopixelated","/videos/tag/cartoon","/videos/tag/zipai","/videos/tag/toupai","/videos/tag/caption","/videos/tag/hotel","/videos/tag/ktv","/videos/tag/classroom","/videos/tag/office","/videos/tag/outside","/videos/tag/wc","/videos/tag/car","/videos/tag/home","/videos/tag/woman","/videos/tag/student","/videos/tag/teacher","/videos/tag/lady","/videos/tag/ol","/videos/tag/model","/videos/tag/celebrity","/videos/tag/anchor","/videos/tag/mother","/videos/tag/sister","/videos/tag/big-breast","/videos/tag/beauty-breast","/videos/tag/big-ass","/videos/tag/beauty-back","/videos/tag/powder","/videos/tag/solppy","/videos/tag/virgin","/videos/tag/beauty-leg","/videos/tag/black-big","/videos/tag/sm","/videos/tag/bundling","/videos/tag/foot-love","/videos/tag/rape","/videos/tag/homosexual","/videos/tag/adultery","/videos/tag/fornication","/videos/tag/uniform","/videos/tag/stockings","/videos/tag/nurse","/videos/tag/cosplay","/videos/tag/hostess","/videos/tag/sexy-ingerie","/videos/tag/back-fuck","/videos/tag/injection","/videos/tag/masturbation","/videos/tag/oral-copulation","/videos/tag/deep-throat","/videos/tag/spray-tide","/videos/tag/group","/videos/tag/breast-fuck","/videos/tag/yan-shot","/videos/tag/anal-copulation","/videos/tag/vegetarian","/videos/tag/pure","/videos/tag/hairless","/videos/tag/avlady","/videos/tag/bestiality","/videos/tag/loli","/videos/tag/star","/videos/tag/toys","/videos/tag/shemale","/videos/tag/other","/videos/tag/massage","/videos/tag/exhibitionism"};
    //年份 year
    public static String[] yearName = new String[]{"线路","线路1"};
    public static String[] yearId = new String[]{"https://gaytubewatch.com","https://gaytubewatch.com"};
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
        return SPUtils.getInstance().getString("gaytube_domain_url", "https://gaytubewatch.com");
    }

    public static void setReqDomain(String value){
        SPUtils.getInstance().put("gaytube_domain_url", value);
    }
}
