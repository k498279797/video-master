package com.vxiaokang.video.activity.tool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;

/****
 * 水波纹扩散
 */
public class WaterRippleActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "WaterRippleActivity";
    private ImageView ivBack;

    private Button showResult;
    private Button showResult2;
    private Button setAuto;

    private ImageView iv_wave; // -中心imageView
    private ImageView iv_wave_1; //中间
    private ImageView iv_wave_2; //外层
    private FrameLayout waterRipple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_ripple);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
        txtRotation();

    }

    private void txtRotation(){
        showResult = findViewById(R.id.showResult);
        showResult.setOnClickListener(this);
        showResult2 = findViewById(R.id.showResult2);
        showResult2.setOnClickListener(this);

        setAuto = findViewById(R.id.setAuto);
        setAuto.setOnClickListener(this);

        iv_wave = findViewById(R.id.iv_wave);
        iv_wave_1 = findViewById(R.id.iv_wave_1);
        iv_wave_2 = findViewById(R.id.iv_wave_2);

        waterRipple = findViewById(R.id.waterRipple);
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);
    }

    private void setAnim1(View view) {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从原始放大到1.4倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);

        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(Animation.RESTART);
        alphaAnimation.setRepeatCount(Animation.RESTART);
        as.setDuration(800);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        view.startAnimation(as);
    }
    private void setAnim2(View view) {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f, 1.8f, 1.4f, 1.8f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 0.1f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(Animation.RESTART);
        alphaAnimation.setRepeatCount(Animation.RESTART);
        as.setDuration(800);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        view.startAnimation(as);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.showResult:
               // waterRipple.removeAllViews();
               // View view = LayoutInflater.from(this).inflate(R.layout.activity_water_ripple_item, null);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                View  view = LayoutInflater.from(this).inflate(R.layout.activity_water_ripple_item, null);
                View  view1 = view.findViewById(R.id.iv_wave_1);
                View  view2 = view.findViewById(R.id.iv_wave_2);
                view.setLayoutParams(lp);



                waterRipple.addView(view);
                setAnim1(view1);
                setAnim2(view2);
                break;
            case R.id.showResult2:
                //setAnim2();
                //setAnim1();
                break;
            case R.id.setAuto:
                for(int i = 0  ; i < 2 ; i++){
                  //  setAnim1();
                   // setAnim2();
                }
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
