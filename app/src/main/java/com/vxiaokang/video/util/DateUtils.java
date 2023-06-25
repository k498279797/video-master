package com.vxiaokang.video.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转化
 */
public class DateUtils {

    public static String dataToString(Date date,String format){
        try{
            if(EmptyUtils.isEmpty(format)){
                format = "yyyy-MM-dd HH:mm:ss";
            }
            if(null != date){
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(date); 
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
