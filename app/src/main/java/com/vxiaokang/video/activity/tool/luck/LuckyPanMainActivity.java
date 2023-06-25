package com.vxiaokang.video.activity.tool.luck;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;

public class LuckyPanMainActivity extends AppCompatActivity implements View.OnClickListener {

    private LuckyPanView mLuckyPanView;
    private ImageView iv_go;
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_pan_main);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);


        mLuckyPanView =  findViewById(R.id.surface_pan);
        iv_go =  findViewById(R.id.iv_go);
        iv_go.setOnClickListener(v -> {
            if (!mLuckyPanView.isStart()) {
                mLuckyPanView.luckyStart(1);
                iv_go.setImageResource(R.drawable.stop);
            } else {
                if (!mLuckyPanView.isShouldEnd()) {
                    mLuckyPanView.luckyEnd();
                    iv_go.setImageResource(R.drawable.start);
                }
            }
        });
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
}
