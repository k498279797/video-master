package com.vxiaokang.video.activity.video.shipin.bean;

import com.blankj.utilcode.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class ShipinConstant {
    /**
     *
     *  https://www.88ysw.com
     *
     */
    public static List<ShipinCategory> cateList = new ArrayList<>();
    //类型
    public static String[] tabName = new String[]{"全部","电影","电视剧","综艺","动漫","动作片","喜剧片","爱情片","科幻片","恐怖片","剧情片","战争片","国产剧","港剧","台剧","日剧","韩剧","美剧","泰剧"};
    public static int[] tabId = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,13,14,15,16,28,29,30};
    //排序
    public static String[] orderName = new String[]{"排序","最新","最热","好评"};
    public static String[] orderId = new String[]{"","time","hit","commend"};
    //年份
    public static String[] yearName = new String[]{"年份","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","更早"};
    public static String[] yearId = new String[]{"","hit","2023","2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","more"};


    static {
        /**
         *   <li class="active"><a href="/search.php?searchtype=5&amp;tid=1">电影</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=2">电视剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=3">综艺</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=4">动漫</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=5">动作片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=6">喜剧片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=7">爱情片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=8">科幻片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=9">恐怖片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=10">剧情片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=11">战争片</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=13">国产剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=14">港剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=15">台剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=16">日剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=28">韩剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=29">美剧</a></li>
         *         <li><a href="/search.php?searchtype=5&amp;tid=30">泰剧</a></li>
         */
        cateList.add(new ShipinCategory("","全部"));
        cateList.add(new ShipinCategory("1","电影"));
        cateList.add(new ShipinCategory("2","电视剧"));
        cateList.add(new ShipinCategory("3","综艺"));
        cateList.add(new ShipinCategory("4","动漫"));
        cateList.add(new ShipinCategory("5","动作片"));
        cateList.add(new ShipinCategory("6","喜剧片"));
        cateList.add(new ShipinCategory("7","爱情片"));
        cateList.add(new ShipinCategory("8","科幻片"));
        cateList.add(new ShipinCategory("9","恐怖片"));
        cateList.add(new ShipinCategory("10","剧情片"));
        cateList.add(new ShipinCategory("11","战争片"));
        cateList.add(new ShipinCategory("13","国产剧"));
        cateList.add(new ShipinCategory("14","港剧"));
        cateList.add(new ShipinCategory("15","台剧"));
        cateList.add(new ShipinCategory("16","日剧"));
        cateList.add(new ShipinCategory("28","韩剧"));
        cateList.add(new ShipinCategory("29","美剧"));
        cateList.add(new ShipinCategory("30","泰剧"));

    }

   // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){
        return SPUtils.getInstance().getString("shipin_domain_url", "https://www.88dyw.com");
    }

    public static void setReqDomain(String value){
         SPUtils.getInstance().put("shipin_domain_url", value);
    }

}
