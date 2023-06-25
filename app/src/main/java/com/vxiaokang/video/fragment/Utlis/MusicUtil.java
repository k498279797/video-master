package com.vxiaokang.video.fragment.Utlis;

import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.vxiaokang.video.activity.video.common.UserAgentUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with Android.
 *
 * @CLASS: http://www.6080kan.tv 获取视频地址
 * @Date: 2022/04/19/17:32
 * @Description:
 */
public class MusicUtil {
    private static final String TAG = "MusicUtil";
    private static final long pastTime = 2 * 60 * 60 * 1000;//过期时间
    private static Map<String,String> map = new HashMap<>();//当前可播放路径
    private static Map<String,Long> mapTime = new HashMap<>();//当前可播放路径

    //自定义监听
    public interface MusicListener{
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
     *  https://www.hifini.com/thread-269814.htm
     */
    public static void getUrl(String url,MusicListener listener){
        try {
            String localUrl = getLocationUrl(url);
            if(!StringUtils.isEmpty(localUrl)){
                listener.success(localUrl);
                return;
            }
            new Thread(() -> {
                    try{
                        String domain = "https://www.hifini.com/";
                       // String url = apiUrl+"/play/?148294-5-66.html";
                        //System.out.println(url);
                        Document doc = Jsoup.connect(url)
                                .userAgent(UserAgentUtils.getUserAgent())
                                .ignoreContentType(true).timeout(60000).get();
                        // System.out.println(doc2);
                        if(null != doc){
                            String body = doc.toString();
                            String one = "url: '";
                            if(body.indexOf(one) != -1) {
                                body = body.substring(body.indexOf(one)+one.length());
                                body = body.substring(0, body.indexOf("',"));
                                if(!StringUtils.isEmpty(body)){
                                    if(body.startsWith("http")){
                                        map.put(url,body);
                                        mapTime.put(url,System.currentTimeMillis());
                                        listener.success(body);
                                    }else{
                                        String apiUrl = domain+body;
                                        Log.d(TAG,"apiUrl = "+apiUrl);
                                        map.put(url,apiUrl);
                                        mapTime.put(url,System.currentTimeMillis());
                                        listener.success(apiUrl);
                                        //getRealUrl(apiUrl,listener);
                                    }

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
            e.printStackTrace();
            listener.error("数据异常、请联系客服");
        }
    }


    public static void getRealUrl(String url,MusicListener listener){
        try{
            new Thread(() -> {
               try {

                  // String music = Jsoup.parse(url).location();
                  // Document document = Jsoup.parse(new URL(url), 5000);
                   Document document = Jsoup.connect(url)
                           //.header("Content-Type", "application/json;charset=UTF-8")
                           .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36")
                           .ignoreContentType(true)
                           .followRedirects(true)
                           .timeout(120000).get();
                 /*  Document document = Jsoup.connect(url)
                           //.header("Content-Type", "application/json;charset=UTF-8")
                           .userAgent(UserAgentUtils.getUserAgent())
                           .ignoreContentType(true)
                           .followRedirects(true)
                           .timeout(120000).parser();
                   Log.d(TAG,"apiUrl =22 "+document);*/
                 /*  if(null == document){
                       listener.error("地址解析失败");
                       return;
                   }*/
                   String music = document.location();
                   if(StringUtils.isEmpty(music) || music.contains("music.163.com")) {
                       listener.error("地址解析失败");
                       return;
                   }
                   map.put(url,music);
                   mapTime.put(url,System.currentTimeMillis());
                   listener.success(music);
               }catch (Exception e){
                   e.printStackTrace();
                   listener.error("数据异常、请联系客服");
               }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
            listener.error("数据异常、请联系客服");
        }
    }
}
