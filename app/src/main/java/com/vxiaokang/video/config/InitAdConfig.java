package com.vxiaokang.video.config;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.vxiaokang.video.constants.ConfigKey;

/**
 * 广告开关配置
 */
public class InitAdConfig {
    /***广告开关 0 开 1 关闭**/
    public static String getAppAdSwitch(){
        return SPUtils.getInstance().getString(ConfigKey.APP_AD_SWITCH,"-1");
    }
    /**
     * 广告开启
     * @return
     */
    public static boolean isOpenFlag(){
        String flag = SPUtils.getInstance().getString(ConfigKey.APP_AD_SWITCH,"-1");
        if("0".equals(flag)){
            return true;
        }else{
            return false;
        }
    }
    /***支付开关 0 开 1 关闭**/
    public static String getAppPaySwitch(){
        return SPUtils.getInstance().getString(ConfigKey.APP_PAY_SWITCH,"-1");
    }
    /**
     * 支付开启
     * @return
     */
    public static boolean isOpenPayFlag(){
        String flag = SPUtils.getInstance().getString(ConfigKey.APP_PAY_SWITCH,"-1");
        if("0".equals(flag)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 协议是否开启
     * @return
     */
    public static boolean isAgreement(){
        String ysxy =  SPUtils.getInstance().getString("ysxy", "0");
        if("1".equals(ysxy)){
            return true;
        }
        return false;
    }

    public static boolean isHuaWeiFlag(){
        String flag = SPUtils.getInstance().getString(ConfigKey.APP_CHANNEL_NO,"-1");
        if(StringUtils.equalsIgnoreCase(flag,"huawei")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isDebug(){
        String flag = SPUtils.getInstance().getString(ConfigKey.APP_DEBUG,"0");
        if(StringUtils.equalsIgnoreCase(flag,"0")){
            return false;
        }else{
            return true;
        }
    }

    public static void setDebug(String value){
        SPUtils.getInstance().put(ConfigKey.APP_DEBUG,value);
    }
}
