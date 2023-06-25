package com.vxiaokang.video.bean.video;

import java.util.ArrayList;
import java.util.List;

public class ChannelBeanUtils {
    public static List<ChannelBean> dataList = new ArrayList<>();
    static {
        ChannelBean bean  = null;
        bean  = new ChannelBean();
        bean.setName("湖南卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/hunanhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("江苏卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/jshd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("东方卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/dfhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("安徽卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/ahhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("黑龙江卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/hljhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("辽宁卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/lnhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("浙江卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/zjhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("深圳卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/szhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("广东卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/gdhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("天津卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/tjhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("湖北卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/hbhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("山东卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/sdhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("重庆卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/cqhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("上海纪实高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/docuchina.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("江西卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/jxhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("河南卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/hnhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("广西卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/gxhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("吉林卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/jlhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("CETV-1高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/cetv1hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("海南卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/lyhd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("贵州卫视高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/gzhd.m3u8");
        dataList.add(bean);
        for (int i = 1 ; i <=14;i++){
            bean  = new ChannelBean();
            bean.setName("CCTV-"+i);
            bean.setUrl("http://ivi.bupt.edu.cn/hls/cctv"+i+"hd.m3u8");
            dataList.add(bean);
        }
        bean  = new ChannelBean();
        bean.setName("CCTV-15音乐");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/cctv15.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("CCTV-17");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/cctv17hd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("CGTN高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/cgtnhd.m3u8");
        dataList.add(bean);

        bean  = new ChannelBean();
        bean.setName("CGTN DOC高清");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/cgtndochd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("CHC电影");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/chchd.m3u8");
        dataList.add(bean);


        bean  = new ChannelBean();
        bean.setName("北京卫视");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv1hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京文艺");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv2hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京科教");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv3hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京影视");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv4hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京财经");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv5hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京体育");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv6hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京新闻");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv9hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京少儿");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv9hd.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京少儿");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv10.m3u8");
        dataList.add(bean);
        bean  = new ChannelBean();
        bean.setName("北京纪实");
        bean.setUrl("http://ivi.bupt.edu.cn/hls/btv11hd.m3u8");
        dataList.add(bean);


    }
}
