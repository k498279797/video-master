package com.vxiaokang.video.activity.tool;

import android.content.Context;



public class AdFilterTool {
    private static String[] filterUrls;
    public static boolean isAd(Context context, String url) {
        if(getFilterUrls() != null && getFilterUrls().length>0){
            for (String adUrl : getFilterUrls() ) {
                if (url.contains(adUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static String[] getFilterUrls() {
        return filterUrls;
    }

    public void setFilterUrls(String[] filterUrls) {
        this.filterUrls = filterUrls;
    }
}
