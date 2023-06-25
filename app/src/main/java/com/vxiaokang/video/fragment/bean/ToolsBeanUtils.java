package com.vxiaokang.video.fragment.bean;

import com.vxiaokang.video.activity.OpenVideoActivity;
import com.vxiaokang.video.activity.OpenWebSiteActivity;
import com.vxiaokang.video.activity.WebPlayActivity;
import com.vxiaokang.video.activity.WebSiteViewActivity;
import com.vxiaokang.video.activity.WebViewVideoPlayActivity;
import com.vxiaokang.video.activity.firework.FireworkShowActivity;
import com.vxiaokang.video.activity.tool.AppInstallActivity;
import com.vxiaokang.video.activity.tool.DragScaleActivity;
import com.vxiaokang.video.activity.tool.LuckCircleActivity;
import com.vxiaokang.video.activity.tool.SleepActivity;
import com.vxiaokang.video.activity.tool.TextRotationActivity;
import com.vxiaokang.video.activity.tool.WaterRippleActivity;
import com.vxiaokang.video.activity.tool.luck.LuckMainActivity;
import com.vxiaokang.video.activity.tool.luck.LuckyPanMainActivity;
import com.vxiaokang.video.activity.video.ams.AmsVideoListActivity;
import com.vxiaokang.video.activity.video.clm.ClmSearchListActivity;
import com.vxiaokang.video.activity.video.gaydude.DudeVideoListActivity;
import com.vxiaokang.video.activity.video.gaytube.GaytubeVideoListActivity;
import com.vxiaokang.video.activity.video.hdtv.HdtvVideoListActivity;
import com.vxiaokang.video.activity.video.huang.HuangVideoMainActivity;
import com.vxiaokang.video.activity.video.kancc.KanccVideoListActivity;
import com.vxiaokang.video.activity.video.kantv.KantvVideoListActivity;
import com.vxiaokang.video.activity.video.kdy.KdyVideoListActivity;
import com.vxiaokang.video.activity.video.local.LocalCateActivity;
import com.vxiaokang.video.activity.video.meijugua.MeijuguaVideoListActivity;
import com.vxiaokang.video.activity.video.pkmp.PkmpVideoListActivity;
import com.vxiaokang.video.activity.video.shipin.ShipinVideoListActivity;
import com.vxiaokang.video.activity.video.tube.TubeVideoListActivity;
import com.vxiaokang.video.activity.video.yinshi.YinshiVideoListActivity;
import com.vxiaokang.video.bean.ToolsBean;
import com.vxiaokang.video.config.InitAdConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具
 */
