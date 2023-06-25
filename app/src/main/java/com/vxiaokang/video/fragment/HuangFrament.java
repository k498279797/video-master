package com.vxiaokang.video.fragment;

import android.content.Intent;
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
import com.vxiaokang.video.activity.video.huang.HuangVideoListActivity;
import com.vxiaokang.video.adapter.HuangListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.view.StandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 课程
 */
public class HuangFrament extends Fragment implements View.OnClickListener{
    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private HuangListDataAdapter classAdapter;


    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_huang, null);
       // StatusBarUtil.transparencyBar(getActivity());
     //   EventBus.getDefault().register(this);
        initView(view);
        return view;
    }
    private void initView(View view) {
        View mybar = view.findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,getContext());

        dkPlayer = view.findViewById(R.id.dk_player);
        videoContainer = view.findViewById(R.id.video_detail_container);
        currVideoImg = view.findViewById(R.id.curr_video_img);
        icPlayBtn = view.findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);
        currVideoTitle = view.findViewById(R.id.curr_video_title);

     //   Glide.with(this).load("https://i.xddzshop.com/20220904/d7f13b875517f6db0707cbfad704d47c.jps").into(currVideoImg);
        currVideoTitle.setText("约炮清纯良家少女-0:29:34");
        String url = "https://v4.cpwsgujranwala.com/20220902/6u2pA0bc/2000kb/hls/index.m3u8";
       // url = "https://acc.tonxzq.com/api/index.m3u8?m=UHp2Wm9RNmk1Y1ZoekNzSjhpNmdtUTQrRUkrVFVBVHUwZVRSWUd4eEp2ZWZHMWxDZ1dkWjBPb0RiR252MXEwT2pleEtnclVkZXdGQ0xJZ1ZDYVlBU0pTVUY2bnNZWHpSYzhTVHUyZlNKQTA9&t=1665479123&k=009d143675a1844a1b904567573fa9d8";
        dkPlayer.setUrl(url);

      //  Glide.with(this).load(R.mipmap.banner).into(currVideoImg);
        //必须设置
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(getActivity());
        controller.addDefaultControlComponent("约炮清纯良家少女", false);
        dkPlayer.setVideoController(controller);

        classRecView = view.findViewById(R.id.class_rec_view);
        if(videoInfoList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/huang.json","utf-8");
            if(!StringUtils.isEmpty(json)){
                VideoInfoBean info = null;
               List<VideoInfoBean> videoInfoListTemp = new Gson().fromJson(json,new TypeToken<List<VideoInfoBean>>(){}.getType());
                info = new VideoInfoBean();
                info.setAdType("1");
                info.setCategoryId(videoInfoListTemp.get(0).getCategoryId());
                String categoryId = videoInfoListTemp.get(0).getCategoryId();
                videoInfoList.add(info);
                for(VideoInfoBean temp : videoInfoListTemp){
                    if(StringUtils.equals(categoryId,temp.getCategoryId())){
                        videoInfoList.add(temp);
                    }else{
                        info = new VideoInfoBean();
                        info.setAdType("1");
                        info.setCategoryId(temp.getCategoryId());
                        categoryId = temp.getCategoryId();
                        videoInfoList.add(info);
                        videoInfoList.add(temp);
                    }
                }
            }
        }
        try{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            classAdapter = new HuangListDataAdapter(getActivity(),videoInfoList);
            classRecView.setLayoutManager(gridLayoutManager);
            classRecView.setAdapter(classAdapter);
            classRecView.setFocusable(false);
            classRecView.setNestedScrollingEnabled(false);

            classAdapter.setOnItemClickListener(position -> {
                if(position >= 0 && position < videoInfoList.size()){
                    String adType = videoInfoList.get(position).getAdType();
                    if(StringUtils.equals(adType,"1")){
                        Intent intent = new Intent(getActivity(), HuangVideoListActivity.class);
                        intent.putExtra("categoryId",videoInfoList.get(position).getCategoryId());
                        startActivity(intent);
                    }else{
                        updateVideoInfo(position);
                    }
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
                if(!StringUtils.isEmpty(videoInfoBean.getCoverImage())){
                    if(!this.isDetached()) {
                       // Glide.with(this).load(videoInfoBean.getCoverImage()).into(currVideoImg);
                        GlideNetWorkImageUtils.showNetworkImage(getActivity(),videoInfoBean.getCoverImage(),currVideoImg);
                    }
                }else{
                    if(!this.isDetached()){
                        Glide.with(this).load(R.mipmap.banner).into(currVideoImg);
                    }
                }
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
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        // Do something 变速逻辑操作
        dkPlayer.setSpeed(event.getSpeed());
    }

}
