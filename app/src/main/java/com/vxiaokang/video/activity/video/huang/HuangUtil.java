package com.vxiaokang.video.activity.video.huang;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.activity.video.bean.ResultVideoBean;
import com.vxiaokang.video.activity.video.bean.ResultVideoItemInfoBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.codec.Base64;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Android.
 *
 * @CLASS: 小黄书
 * @Date: 2022/04/19/17:32 
 * @Description:
 */
public class HuangUtil {
    private static final String TAG = "HuangUtil";
    private static final long pastTime = 2 * 60 * 60 * 1000;//过期时间
    private static Map<String,String> map = new HashMap<>();//当前可播放路径
    private static Map<String,Long> mapTime = new HashMap<>();//当前可播放路径

    //自定义监听
    public interface HuangListener{
        void success(String path);
        void error(String msg);
    }

    private static String getLocationUrl(String videoId){
        long currentTime = System.currentTimeMillis();
        if(map.containsKey(videoId)){
            long firstTime = mapTime.get(videoId);
            if(currentTime-firstTime >= pastTime){ //播放地址失效
                return null;
            }
            return map.get(videoId);
        }
        return null;
    }

    /**
     * 请求更新链接
     * @param videoId 视频ID
     */
    public static void getUrl(String videoId,HuangListener listener){
        try {
            String localUrl = getLocationUrl(videoId);
            if(!StringUtils.isEmpty(localUrl)){
                listener.success(localUrl);
                return;
            }
            videoInfo(videoId,listener);
        }catch (Exception e){
            listener.error("数据异常、请联系客服");
        }
    }

    private static void videoInfo(String videoId,HuangListener listener) {
        try{
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
              //  String videoId = "5953";
                String apiUrl = "https://app.rk5ck5dzx.com/api/video/info?uid=2&vid=%s&d_device=h5&v_code=252";
                try{
                    apiUrl = String.format(apiUrl, videoId);
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).post().text();
                    String string = new String(Base64.decodeBase64(document));
                    ResultVideoBean result = new Gson().fromJson(string,new TypeToken<ResultVideoBean>(){}.getType());
                    if(null !=result && null != result.getData() && null != result.getData().getInfo() && null != result.getData().getMedia()) {
                       // ResultVideoItemInfoBean data = result.getData().getInfo();
                        ResultVideoItemInfoBean media = result.getData().getMedia();
                        if( null != media.getMedia_url() && media.getMedia_url().size() > 0) {
                            // https://app.rk5ck5dzx.com/api/index.m3u8?m=dUYzZjBsSWhiM2c2amxacU5qZDlVNHVsVWpoRFJIQWV4emxKcGh0WUFrZVFyeUJJZC8rVUVuZDFNWFdxK2E4eGlvNnpCVWxBbENXdW1TM3lmbUdHVVE9PQ==&t=1665459779&k=5730376599645c00b37984c43fb9affd&start=90&end=110
                            String end = "&start";
                            String url = media.getMedia_url().get(0).get("src");
                            if(url.contains(end)){
                                url = url.substring(0,url.indexOf(end));
                            }
                            map.put(videoId,url);
                            mapTime.put(videoId,System.currentTimeMillis());
                            listener.success(url);
                        }else{
                            listener.error("获取异常");
                        }
                    }else{
                        listener.error("获取异常");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    listener.error("获取异常");
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
            listener.error("获取异常");
        }
    }

}
