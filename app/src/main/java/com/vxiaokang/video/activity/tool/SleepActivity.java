package com.vxiaokang.video.activity.tool;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;

public class SleepActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "SleepActivity";
    private ImageView ivBack;
    private ImageView img;
    private Button starBtn;
    private Button bigBtn;
    private Button smallBtn;

    private EditText big;
    private EditText stop;
    private EditText small;
    private AnimationSet as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_circle);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView(){
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        big = findViewById(R.id.big);
        stop = findViewById(R.id.stop);
        small = findViewById(R.id.small);

        img = findViewById(R.id.iv_wave);
        starBtn = findViewById(R.id.starBtn);
        starBtn.setOnClickListener(this);
        bigBtn = findViewById(R.id.bigBtn);
        bigBtn.setOnClickListener(this);
        smallBtn = findViewById(R.id.smallBtn);
        smallBtn.setOnClickListener(this);
       // setAnim(4000,7000,4000);
      //  new Handler().postDelayed(() -> setAnim2(), 4000);
         //as = new AnimationSet(true);
    }

    private void setAnim2() {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.5f, 0.5f, 1.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(4000);
        scaleAnimation.setRepeatCount(1);

        //渐变动画 透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.8f);

        //暂停
        ScaleAnimation scaleAnimationStop = new ScaleAnimation(1.5f, 1.5f, 1.5f, 1.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationStop.setStartOffset(2870);
        scaleAnimationStop.setDuration(7000);
        AlphaAnimation alphaAnimationStop = new AlphaAnimation(0.8f, 0.8f);
        alphaAnimationStop.setStartOffset(2870);

        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.5f, 0.5f, 1.5f, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setStartOffset(2870);
        scaleAnimation2.setDuration(4000);
        //渐变动画
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.8f, 1f);
        alphaAnimation2.setStartOffset(2870);

        as.setDuration(15000);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);

        as.addAnimation(scaleAnimationStop);
        as.addAnimation(alphaAnimationStop);

        as.addAnimation(scaleAnimation2);
        as.addAnimation(alphaAnimation2);
        as.setFillAfter(true);               //保留在最后一帧
       // as.setFillBefore(true);
        as.setFillEnabled(true);
        img.startAnimation(as);
    }

    private void setAnimBig(long durationMillis, Animation.AnimationListener listener) {
      //  as.reset();
        as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.5f, 1f, 1.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
       // scaleAnimation.setDuration(4000);
        scaleAnimation.setDuration(durationMillis);
        //渐变动画 透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.5f);

        //as.setDuration(4000);
        as.setDuration(durationMillis);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);

        as.setFillAfter(true);               //保留在最后一帧
        // as.setFillBefore(true);
        as.setFillEnabled(true);
        img.startAnimation(as);
        if(null != listener){
            as.setAnimationListener(listener);
        }
         /*as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG,"setAnimBig onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG,"setAnimBig onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG,"setAnimBig onAnimationRepeat");
            }
        });*/
    }
    private void setAnimSmall(long durationMillis,Animation.AnimationListener listener) {
       // as.reset();
        as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 1f, 1.5f, 1f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //scaleAnimation.setDuration(4000);
        scaleAnimation.setDuration(durationMillis);

        //渐变动画 透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1f);
        as.cancel();
      //  as.setDuration(4000);
        as.setDuration(durationMillis);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        as.setFillAfter(true);               //保留在最后一帧
        // as.setFillBefore(true);
        as.setFillEnabled(true);
        if(null != listener){
            as.setAnimationListener(listener);
        }

        /*as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG,"setAnimSmall onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG,"setAnimSmall onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG,"setAnimSmall onAnimationRepeat");
            }
        });*/
        img.startAnimation(as);
    }

    private void setAnim(int bigTime,int stopTime,int smallTime) {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.6f, 0.8f, 1.6f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 1f);
        scaleAnimation.setDuration(bigTime);

        //暂停
        ScaleAnimation scaleAnimationStop = new ScaleAnimation(1.6f, 1.6f, 1.6f, 1.6f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimationStop.setStartOffset(2870);
        scaleAnimationStop.setDuration(stopTime);
        AlphaAnimation alphaAnimationStop = new AlphaAnimation(0.5f, 0.5f);
        alphaAnimationStop.setStartOffset(2870);

        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.6f, 0.8f, 1.6f, 0.8f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setStartOffset(2870);
        scaleAnimation2.setDuration(smallTime);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.5f, 1f);
        alphaAnimation2.setStartOffset(2870);

        int time = bigTime+stopTime+smallTime;
        as.setDuration(time);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);

        as.addAnimation(scaleAnimationStop);
        as.addAnimation(alphaAnimationStop);

        as.addAnimation(scaleAnimation2);
        as.addAnimation(alphaAnimation2);
        as.setFillAfter(true);               //保留在最后一帧
        as.setFillEnabled(true);
        img.startAnimation(as);

    }


    @Override
    public void onClick(View v) {
        long bigTime = 4000;
        long stopTime = 4000;
        long smallTime = 4000;
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.starBtn:
                 bigTime = Long.valueOf(big.getText().toString());
                 stopTime = Long.valueOf(stop.getText().toString());
                 smallTime = Long.valueOf(small.getText().toString());
                //setAnim(bigTime,stopTime,smallTime);
                long finalStopTime = stopTime;
                long finalSmallTime = smallTime;
                setAnimBig(bigTime,new Animation.AnimationListener(){

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(finalStopTime > 0){
                            new Handler().postDelayed(() -> setAnimSmall(finalSmallTime,null), finalStopTime);
                        }
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                break;
            case R.id.bigBtn:
                bigTime = Long.valueOf(big.getText().toString());
                setAnimBig(bigTime,new Animation.AnimationListener(){

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.smallBtn:
                smallTime = Long.valueOf(small.getText().toString());
                setAnimSmall(smallTime,new Animation.AnimationListener(){

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
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