public class ToolsBeanUtils {
    public static List<ToolsBean> converData(){

        List<ToolsBean> dataList = new ArrayList<>();

        ToolsBean info = new ToolsBean();
       /* info.setTitle("实用网站");
        info.setType("0");
        info.setUrl("https://www.aqd131.cc:8443/?f=www.aqd2021.cc");
        dataList.add(info);*/
        info = new ToolsBean();
        info.setTitle("88影视网");
        info.setType("0");
        info.setUrl("HdtvVideoListActivity");
        info.setClazz(HdtvVideoListActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("88dyw");
        info.setType("0");
        info.setUrl("https://www.88dyw.com");
       // info.setClazz(WebSiteViewActivity.class);
        info.setClazz(ShipinVideoListActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("片库");
        info.setType("0");
        info.setUrl("https://www.pkmp4.com");
        info.setClazz(PkmpVideoListActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("88影视");
        info.setType("0");
        info.setUrl("http://www.80yy.la");
      //  info.setClazz(WebSiteViewActivity.class);
        info.setClazz(YinshiVideoListActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("6090影视");
        info.setType("0");
        info.setUrl("http://www.6080kan.tv");
        info.setClazz(KantvVideoListActivity.class);
        dataList.add(info);


        info = new ToolsBean();
        info.setTitle("樱花动漫");
        info.setType("0");
        info.setUrl("https://www.94kan.cc");
        info.setClazz(KanccVideoListActivity.class);
        dataList.add(info);


        info = new ToolsBean();
        info.setTitle("1080电影");
        info.setType("0");
        info.setUrl("https://www.1080kdy.com");
        info.setClazz(KdyVideoListActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("美剧瓜");
        info.setType("0");
        info.setUrl("http://www.meijugua.com");
        info.setClazz(MeijuguaVideoListActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("爱情岛");
        info.setType("0");
        info.setUrl("https://vip.aqdz38.com");
        info.setClazz(TubeVideoListActivity.class);
        //if(InitAdConfig.isDebug()) {
            dataList.add(info);
        //}


        info = new ToolsBean();
        info.setTitle("gaydude");
        info.setType("0");
        info.setUrl("https://gaydude.me");
        info.setClazz(DudeVideoListActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("gaytube");
        info.setType("0");
        info.setUrl("https://gaytubewatch.com");
        info.setClazz(GaytubeVideoListActivity.class);
        // if(InitAdConfig.isDebug()) {
      //  dataList.add(info);
        // }

        info = new ToolsBean();
        info.setTitle("66摸");
        info.setType("0");
        //https://docs.toponad.com/#/zh-cn/android/DeveloperAccess/WaterFall/waterfall_best_practices
        // https://66dany.buzz
        info.setUrl("https://66ams.top");
        info.setClazz(AmsVideoListActivity.class);
        // if(InitAdConfig.isDebug()) {
        dataList.add(info);
        // }

        info = new ToolsBean();
        info.setTitle("vip电影");
        info.setType("0");
        info.setUrl("http://www.vip1280.net");
        info.setClazz(WebSiteViewActivity.class);
        dataList.add(info);


        info = new ToolsBean();
        info.setTitle("打开网站");
        info.setType("1");
        info.setUrl("");
        info.setClazz(OpenWebSiteActivity.class);
        dataList.add(info);


        info = new ToolsBean();
        info.setTitle("播放视频");
        info.setType("22");
        info.setUrl("http://aliyuncoccdnct-hd2.inter.iqiyi.com/videos/v0/20210902/8c/e3/000182719f0c411a8388cd8d2a03f043.f4v?key=0b0e66333fc1b9825c6859bb687eab7e5&dis_k=ef8fb5d45677022c54d7ce1968c8f59a&dis_t=1669791880&dis_dz=CT-ZheJiang_HangZhou&dis_st=103&src=iqiyi.com&dis_hit=0&dis_tag=01010000&uuid=2f633ae0-63870088-3b0&qd_aid=202861101&qd_k=341c8d9e46908fa8eaeaf0908cbfc2e6&qd_ip=2f633ae0&qd_stert=0&qd_uid=0&qd_p=2f633ae0&qd_sc=e9f4821c84270858f7e9cae308572b98&qd_src=01012001010000000000&qd_tvid=385274600&qd_index=1&qd_vip=0&qd_vipdyn=0&qd_tm=1669791879536&qd_vipres=0&cphc=arta");
        info.setClazz(OpenVideoActivity.class);
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("声音12-单次");
        info.setType("10");
        info.setUrl("");
        if(InitAdConfig.isDebug()){
            dataList.add(info);
        }


        info = new ToolsBean();
        info.setTitle("声音12-0.5");
        info.setType("12");
        info.setUrl("");
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("声音12-1");
        info.setType("13");
        info.setUrl("");
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("声音12-1.5");
        info.setType("14");
        info.setUrl("");
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("声音12-2");
        info.setType("15");
        info.setUrl("");
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("音乐（循环）");
        info.setType("16");
        info.setUrl("");
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("音乐（单次）");
        info.setType("17");
        info.setUrl("");
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        // https://www.hlsplayer.net/#type=m3u8&src=https%3A%2F%2Fnew.qqaku.com%2F20221209%2FNLMPy0yi%2Findex.m3u8
        info = new ToolsBean();
        info.setTitle("小黄书");
        info.setType("20");
        info.setUrl("");
        info.setClazz(HuangVideoMainActivity.class);
        if(InitAdConfig.isHuaWeiFlag()){
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("分类搜索");
        info.setType("0");
        info.setUrl("");
        info.setClazz(LocalCateActivity.class);
        if(InitAdConfig.isHuaWeiFlag()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("磁力猫");
        info.setType("1");
        info.setUrl("");
        info.setClazz(ClmSearchListActivity.class);
        if(InitAdConfig.isHuaWeiFlag()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("倒置文字");
        info.setType("0");
        info.setUrl("");
        info.setClazz(TextRotationActivity.class);
      //  if(InitAdConfig.isDebug()) {
            dataList.add(info);
       // }

        info = new ToolsBean();
        info.setTitle("拉伸矩形");
        info.setType("0");
        info.setUrl("");
        info.setClazz(DragScaleActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("H5Video");
        info.setType("0");
      //  info.setUrl("https://baidu.sd-play.com/20221122/1nZnwvaY/index.m3u8");
        info.setUrl("https://www.hlsplayer.net/#type=m3u8&src=https://new.qqaku.com/20221209/NLMPy0yi/index.m3u8");
        info.setClazz(WebViewVideoPlayActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }
        info = new ToolsBean();
        info.setTitle("测试");
        info.setType("0");
        info.setUrl("file:///android_asset/video_play.html");
        info.setClazz(WebSiteViewActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }


        info = new ToolsBean();
        info.setTitle("视频测试");
        info.setType("0");
        info.setUrl("");
        info.setClazz(WebPlayActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("烟花效果");
        info.setType("0");
        info.setUrl("");
        info.setClazz(FireworkShowActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("圆形放大");
        info.setType("0");
        info.setUrl("");
        info.setClazz(SleepActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("今天吃什么");
        info.setType("0");
        info.setUrl("");
        info.setClazz(LuckCircleActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("吃什么");
        info.setType("0");
        info.setUrl("");
        info.setClazz(LuckMainActivity.class);
         if(InitAdConfig.isDebug()) {
        dataList.add(info);
         }
        info = new ToolsBean();
        info.setTitle("抽奖");
        info.setType("0");
        info.setUrl("");
        info.setClazz(LuckyPanMainActivity.class);
         if(InitAdConfig.isDebug()) {
        dataList.add(info);
         }

        info = new ToolsBean();
        info.setTitle("应用安装");
        info.setType("0");
        info.setUrl("");
        info.setClazz(AppInstallActivity.class);
        if(InitAdConfig.isDebug()) {
            dataList.add(info);
        }

        info = new ToolsBean();
        info.setTitle("自定义扩散");
        info.setType("0");
        info.setUrl("");
        info.setClazz(WaterRippleActivity.class);
        //if(InitAdConfig.isDebug()) {
            dataList.add(info);
        //}

        return dataList;
    }

}
