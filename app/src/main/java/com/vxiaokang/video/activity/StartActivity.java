package com.vxiaokang.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.UmUtils;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.constants.ConfigKey;


/**
 * 开屏页
 */
public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    private FrameLayout mSplashContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        StatusBarUtil.transparencyBar(this);
        mSplashContainer = findViewById(R.id.start_splash_container);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initData();
      /*  YsDialog.showYsDialog(this,new YsDialog.YsDialogListener(){
            @Override
            public void success() {
                //隐私协议

            }
            @Override
            public void error() {
                App.exitAPP();
            }
        });*/

       // initData();

    }

    private void initData(){
        String currentChannelNo = "huawei";
        String channelName = UmUtils.getChannelName(this);
        if(!TextUtils.isEmpty(channelName)){
            currentChannelNo =  channelName;
        }
     //   Log.d(TAG,"currentChannelNo-"+currentChannelNo);
        SPUtils.getInstance().put(ConfigKey.APP_CHANNEL_NO,currentChannelNo);

        goToMainActivity();
    }

    /**
     * 跳转到主页面
     */
    private void goToMainActivity() {

        new Handler().postDelayed(() -> toMainActivity(), 800);
    }
    private void toMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        mSplashContainer.removeAllViews();
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        finish();
    }
}
