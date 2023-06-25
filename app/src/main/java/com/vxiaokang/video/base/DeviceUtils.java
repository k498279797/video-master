package com.vxiaokang.video.base;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vxiaokang.video.bean.req.ReqAddDevice;
import com.vxiaokang.video.request.RequsetDevice;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class DeviceUtils {
    private static  String TAG = "DeviceUtils";
    /**
     * 统计
     */
    public static void addDevice(Context context){
        try {
            ReqAddDevice params = new ReqAddDevice();
            String manufacturer = android.os.Build.MANUFACTURER;  //制造商
            String  uUID = String.valueOf(DeviceUuidFactory.getInstance(context).getDeviceUuid());
            String mChannel = UmUtils.getChannelName(context);
            String mDevice = manufacturer;
            params.setChannelId(mChannel);
            params.setDeviceNo(uUID);
            params.setDeviceName(mDevice);
            Log.e("addDevice","开始设备上报..."+new Gson().toJson(params));
            RequsetDevice.addData(params, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    //  promptDialog.dismiss();
                    Log.e(TAG,"error :" + e.getMessage());
                }
                @Override
                public void onResponse(String response, int id) {
                    //  promptDialog.dismiss();
                    Log.e(TAG,"resp:" + response);
                }
            });
        }catch (Exception e){
            // e.printStackTrace();
            Log.e(TAG,"设备上报异常err ="+e.getMessage());
        }
    }
}
