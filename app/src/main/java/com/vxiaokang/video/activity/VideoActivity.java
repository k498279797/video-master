package com.vxiaokang.video.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.VideoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.IntentParamsBean;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.view.StandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/****
 * 课程详情列表
 */
public class VideoActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClassDetailActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //标题

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private TextView currVideoViews;
    private ImageView currVideoImg;


    private RecyclerView classRecView;
    private List<String> videoInfoList = new ArrayList<>();
    private VideoListDataAdapter classAdapter;
    private IntentParamsBean paramsBean = new IntentParamsBean();
    private int currentPosition = -1;
    private LinearLayout copyBtn;

    private String currentUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
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


        if(videoInfoList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/video.json","utf-8");
            if(!StringUtils.isEmpty(json)){
                videoInfoList = new Gson().fromJson(json,new TypeToken<List<String>>(){}.getType());
               // videoInfoList.add(0,"https://emb4.gaytubewatch.com/best_clips/a7/1f/1527463175.6356.mp4?e=1671445169&hash=GPYK-al0v10io_3_8lIRfg");
            }
        }

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        txt_show_set_back.setText("视频播放");

        copyBtn =  findViewById(R.id.copyBtn);
        copyBtn.setOnClickListener(this);

        dkPlayer = findViewById(R.id.dk_player);
        videoContainer = findViewById(R.id.video_detail_container);
        currVideoImg = findViewById(R.id.curr_video_img);
        icPlayBtn = findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);

        currVideoTitle = findViewById(R.id.curr_video_title);
        currVideoViews = findViewById(R.id.curr_video_views);
        classRecView = findViewById(R.id.detail_rec_view);

        //Glide.with(this).load(paramsBean.getCoverImage()).into(currVideoImg);
        //currVideoTitle.setText(paramsBean.getSourceTitle());
        //currVideoViews.setText(paramsBean.getSourceViews()+"次观看");

        dkPlayer.setUrl(videoInfoList.get(0));
        //必须设置
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("", false);
        dkPlayer.setVideoController(controller);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        classAdapter = new VideoListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                updateVideoInfo(position);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

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
            case R.id.copyBtn:
                clipboardContent(currentUrl);
                break;
            default:
                break;
        }
    }
    public void clipboardContent(String content){
        try{
            if(EmptyUtils.isNotEmpty(content)){
                ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(content); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
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
    public void updateVideoInfo(int position){
        try{
            if(position>=0 && null != videoInfoList && videoInfoList.size()-1>= position){
                String videoInfoBean =  videoInfoList.get(position);
               // Glide.with(this).load(videoInfoBean.getCoverImage()).into(currVideoImg);
               // currVideoTitle.setText(videoInfoBean.getTitle());
               // currVideoViews.setText(videoInfoBean.getUpVote());
                currentUrl = videoInfoBean;
               // currVideoDuration.setText(videoInfoBean.getDuration());
                dkPlayer.release();
               // Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
                dkPlayer.setUrl(videoInfoBean);
                currVideoImg.setVisibility(View.GONE);
                videoContainer.setVisibility(View.GONE);
                icPlayBtn.setVisibility(View.GONE);
                currentPosition = position;

                StandardVideoController controller = new StandardVideoController(this);
                controller.addDefaultControlComponent("", false);
                dkPlayer.setVideoController(controller);
              //  playFlagMap.put("video_"+currentPosition,"1");
                dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
                dkPlayer.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
