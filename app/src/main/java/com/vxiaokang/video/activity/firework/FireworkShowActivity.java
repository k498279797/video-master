package com.vxiaokang.video.activity.firework;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;

import me.leefeng.promptlibrary.PromptDialog;

public class FireworkShowActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "FireworkShowActivity";
    private ImageView ivBack;
    private PromptDialog promptDialog;
    private FireworkShow fireworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firework_activity);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        promptDialog = new PromptDialog(this);
        promptDialog.setViewAnimDuration(1000);
        initView();
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        fireworks = findViewById(R.id.fireworks);
        fireworks.startFireworkShow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fireworks.stopFireworkShow();
    }
}
