package com.vxiaokang.video.activity.video.common;

import java.util.ArrayList;
import java.util.List;

public class UserAgentUtils {
    public static List<String> userAgentList = new ArrayList<String>();
    static {
        userAgentList.add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36");
        userAgentList.add("Opera/9.80 (Windows NT 5.2; U; ru) Presto/2.7.62 Version/11.01");
        userAgentList.add("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1500.55 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.93 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.93 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (X11; CrOS i686 3912.101.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10; rv:33.0) Gecko/20100101 Firefox/33.0");

    }

    public static String getUserAgent(){
        int num = (int)(Math.random()*userAgentList.size());
        return userAgentList.get(num);
    }
}
