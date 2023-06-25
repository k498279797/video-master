package com.vxiaokang.video.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.ChannelListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.video.ChannelBean;
import com.vxiaokang.video.view.StandardVideoController;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.LiveControlView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 课程
 */
public class TVFrament extends Fragment implements View.OnClickListener{
    private String TAG = "TVFrament";
    private PromptDialog promptDialog;

    private RecyclerView channelRecView;
    private List<ChannelBean> channelBeanList = new ArrayList<>();
    private ChannelListDataAdapter channelAdapter;
    private TextView page_title;

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
  //  private SuperPlayerView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;
    private int currentPosition = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tv, null);
        initView(view);
        return view;
    }
    private void initView(View view) {
        promptDialog = new PromptDialog(getActivity());
        if(channelBeanList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/tv2.json","utf-8");
            if(!StringUtils.isEmpty(json)){
                channelBeanList = new Gson().fromJson(json,new TypeToken<List<ChannelBean>>(){}.getType());
            }
        }
        View mybar = view.findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,getContext());
        page_title = view.findViewById(R.id.page_title);
        channelRecView = view.findViewById(R.id.tv_channel_rec_view);
        try{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            channelAdapter = new ChannelListDataAdapter(getActivity(),channelBeanList);
            channelRecView.setLayoutManager(gridLayoutManager);
            channelRecView.setAdapter(channelAdapter);
            channelRecView.setFocusable(false);
            channelRecView.setNestedScrollingEnabled(false);
            channelAdapter.setOnItemClickListener(position -> {
                try{
                    updateVideoInfo(position);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        dkPlayer = view.findViewById(R.id.dk_player);
        videoContainer = view.findViewById(R.id.video_detail_container);
        currVideoImg = view.findViewById(R.id.curr_video_img);
        icPlayBtn = view.findViewById(R.id.ic_play_btn);
        currVideoTitle = view.findViewById(R.id.curr_video_title);
        currVideoTitle.setText("浙江卫视");

        /*SuperPlayerModel model = new SuperPlayerModel();
        model.url = "http://39.134.115.163:8080/PLTV/88888910/224/3221225745/index.m3u8";
        //播放模式，可设置自动播放模式：PLAY_ACTION_AUTO_PLAY 0，手动播放模式：PLAY_ACTION_MANUAL_PLAY 1
        model.playAction = 1;
        model.title = "湖南卫视";
        dkPlayer.playWithModelNeedLicence(model);*/


        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);

        StandardVideoController controller = new StandardVideoController(getContext());
        TitleView titleView = new TitleView(getActivity());//标题栏
        controller.addControlComponent(titleView);
      //  controller.addDefaultControlComponent("湖南卫视", false);
        controller.addControlComponent(new LiveControlView(getActivity()));//直播控制条
        titleView.setTitle("湖南卫视");

        dkPlayer.setUrl("http://39.134.115.163:8080/PLTV/88888910/224/3221225745/index.m3u8");
        //必须设置
       dkPlayer.setEnableAudioFocus(false);
        dkPlayer.setVideoController(controller);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video_detail_container:
            case R.id.ic_play_btn:
                currVideoImg.setVisibility(View.GONE);
                videoContainer.setVisibility(View.GONE);
                icPlayBtn.setVisibility(View.GONE);
                dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
                dkPlayer.start();
               // dkPlayer.setIsAutoPlay(true);
                break;
            default:
                break;
        }
    }

    public void updateVideoInfo(int position){
        try{
            if(position>=0 && null != channelBeanList && position  < channelBeanList.size()){
                ChannelBean bean =  channelBeanList.get(position);

                dkPlayer.release();
                currVideoImg.setVisibility(View.VISIBLE);
                videoContainer.setVisibility(View.VISIBLE);
                icPlayBtn.setVisibility(View.VISIBLE);
                currentPosition = position;
                page_title.setText(bean.getName());
                currVideoTitle.setText(bean.getName());
                StandardVideoController controller = new StandardVideoController(getContext());
                controller.addControlComponent(new LiveControlView(getActivity()));//直播控制条

                PrepareView prepareView = new PrepareView(getActivity());//准备播放界面
                prepareView.setClickStart();
                ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
                Glide.with(this).load(R.mipmap.hunan).into(thumb);
                controller.addControlComponent(prepareView);
                controller.addControlComponent(new CompleteView(getActivity()));//自动完成播放界面
                controller.addControlComponent(new ErrorView(getActivity()));//错误界面

                TitleView titleView = new TitleView(getActivity());//标题栏
                controller.addControlComponent(titleView);
                //  controller.addDefaultControlComponent("湖南卫视", false);
                titleView.setTitle(bean.getName());
                //controller.addDefaultControlComponent(bean.getName(), false);
                dkPlayer.setUrl(bean.getUrl());
                dkPlayer.setVideoController(controller);

               /* SuperPlayerModel model = new SuperPlayerModel();
                model.url = bean.getUrl();
                //播放模式，可设置自动播放模式：PLAY_ACTION_AUTO_PLAY 0，手动播放模式：PLAY_ACTION_MANUAL_PLAY 1
                model.playAction = 1;
                model.title = bean.getName();
                dkPlayer.setIsAutoPlay(false);
                dkPlayer.playWithModelNeedLicence(model);*/
                //dkPlayer.start();
            }
        }catch (Exception e){

        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(!menuVisible){
            if(null != dkPlayer){
                dkPlayer.pause();
                //dkPlayer.onPause();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dkPlayer.pause();
       // dkPlayer.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        dkPlayer.pause();
       // dkPlayer.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dkPlayer.release();
    }
}
