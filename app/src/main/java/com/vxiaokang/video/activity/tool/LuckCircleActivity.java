package com.vxiaokang.video.activity.tool;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.tool.luck.LuckCircle;
import com.vxiaokang.video.base.system.StatusBarUtil;

public class LuckCircleActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "SleepActivity";
    private ImageView ivBack;
    private LuckCircle luckView;

    private Button starBtn;
    private Button paseBtn;
    private Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_circle);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView(){
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        luckView = findViewById(R.id.luckView);
        luckView.luckEnd();

        starBtn = findViewById(R.id.starBtn);
        starBtn.setOnClickListener(this);
        paseBtn = findViewById(R.id.paseBtn);
        paseBtn.setOnClickListener(this);
        stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.starBtn:
                    luckView.luckyStart();
                break;
            case R.id.paseBtn:
            case R.id.stopBtn:
                luckView.luckEnd();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }


}