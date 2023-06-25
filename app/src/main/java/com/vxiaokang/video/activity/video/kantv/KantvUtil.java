package com.vxiaokang.video.activity.video.kantv;

import com.blankj.utilcode.util.StringUtils;
import com.vxiaokang.video.activity.video.common.UserAgentUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Android.
 *
 * @CLASS: http://www.6080kan.tv 获取视频地址
 * @Date: 2022/04/19/17:32
 * @Description:
 */
public class KantvUtil {
    private static final String TAG = "KdyUtil";
    private static final long pastTime = 2 * 60 * 60 * 1000;//过期时间
    private static Map<String,String> map = new HashMap<>();//当前可播放路径
    private static Map<String,Long> mapTime = new HashMap<>();//当前可播放路径

    //自定义监听
    public interface KantvListener{
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
     *  http://www.6080kan.tv/play/92799-0-0.html
     */
    public static void getUrl(String url,KantvListener listener){
        try {
            String localUrl = getLocationUrl(url);
            if(!StringUtils.isEmpty(localUrl)){
                listener.success(localUrl);
                return;
            }
            new Thread(() -> {
                    try{
                       // String url = apiUrl+"/play/?148294-5-66.html";
                        //System.out.println(url);
                        Document doc = Jsoup.connect(url)
                                .userAgent(UserAgentUtils.getUserAgent())
                                .ignoreContentType(true).timeout(60000).get();
                        // System.out.println(doc2);
                        if(null != doc){
                            String body = doc.toString();
                            String one = "now=\"";
                            if(body.indexOf(one) != -1) {
                                body = body.substring(body.indexOf(one)+one.length());
                                body = body.substring(0, body.indexOf("\";"));
                                if(!StringUtils.isEmpty(body)){
                                    map.put(url,body);
                                    mapTime.put(url,System.currentTimeMillis());
                                    listener.success(body);
                                }else{
                                    listener.error("地址解析失败");
                                }
                            }else{
                                listener.error("地址解析失败");
                            }
                        }else{
                            listener.error("获取地址失败");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                        listener.error("请求异常");
                    }
            }).start();
        }catch (Exception e){
            listener.error("数据异常、请联系客服");
        }
    }
}
