package com.vxiaokang.video.activity.video.ams.bean;

import com.blankj.utilcode.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class AmsConstant {
    /**
     *
     *  https://66ams.top
     */

    //类型
    public static String[] tabName = new String[]{"全部","AV剪辑","网友上传","8X红人","SWAG","高清无码","中文字幕","国产AV","69互舔","LUNA露娜","两女一男","两男一女","中文音声","人妻熟女","写真","初夜开苞","制服诱惑","刺激车震","医生护士","厕所偷拍","口交深喉","名模空姐","多人群P","大庭广众","奇葩怪癖","女上男下","奸夫淫妇","威九国际","家庭乱伦","巨乳肥臀","年轻萝莉","强奸迷奸","情趣丝袜","情趣内衣","成人玩具","户外啪啪","打打飞机","抽插特写","推油乳交","教师学生","服务","极品女神","百合拉拉","系列","网红主播","美穴白虎","老汉推车","自慰喷水","自拍偷拍","舔逼品玉","角色扮演","调教虐待","足交恋足","长腿","颜射吞精","三级","亚洲","写真","动画","国产","图片","大作","大陆","小说","故事","日韩","有声","欧美","短篇","经验","色漫"};
    public static String[] tabId = new String[]{"/video/","/avjianji/","/wangyoushangchuan/","/8xhongren/","/swag/","/gaoqingwuma/","/zhongwenzimu/","/guochanav/","/tags/69%E4%BA%92%E8%88%94/","/tags/luna%E9%9C%B2%E5%A8%9C/","/tags/%E4%B8%A4%E5%A5%B3%E4%B8%80%E7%94%B7/","/tags/%E4%B8%A4%E7%94%B7%E4%B8%80%E5%A5%B3/","/tags/%E4%B8%AD%E6%96%87%E9%9F%B3%E5%A3%B0/","/tags/%E4%BA%BA%E5%A6%BB%E7%86%9F%E5%A5%B3/","/tags/%E5%86%99%E7%9C%9F/","/tags/%E5%88%9D%E5%A4%9C%E5%BC%80%E8%8B%9E/","/tags/%E5%88%B6%E6%9C%8D%E8%AF%B1%E6%83%91/","/tags/%E5%88%BA%E6%BF%80%E8%BD%A6%E9%9C%87/","/tags/%E5%8C%BB%E7%94%9F%E6%8A%A4%E5%A3%AB/","/tags/%E5%8E%95%E6%89%80%E5%81%B7%E6%8B%8D/","/tags/%E5%8F%A3%E4%BA%A4%E6%B7%B1%E5%96%89/","/tags/%E5%90%8D%E6%A8%A1%E7%A9%BA%E5%A7%90/","/tags/%E5%A4%9A%E4%BA%BA%E7%BE%A4p/","/tags/%E5%A4%A7%E5%BA%AD%E5%B9%BF%E4%BC%97/","/tags/%E5%A5%87%E8%91%A9%E6%80%AA%E7%99%96/","/tags/%E5%A5%B3%E4%B8%8A%E7%94%B7%E4%B8%8B/","/tags/%E5%A5%B8%E5%A4%AB%E6%B7%AB%E5%A6%87/","/tags/%E5%A8%81%E4%B9%9D%E5%9B%BD%E9%99%85/","/tags/%E5%AE%B6%E5%BA%AD%E4%B9%B1%E4%BC%A6/","/tags/%E5%B7%A8%E4%B9%B3%E8%82%A5%E8%87%80/","/tags/%E5%B9%B4%E8%BD%BB%E8%90%9D%E8%8E%89/","/tags/%E5%BC%BA%E5%A5%B8%E8%BF%B7%E5%A5%B8/","/tags/%E6%83%85%E8%B6%A3%E4%B8%9D%E8%A2%9C/","/tags/%E6%83%85%E8%B6%A3%E5%86%85%E8%A1%A3/","/tags/%E6%88%90%E4%BA%BA%E7%8E%A9%E5%85%B7/","/tags/%E6%88%B7%E5%A4%96%E5%95%AA%E5%95%AA/","/tags/%E6%89%93%E6%89%93%E9%A3%9E%E6%9C%BA/","/tags/%E6%8A%BD%E6%8F%92%E7%89%B9%E5%86%99/","/tags/%E6%8E%A8%E6%B2%B9%E4%B9%B3%E4%BA%A4/","/tags/%E6%95%99%E5%B8%88%E5%AD%A6%E7%94%9F/","/tags/%E6%9C%8D%E5%8A%A1/","/tags/%E6%9E%81%E5%93%81%E5%A5%B3%E7%A5%9E/","/tags/%E7%99%BE%E5%90%88%E6%8B%89%E6%8B%89/","/tags/%E7%B3%BB%E5%88%97/","/tags/%E7%BD%91%E7%BA%A2%E4%B8%BB%E6%92%AD/","/tags/%E7%BE%8E%E7%A9%B4%E7%99%BD%E8%99%8E/","/tags/%E8%80%81%E6%B1%89%E6%8E%A8%E8%BD%A6/","/tags/%E8%87%AA%E6%85%B0%E5%96%B7%E6%B0%B4/","/tags/%E8%87%AA%E6%8B%8D%E5%81%B7%E6%8B%8D/","/tags/%E8%88%94%E9%80%BC%E5%93%81%E7%8E%89/","/tags/%E8%A7%92%E8%89%B2%E6%89%AE%E6%BC%94/","/tags/%E8%B0%83%E6%95%99%E8%99%90%E5%BE%85/","/tags/%E8%B6%B3%E4%BA%A4%E6%81%8B%E8%B6%B3/","/tags/%E9%95%BF%E8%85%BF/","/tags/%E9%A2%9C%E5%B0%84%E5%90%9E%E7%B2%BE/","/categories/%E4%B8%89%E7%BA%A7/","/categories/%E4%BA%9A%E6%B4%B2/","/categories/%E5%86%99%E7%9C%9F/","/categories/%E5%8A%A8%E7%94%BB/","/categories/%E5%9B%BD%E4%BA%A7/","/categories/%E5%9B%BE%E7%89%87/","/categories/%E5%A4%A7%E4%BD%9C/","/categories/%E5%A4%A7%E9%99%86/","/categories/%E5%B0%8F%E8%AF%B4/","/categories/%E6%95%85%E4%BA%8B/","/categories/%E6%97%A5%E9%9F%A9/","/categories/%E6%9C%89%E5%A3%B0/","/categories/%E6%AC%A7%E7%BE%8E/","/categories/%E7%9F%AD%E7%AF%87/","/categories/%E7%BB%8F%E9%AA%8C/","/categories/%E8%89%B2%E6%BC%AB/"};
    //排序
    public static String[] orderName = new String[]{"标签","無碼","H動畫","自拍","偷拍","中文字幕","酒店","KTV","教室","辦公室","野外","洗手間","車震","家裡","少婦","學生","老師","小姐","OL","嫩模","網紅","主播","媽媽","姐姐","巨乳","美乳","巨臀","美背","三點粉","多汁","處女","美腿","巨屌","SM","捆綁","戀足","強姦","同性","迷姦","近親相姦","制服誘惑","絲襪","護士","Cosplay","空姐","情趣內衣","后入","無套內射","自慰","口爆","深喉","潮吹","群P","乳交","顏射","肛交","素人","清純可愛","白虎","女優","人獸","蘿莉","明星","情趣玩具","人妖","另類","按摩","露出"};
    public static String[] orderId = new String[]{"","/videos/tag/nopixelated","/videos/tag/cartoon","/videos/tag/zipai","/videos/tag/toupai","/videos/tag/caption","/videos/tag/hotel","/videos/tag/ktv","/videos/tag/classroom","/videos/tag/office","/videos/tag/outside","/videos/tag/wc","/videos/tag/car","/videos/tag/home","/videos/tag/woman","/videos/tag/student","/videos/tag/teacher","/videos/tag/lady","/videos/tag/ol","/videos/tag/model","/videos/tag/celebrity","/videos/tag/anchor","/videos/tag/mother","/videos/tag/sister","/videos/tag/big-breast","/videos/tag/beauty-breast","/videos/tag/big-ass","/videos/tag/beauty-back","/videos/tag/powder","/videos/tag/solppy","/videos/tag/virgin","/videos/tag/beauty-leg","/videos/tag/black-big","/videos/tag/sm","/videos/tag/bundling","/videos/tag/foot-love","/videos/tag/rape","/videos/tag/homosexual","/videos/tag/adultery","/videos/tag/fornication","/videos/tag/uniform","/videos/tag/stockings","/videos/tag/nurse","/videos/tag/cosplay","/videos/tag/hostess","/videos/tag/sexy-ingerie","/videos/tag/back-fuck","/videos/tag/injection","/videos/tag/masturbation","/videos/tag/oral-copulation","/videos/tag/deep-throat","/videos/tag/spray-tide","/videos/tag/group","/videos/tag/breast-fuck","/videos/tag/yan-shot","/videos/tag/anal-copulation","/videos/tag/vegetarian","/videos/tag/pure","/videos/tag/hairless","/videos/tag/avlady","/videos/tag/bestiality","/videos/tag/loli","/videos/tag/star","/videos/tag/toys","/videos/tag/shemale","/videos/tag/other","/videos/tag/massage","/videos/tag/exhibitionism"};
    //年份 year
    public static String[] yearName = new String[]{"线路","线路1","线路3","线路4"};
    public static String[] yearId = new String[]{"https://66mee6.top","https://66ams.top","https://66aob.top","https://66dany.buzz"};
    //语言 lang
    public static String[] languageName = new String[]{"语言","国语","粤语","英语","日语","韩语","泰语","法语"};
    public static String[] languageId = new String[]{"","国语","粤语","英语","日语","韩语","泰语","法语"};
    //地区 area
    public static String[] regionName = new String[]{"地区","大陆","香港","台湾","日本","韩国","欧美","泰国","德国","印度","意大利","西班牙","加拿大","其他"};
    public static String[] regionId = new String[]{"","大陆","香港","台湾","日本","韩国","欧美","泰国","德国","印度","意大利","西班牙","加拿大","其他"};
    //类型 class
    public static String[] typeName = new String[]{"类型","解密","乡村","都市","少儿","对话","搞笑","恐怖","宫廷","剧情","言情","家庭","励志","偶像","时装","年代","悬疑","古装","热血","同人","耽美"};
    public static String[] typeId = new String[]{"","解密","乡村","都市","少儿","对话","搞笑","恐怖","宫廷","剧情","言情","家庭","励志","偶像","时装","年代","悬疑","古装","热血","同人","耽美"};

    public static List<String> playUrl = new ArrayList();
    static {
        playUrl.add("https://cdn1m.2z0sois.com/v/");
        playUrl.add("https://cdn2m.2z0sois.com/v/");
        playUrl.add("https://cdn3m.2z0sois.com/v/");
    }

    // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){
        //https://rvbqfdbyor.abbeeac58f93f295745239.buzz:8443/redirect/
        return SPUtils.getInstance().getString("ams_domain_url", "https://66dany.buzz");
    }

    public static void setReqDomain(String value){
        SPUtils.getInstance().put("ams_domain_url", value);
    }
}
