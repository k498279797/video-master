package com.vxiaokang.video.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.view.StandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/****
 * 视频播放
 */
public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClassDetailActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //标题

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private TextView currVideoViews;
    private ImageView currVideoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);


        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        txt_show_set_back.setText("视频播放");

        dkPlayer = findViewById(R.id.dk_player);
        videoContainer = findViewById(R.id.video_detail_container);
        currVideoImg = findViewById(R.id.curr_video_img);
        icPlayBtn = findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);

        currVideoTitle = findViewById(R.id.curr_video_title);
        currVideoViews = findViewById(R.id.curr_video_views);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String videoTitle = getIntent().getStringExtra("videoTitle");
        String views = getIntent().getStringExtra("views");
        String imgUrl = getIntent().getStringExtra("imgUrl");
        if(StringUtils.isEmpty(url)){
            url = "https://s.xlzys.com/play/rb283vze/index.m3u8";
        }
        if(StringUtils.isEmpty(title)){
            title = "";
        }else{
            txt_show_set_back.setText(title);
        }

        if(!StringUtils.isEmpty(views)){
            currVideoViews.setText(views);
        }
        if(!StringUtils.isEmpty(videoTitle)){
            currVideoTitle.setText(videoTitle);
        }
        if(!StringUtils.isEmpty(imgUrl)){
            if(!this.isDestroyed()){
              /*  Glide.with(this).load(imgUrl).placeholder(R.mipmap.error)
                        .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                        .error(R.mipmap.error).into(currVideoImg);*/
                GlideNetWorkImageUtils.showNetworkImage(this,imgUrl,currVideoImg);
            }
        }
        //
        //currVideoTitle.setText(paramsBean.getSourceTitle());
        //currVideoViews.setText(paramsBean.getSourceViews()+"次观看");

        dkPlayer.setUrl(url);
        //必须设置
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(title, false);
        dkPlayer.setVideoController(controller);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.video_detail_container:
            case R.id.ic_play_btn:
                currVideoImg.setVisibility(View.GONE);
                videoContainer.setVisibility(View.GONE);
                icPlayBtn.setVisibility(View.GONE);
                dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
                dkPlayer.start();
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
    public void onPause() {
        super.onPause();
        dkPlayer.pause();
    }


    @Override
    public void onResume() {
        super.onResume();
        dkPlayer.pause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        // Do something 变速逻辑操作
        dkPlayer.setSpeed(event.getSpeed());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dkPlayer.release();
        EventBus.getDefault().unregister(this);
    }



}
