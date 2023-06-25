package com.vxiaokang.video.activity.video.huang;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.bean.ResultKeywordBean;
import com.vxiaokang.video.activity.video.bean.ResultKeywordItemInfoBean;
import com.vxiaokang.video.activity.video.bean.ResultVideoBean;
import com.vxiaokang.video.activity.video.bean.ResultVideoItemInfoBean;
import com.vxiaokang.video.adapter.HuangVideoDetailDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.IntentParamsBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.codec.Base64;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.view.StandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/****
 * 课程详情列表
 */
public class HuangVideoDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "HuangVideoDetailActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //标题

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private TextView currVideoViews;
    private ImageView currVideoImg;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private HuangVideoDetailDataAdapter classAdapter;
    private IntentParamsBean paramsBean = new IntentParamsBean();
    private int currentPosition = -1;
    private String topTitle;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int total = 0;
    private int totalPage = 1;
    private boolean loadFlag = true;
    private String categoryId = "";
    private String searchVal = "";

    private String currentPlayUrl = "";
    private LinearLayout copyBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huagn_video_detail);
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
        String sourceId = getIntent().getStringExtra(Constants.sourceId);
        String searchValTemp = getIntent().getStringExtra("searchVal");
        if(!StringUtils.isEmpty(searchValTemp)){
            searchVal = searchValTemp;
        }
        paramsBean.setFromType(fromType);
        paramsBean.setSourceUrl(sourceUrl);
        paramsBean.setCoverImage(coverImage);
        paramsBean.setSourceTitle(sourceTitle);
        paramsBean.setSourceViews(sourceViews);
        paramsBean.setSourceDuration(sourceDuration);
        paramsBean.setSourceItemId(sourceItemId);
        paramsBean.setSourceId(sourceId);
      //  Log.d(TAG,"paramsBean =" + new Gson().toJson(paramsBean));
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        copyBtn = findViewById(R.id.copyBtn);
        copyBtn.setOnClickListener(this);

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
            /*Glide.with(this).load(paramsBean.getCoverImage()).placeholder(R.mipmap.error)
                    .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                    .error(R.mipmap.error).into(currVideoImg);*/

            GlideNetWorkImageUtils.showNetworkImage(this,paramsBean.getCoverImage(),currVideoImg);
        }
        currVideoTitle.setText(paramsBean.getSourceTitle());
        currVideoViews.setText(paramsBean.getSourceViews()+"次观看");
      //  String temp = "https://app.rk5ck5dzx.com/api/index.m3u8?m=dUYzZjBsSWhiM2c2amxacU5qZDlVNHVsVWpoRFJIQWV4emxKcGh0WUFrZVFyeUJJZC8rVUVuZDFNWFdxK2E4eGlvNnpCVWxBbENXdW1TM3lmbUdHVVE9PQ==&t=1665459779&k=5730376599645c00b37984c43fb9affd";
       // dkPlayer.setUrl(temp);
        if(!StringUtils.isEmpty(paramsBean.getSourceUrl())){
            currentPlayUrl = paramsBean.getSourceUrl();
            dkPlayer.setUrl(paramsBean.getSourceUrl());
        }else{
            if(!StringUtils.isEmpty(paramsBean.getSourceId())){
                //动态获取
                HuangUtil.getUrl(paramsBean.getSourceId(), new HuangUtil.HuangListener() {
                    @Override
                    public void success(String path) {
                       // Log.d(TAG,"path"+path);
                        paramsBean.setSourceUrl(path);
                        dkPlayer.setUrl(path);
                    }
                    @Override
                    public void error(String msg) {

                    }
                });
            }
        }

        //必须设置
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(paramsBean.getSourceTitle(), false);
        dkPlayer.setVideoController(controller);


        categoryId = TextUtils.isEmpty(paramsBean.getSourceItemId())?  "" : paramsBean.getSourceItemId();
        if(!StringUtils.isEmpty(categoryId)){
            setCourseVideoList(categoryId,pageIndex);
        }else if(!StringUtils.isEmpty(searchVal)){
            setDataList();
        }else{
            keywordSearch();
        }
        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if(totalPage > pageIndex ){
                if(loadFlag){
                    if(!StringUtils.isEmpty(categoryId)){
                        pageIndex = pageIndex+1;
                        setCourseVideoList(categoryId,pageIndex);
                        refreshlayout.finishLoadMore(5000);
                    }else if(!StringUtils.isEmpty(searchVal)){
                        pageIndex = pageIndex+1;
                        setDataList();
                        refreshlayout.finishLoadMore(5000);
                    }
                }else{
                    refreshlayout.finishLoadMore(5000);
                }
            }else{
                refreshlayout.finishLoadMore(2000,true,true);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        classAdapter = new HuangVideoDetailDataAdapter(this,videoInfoList);
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
                if(!StringUtils.isEmpty(currentPlayUrl)){
                    clipboardContent(currentPlayUrl);
                }else{
                    Toast.makeText(this, "地址为空", Toast.LENGTH_SHORT).show();
                }
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
                VideoInfoBean videoInfoBean =  videoInfoList.get(position);
                if(StringUtils.isEmpty(videoInfoBean.getLink())){
                    HuangUtil.getUrl(videoInfoBean.getVideoId(), new HuangUtil.HuangListener() {
                        @Override
                        public void success(String path) {
                            playVideo(videoInfoBean,path,position);
                        }
                        @Override
                        public void error(String msg) {
                            Toast.makeText(App.getContext(), "播放地址出错啦", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    playVideo(videoInfoBean,videoInfoBean.getLink(),position);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void playVideo(VideoInfoBean videoInfoBean,String url,int position){
        try{
            //if(position>=0 && null != videoInfoList && videoInfoList.size()-1>= position){
             //   VideoInfoBean videoInfoBean =  videoInfoList.get(position);
                if(!StringUtils.isEmpty(videoInfoBean.getCoverImage())){
                    if(!this.isDestroyed()){
                       /* Glide.with(this).load(videoInfoBean.getCoverImage()).placeholder(R.mipmap.error)
                                .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                                .error(R.mipmap.error).into(currVideoImg);*/
                        GlideNetWorkImageUtils.showNetworkImage(this,videoInfoBean.getCoverImage(),currVideoImg);
                    }
                }
                currVideoTitle.setText(videoInfoBean.getTitle());
                currVideoViews.setText(videoInfoBean.getUpVote());
                // currVideoDuration.setText(videoInfoBean.getDuration());
                dkPlayer.release();
                //  Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
                currentPlayUrl = url;
                dkPlayer.setUrl(url);
                currVideoImg.setVisibility(View.VISIBLE);
                videoContainer.setVisibility(View.VISIBLE);
                icPlayBtn.setVisibility(View.VISIBLE);
                currentPosition = position;
                StandardVideoController controller = new StandardVideoController(this);
                controller.addDefaultControlComponent(videoInfoBean.getTitle(), false);
                dkPlayer.setVideoController(controller);
                //  playFlagMap.put("video_"+currentPosition,"1");
              //  dkPlayer.start();
          //  }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCourseVideoList(String categoryId,int page) {
        try{
            loadFlag = false;
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                String catTitle = "";
                // String id = "15";
                // String page = "1";
                String apiUrl = "https://"+ HuangConstant.getReqDomain()+"/api/home/recommend_list?uid=2&page=%s&id=%s&d_device=h5&v_code=252";
                apiUrl = String.format(apiUrl, page,categoryId);
                //apiUrl = "https://app.rk5ck5dzx.com/api/visualization/get&uid=98833266&tab_id=12&d_device=h5&v_code=252";
                try{
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).post().text();
                    String string = new String(Base64.decodeBase64(document));
                    ResultVideoBean result = new Gson().fromJson(string,new TypeToken<ResultVideoBean>(){}.getType());
                    if(null !=result && null != result.getData() && null != result.getData().getList()) {
                        topTitle = result.getData().getName();
                        total = Integer.valueOf(result.getData().getTotal());
                        for(ResultVideoItemInfoBean data : result.getData().getList()) {
                            if(null != data && null != data.getMedia_url() && data.getMedia_url().size() > 0) {
                                VideoInfoBean info =  new VideoInfoBean();
                                info.setVideoId(data.getId());
                                info.setTitle(data.getName());
                                info.setLink(data.getMedia_url().get(0).get("src"));
                                info.setAuthor(data.getTime_len());
                                info.setUpVote(data.getPlays());
                                info.setCoverImage(data.getImg3());
                                info.setCategoryId(categoryId);
                                dataListTemp.add(info);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(dataListTemp.size() > 0){
                    runOnUiThread(()->{
                        if(page == 1){
                            totalPage = total/pageSize + (total%pageSize > 0 ? 1 : 0);
                        }
                        loadFlag = true;
                        if(page == 1){
                            txt_show_set_back.setText(topTitle);
                            videoInfoList = dataListTemp;
                            classAdapter.updateData(dataListTemp);
                        }else{
                            videoInfoList.addAll(dataListTemp);
                            classAdapter.addData(dataListTemp);
                        }
                    });
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void keywordSearch() {
        try{
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                String apiUrl = "https://"+HuangConstant.getReqDomain()+"/api/video/keyword?uid=2&d_device=h5&v_code=252";
                try{
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).post().text();
                    String string = new String(Base64.decodeBase64(document));
                    ResultKeywordBean result = new Gson().fromJson(string,new TypeToken<ResultKeywordBean>(){}.getType());
                    if(null != result && null != result.getData() && null != result.getData().getVideo() && result.getData().getVideo().size() >0) {
                        for(ResultKeywordItemInfoBean data : result.getData().getVideo()) {
                            if(null != data ) {
                                VideoInfoBean info =  new VideoInfoBean();
                                info.setVideoId(data.getId());
                                info.setTitle(data.getName());
                                if( null != data.getMedia_url() && data.getMedia_url().size() > 0) {
                                    info.setLink(data.getMedia_url().get(0).get("src"));
                                }
                                info.setAuthor(data.getTime_len());
                                info.setUpVote(data.getPlays());
                                if(!StringUtils.isEmpty(data.getImg3())) {
                                    info.setCoverImage(data.getImg3());
                                }else if(!StringUtils.isEmpty(data.getImage())) {
                                    info.setCoverImage(data.getImage());
                                }
                                dataListTemp.add(info);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(()->{
                    if(null != dataListTemp && dataListTemp.size() > 0){
                        loadFlag = true;
                        videoInfoList = dataListTemp;
                        classAdapter.updateData(dataListTemp);
                    }
                });

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDataList() {
        try{
            loadFlag = false;
          //  Log.d(TAG,"返回 -searchVal :" + searchVal);
          //  Log.d(TAG,"返回 -page :" + pageIndex);
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                //   String apiUrl = "https://app.rk5ck5dzx.com/api/home/recommend_list?uid=2&page=%s&id=%s&d_device=h5&v_code=252";
                String apiUrl = "https://"+HuangConstant.getReqDomain()+"/api/video/video_search?uid=2&page=%s&keyword=%s&d_device=h5&v_code=252";
                apiUrl = String.format(apiUrl,pageIndex,searchVal);
                //apiUrl = "https://app.rk5ck5dzx.com/api/visualization/get&uid=98833266&tab_id=12&d_device=h5&v_code=252";
                try{
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).post().text();
                    String string = new String(Base64.decodeBase64(document));
                    ResultVideoBean result = new Gson().fromJson(string,new TypeToken<ResultVideoBean>(){}.getType());
                    if(null !=result && null != result.getData() && null != result.getData().getList()) {
                        // topTitle = result.getData().getName();
                        total = Integer.valueOf(result.getData().getTotal());
                        for(ResultVideoItemInfoBean data : result.getData().getList()) {
                            if(null != data ) {
                                VideoInfoBean info =  new VideoInfoBean();
                                info.setVideoId(data.getId());
                                info.setTitle(data.getName());
                                if(!StringUtils.isEmpty(data.getImg3())) {
                                    info.setCoverImage(data.getImg3());
                                }else if(!StringUtils.isEmpty(data.getImage())) {
                                    info.setCoverImage(data.getImage());
                                }
                                if( null != data.getMedia_url() && data.getMedia_url().size() > 0) {
                                    info.setLink(data.getMedia_url().get(0).get("src"));
                                }
                                info.setAuthor(data.getTime_len());
                                info.setUpVote(data.getPlays());
                                dataListTemp.add(info);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(()->{
                    if(null != dataListTemp && dataListTemp.size() > 0){
                        loadFlag = true;
                        if(pageIndex == 1){
                            totalPage = total/pageSize + (total%pageSize > 0 ? 1 : 0);
                            Log.d(TAG,"返回 -total :" + total);
                            Log.d(TAG,"返回 -totalPage :" + totalPage);
                        }
                        if(pageIndex == 1){
                            videoInfoList = dataListTemp;
                            classAdapter.updateData(dataListTemp);
                        }else{
                            videoInfoList.addAll(dataListTemp);
                            classAdapter.addData(dataListTemp);
                        }
                    }
                });
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
