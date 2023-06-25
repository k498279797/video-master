package com.vxiaokang.video.activity.video.clm.bean;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.activity.video.shipin.bean.ShipinCategory;

import java.util.ArrayList;
import java.util.List;

public class CLMConstant {
    /**
     * 
     *  http://www.磁力猫.com
     *  https://www.cilimiao.cn
     *  https://cilimcn.cilizhizhu.vip
     *
     */
    public static List<ShipinCategory> cateList = new ArrayList<>();
    //路由
    public static String[] tabName = new String[]{"clm374.buzz","clm375.buzz","clm376.buzz","clm377.buzz","clm378.buzz","clm379.buzz","clm380.buzz","clm381.buzz"};
    public static String[] tabValue = new String[]{"https://clm374.buzz","https://clm375.buzz","https://clm376.buzz","https://clm377.buzz","https://clm378.buzz","https://clm379.buzz","https://clm380.buzz","https://clm381.buzz"};

    public static String tabDefault = "[\"https://clm374.buzz\",\"https://clm375.buzz\",\"https://clm376.buzz\",\"https://clm377.buzz\",\"https://clm378.buzz\",\"https://clm379.buzz\",\"https://clm380.buzz\",\"https://clm381.buzz\"]";
    //类型
    public static String[] typeName = new String[]{"全部","影视","音乐","图像","文档书籍","压缩文件","安装包","其他"};
    public static String[] typeValue = new String[]{"0","1","2","3","4","5","6","7"};
    //排序
    public static String[] orderName = new String[]{"热度","相关热度","文件大小","添加时间","最近访问"};
    public static String[] orderValue = new String[]{"3","0","1","2","4"};

   // public static String domain_url = "acc.dasaidao.com";
    public static String getReqDomain(){
        return SPUtils.getInstance().getString("clm_domain_url", "http://www.磁力猫.com");
    }

    public static void setReqDomain(String value){
         SPUtils.getInstance().put("clm_domain_url", value);
    }


    public static String getReqLineDomain(){
        return SPUtils.getInstance().getString("clm_line_domain_url", "https://clm374.buzz");
    }

    public static void setReqLineDomain(String value){
        SPUtils.getInstance().put("clm_line_domain_url", value);
    }

    public static String[] getReqTabName(){
        String string =  SPUtils.getInstance().getString("clm_tab_name", tabDefault);
        List<String> list = new Gson().fromJson(string,new TypeToken<List<String>>(){}.getType());
        for(int i = 0 ; i < list.size() ; i++){
            String item = list.get(i);
            if(item.startsWith("http")){
                item = item.replace("https://","");
                item = item.replace("http://","");
                list.set(i,item);
            }
        }
        String[] textArr = new String[list.size()];
        return list.toArray(textArr);
    }

    public static void setReqTabName(String value){
        SPUtils.getInstance().put("clm_tab_name", value);
    }

    public static String[] getReqTabValue(){
        String string = SPUtils.getInstance().getString("clm_tab_value", tabDefault);
        List<String> list = new Gson().fromJson(string,new TypeToken<List<String>>(){}.getType());
        String[] textArr = new String[list.size()];
        return list.toArray(textArr);
    }

    public static void setReqTabValue(String value){
        SPUtils.getInstance().put("clm_tab_value", value);
    }

}
