package com.vxiaokang.video.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.ClassListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.IntentParamsBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.bean.req.ReqVideoBean;
import com.vxiaokang.video.bean.resp.RespVideoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.request.RequestGetVideo;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.view.StandardVideoController;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/****
 * ??????
 */
public class ClassDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClassDetailActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //??

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private TextView currVideoViews;
    private ImageView currVideoImg;


    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private ClassListDataAdapter classAdapter;
    private IntentParamsBean paramsBean = new IntentParamsBean();
    private int currentPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        EventBus.getDefault().register(this);
        initParams();
        initView();
    }
    private void initParams(){
        String fromType = getIntent().getStringExtra(Constants.fromType);
        String sourceUrl = getIntent().getStringExtra(Constants.sourceUrl);
        String coverImage = getIntent().getStringExtra(Constants.coverImage);
        String sourceTitle = getIntent().getStringExtra(Constants.sourceTitle);
        String sourceViews = getIntent().getStringExtra(Constants.sourceViews);
        String sourceDuration = getIntent().getStringExtra(Constants.sourceDuration);
        String sourceItemId = getIntent().getStringExtra(Constants.sourceItemId);
        paramsBean.setFromType(fromType);
        sourceUrl = "https://iqiyi.cdn9-okzy.com/20200904/15026_8c4175d4/1000k/hls/index.m3u8";
        paramsBean.setSourceUrl(sourceUrl);
        paramsBean.setCoverImage(coverImage);
        paramsBean.setSourceTitle(sourceTitle);
        paramsBean.setSourceViews(sourceViews);
        paramsBean.setSourceDuration(sourceDuration);
        paramsBean.setSourceItemId(sourceItemId);
        Log.d(TAG,"paramsBean =" + new Gson().toJson(paramsBean));
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        txt_show_set_back = findViewById(R.id.txt_show_set_back);

        dkPlayer = findViewById(R.id.dk_player);
        videoContainer = findViewById(R.id.video_detail_container);
        currVideoImg = findViewById(R.id.curr_video_img);
        icPlayBtn = findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);

        currVideoTitle = findViewById(R.id.curr_video_title);
        currVideoViews = findViewById(R.id.curr_video_views);
        classRecView = findViewById(R.id.detail_rec_view);
        if(!this.isDestroyed()) {
            //Glide.with(this).load(paramsBean.getCoverImage()).into(currVideoImg);
            GlideNetWorkImageUtils.showNetworkImage(this,paramsBean.getCoverImage(),currVideoImg);
        }
        currVideoTitle.setText(paramsBean.getSourceTitle());
        currVideoViews.setText(paramsBean.getSourceViews()+"???");

        dkPlayer.setUrl(paramsBean.getSourceUrl());
        //????
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(paramsBean.getSourceTitle(), false);
        dkPlayer.setVideoController(controller);


        //  VideoInfoBean bean1 = new VideoInfoBean();
        //  bean1.setTitle("???????????");
        //  bean1.setCoverImage("http://119.23.211.203/images/chess/introduction/6.png");
        //   bean1.setUpVote("23541");
        //   videoInfoList.add(bean1);
        //    videoInfoList.add(bean1);
        setCourseVideoList(TextUtils.isEmpty(paramsBean.getSourceItemId())?  "" : paramsBean.getSourceItemId());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        classAdapter = new ClassListDataAdapter(this,videoInfoList);
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
        // Do something ??????
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
                VideoInfoBean videoInfoBean =  videoInfoList.get(position);
                if(!this.isDestroyed()) {
                   // Glide.with(this).load(videoInfoBean.getCoverImage()).into(currVideoImg);
                    GlideNetWorkImageUtils.showNetworkImage(this,paramsBean.getCoverImage(),currVideoImg);
                }
                currVideoTitle.setText(videoInfoBean.getTitle());
                currVideoViews.setText(videoInfoBean.getUpVote());

                // currVideoDuration.setText(videoInfoBean.getDuration());
                dkPlayer.release();
                Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
                dkPlayer.setUrl(videoInfoBean.getLink());
                currVideoImg.setVisibility(View.GONE);
                videoContainer.setVisibility(View.GONE);
                icPlayBtn.setVisibility(View.GONE);
                currentPosition = position;

                StandardVideoController controller = new StandardVideoController(this);
                controller.addDefaultControlComponent(videoInfoBean.getTitle(), false);
                dkPlayer.setVideoController(controller);
                dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
                //  playFlagMap.put("video_"+currentPosition,"1");
                dkPlayer.start();
            }
        }catch (Exception e){

        }
    }

    public void setCourseVideoList(String categoryId){
        try{
            ReqVideoBean params = new ReqVideoBean();
            params.setCategoryId(categoryId);
            RequestGetVideo.getData(params, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    //  promptDialog.dismiss();
                    Logger.d(TAG,"????:" + e.getMessage());
                }
                @Override
                public void onResponse(String response, int id) {
                    //  promptDialog.dismiss();
                    Logger.d(TAG,"??:" + response);
                    RespVideoBean bean = new Gson().fromJson(response, RespVideoBean.class);
                    if (null != bean && "0".equals(bean.getCode())){
                        if(null != bean.getRows()){
                            videoInfoList = bean.getRows();
                            classAdapter.updateData(bean.getRows());
                        }
                    }
                }
            });
        }catch (Exception e){}
    }

}