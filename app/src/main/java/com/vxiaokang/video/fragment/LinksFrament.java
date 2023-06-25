package com.vxiaokang.video.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.vxiaokang.video.adapter.LinksListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.view.StandardVideoController;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 课程
 */
public class LinksFrament extends Fragment implements View.OnClickListener{
    private String TAG = "ClassFrament";
    private PromptDialog promptDialog;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private LinksListDataAdapter classAdapter;


    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_links, null);
       // StatusBarUtil.transparencyBar(getActivity());
        initView(view);
        return view;
    }
    private void initView(View view) {
        promptDialog = new PromptDialog(getActivity());

        View mybar = view.findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,getContext());

        dkPlayer = view.findViewById(R.id.dk_player);
        videoContainer = view.findViewById(R.id.video_detail_container);
        currVideoImg = view.findViewById(R.id.curr_video_img);
        icPlayBtn = view.findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);
        currVideoTitle = view.findViewById(R.id.curr_video_title);
        if(!this.isDetached()){
            Glide.with(this).load(R.mipmap.banner).into(currVideoImg);
        }
        currVideoTitle.setText("王心凌-我会好好的");
        dkPlayer.setUrl("http://hc.yinyuetai.com/uploads/localVideos/common/8153012D1E870D4F4CAB88333BD9FE16.flv");
        //必须设置
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(getActivity());
        controller.addDefaultControlComponent("王心凌-我会好好的", false);
        dkPlayer.setVideoController(controller);



        classRecView = view.findViewById(R.id.class_rec_view);
        if(videoInfoList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/mv.json","utf-8");
           // Log.d(TAG,"JSON---"+json);
            if(!StringUtils.isEmpty(json)){
                videoInfoList = new Gson().fromJson(json,new TypeToken<List<VideoInfoBean>>(){}.getType());
            }
        }
        try{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            classAdapter = new LinksListDataAdapter(getActivity(),videoInfoList);
            classRecView.setLayoutManager(gridLayoutManager);
            classRecView.setAdapter(classAdapter);
            classRecView.setFocusable(false);
            classRecView.setNestedScrollingEnabled(false);

            classAdapter.setOnItemClickListener(position -> {
                if(position >= 0 && position < videoInfoList.size()){
                    updateVideoInfo(position);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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
                break;
            default:
                break;
        }
    }

    public void updateVideoInfo(int position){
        try{
            if(position>=0 && null != videoInfoList && videoInfoList.size()-1>= position){
                VideoInfoBean videoInfoBean =  videoInfoList.get(position);
               // Glide.with(this).load(videoInfoBean.getCoverImage()).into(currVideoImg);
                Glide.with(this).load(R.mipmap.banner).into(currVideoImg);
                currVideoTitle.setText(videoInfoBean.getAuthor()+"-"+videoInfoBean.getTitle());
                dkPlayer.release();
               // Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
                dkPlayer.setUrl(videoInfoBean.getLink());
                currVideoImg.setVisibility(View.GONE);
                videoContainer.setVisibility(View.GONE);
                icPlayBtn.setVisibility(View.GONE);
                StandardVideoController controller = new StandardVideoController(getActivity());
                controller.addDefaultControlComponent(videoInfoBean.getAuthor()+"-"+videoInfoBean.getTitle(), false);
                dkPlayer.setVideoController(controller);
                dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
                dkPlayer.start();
            }
        }catch (Exception e){
                e.printStackTrace();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        dkPlayer.release();
    }

}
