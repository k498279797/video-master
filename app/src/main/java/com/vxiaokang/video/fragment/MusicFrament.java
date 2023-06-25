package com.vxiaokang.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.music.MusicSearchListActivity;
import com.vxiaokang.video.activity.video.common.UserAgentUtils;
import com.vxiaokang.video.activity.video.huang.HuangVideoDetailActivity;
import com.vxiaokang.video.adapter.MusicListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.fragment.Utlis.MusicUtil;
import com.vxiaokang.video.view.SongStandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 课程
 */
public class MusicFrament extends Fragment implements View.OnClickListener{
    private String TAG = "MusicFrament";
    private PromptDialog promptDialog;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private MusicListDataAdapter classAdapter;


    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;

    private Spinner tabSp;
    private String categoryId = "index-%s.htm";
    private boolean initFlag = false;
    private int pageIndex = 1;
    private boolean loadFlag = true;
    private String domain = "https://www.hifini.com/";
    private int currentPosition = -1;
    private TextView search_edit;
    private ImageView search_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_music, null);
       // StatusBarUtil.transparencyBar(getActivity());
        Log.d(TAG,"onCreateView");
        initView(view);
        return view;
    }
    private void initView(View view) {
        promptDialog = new PromptDialog(getActivity());

        View mybar = view.findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,getContext());
        initSpinnerTab(view);

        search_edit = view.findViewById(R.id.search_edit);
        search_edit.setOnClickListener(this);
        search_button = view.findViewById(R.id.search_button);
        search_button.setOnClickListener(this);

        dkPlayer = view.findViewById(R.id.dk_player);
        videoContainer = view.findViewById(R.id.video_detail_container);
        currVideoImg = view.findViewById(R.id.curr_video_img);
        icPlayBtn = view.findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);
        currVideoTitle = view.findViewById(R.id.curr_video_title);
        String img = "https://y.gtimg.cn/music/photo_new/T002R300x300M000002bvLan3nRMoQ.jpg";
       /* if(!this.isDetached()){
            Glide.with(this).load(img).into(currVideoImg);
        }*/
        currVideoTitle.setText("张杰《听》");
        dkPlayer.setUrl("https://ws.stream.qqmusic.qq.com/C400003Z6QPF4elpHi.m4a?guid=920781667&vkey=55DC09D2CC808408B09789285E473216E57742688316CEB94BD1DC3E441D17653DDDE165F2DE211B25DBF4AF62EC18902032BCA488FD8E77&uin=626567678&fromtag=103032");
        //必须设置
        dkPlayer.setEnableAudioFocus(true);
        SongStandardVideoController controller = new SongStandardVideoController(getActivity());
        controller.addDefaultControlComponent("张杰《听》", false);
        controller.setOnFocusChangeListener((v, hasFocus) -> Log.d(TAG,"hasFocus"+hasFocus));
        dkPlayer.setVideoController(controller);
        classRecView = view.findViewById(R.id.class_rec_view);
        try{
            RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
            refreshLayout.setEnableRefresh(false);
            refreshLayout.setOnLoadMoreListener(refreshlayout -> {
                // refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                if(loadFlag){
                    pageIndex = pageIndex+1;
                    setDataList();
                    refreshlayout.finishLoadMore(5000);
                }else{
                    refreshlayout.finishLoadMore(2000,true,true);
                }
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            classAdapter = new MusicListDataAdapter(getActivity(),videoInfoList);
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

    private void initSpinnerTab(View view){
        String[] tabName = new String[]{"首页","华语","日韩","欧美","Remix","纯音乐","异次元","特供"};
        String[] tabId = new String[]{"index-%s.htm","forum-1-%s.htm","forum-15-%s.htm","forum-10-%s.htm","forum-11-%s.htm","forum-12-%s.htm","forum-13-%s.htm","forum-17-%s.htm"};
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        tabSp = view.findViewById(R.id.search_tab);
        //设置下拉框的标题，不设置就没有难看的标题了
        tabSp.setPrompt("类型");
        //设置下拉框的数组适配器
        tabSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        tabSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        tabSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = tabId[position]+"";
                if(initFlag){
                    pageIndex = 1;
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setDataList();
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
            case R.id.search_edit:
            case R.id.search_button:
                Intent intent = new Intent(getActivity(), MusicSearchListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void updateVideoInfo(int position){
        try{
            if(position>=0 && null != videoInfoList && videoInfoList.size()-1>= position){
                VideoInfoBean videoInfoBean =  videoInfoList.get(position);
                MusicUtil.getUrl(videoInfoBean.getLink(), new MusicUtil.MusicListener() {
                    @Override
                    public void success(String path) {
                        getActivity().runOnUiThread(() -> playVideo(videoInfoBean, path, position));
                    }
                    @Override
                    public void error(String msg) {
                        currentPosition = position;
                        Looper.prepare();
                        Toast.makeText(App.getContext(), "播放地址出错啦", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    public void playVideo(VideoInfoBean videoInfoBean,String url,int position){
      //  VideoInfoBean videoInfoBean =  videoInfoList.get(position);
        // Glide.with(this).load(videoInfoBean.getCoverImage()).into(currVideoImg);
            currentPosition = position;
            Glide.with(getActivity()).load(R.mipmap.banner).into(currVideoImg);
            currVideoTitle.setText(videoInfoBean.getTitle());
            dkPlayer.release();
            // Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
            dkPlayer.setUrl(url);
            currVideoImg.setVisibility(View.GONE);
            videoContainer.setVisibility(View.GONE);
            icPlayBtn.setVisibility(View.GONE);
            SongStandardVideoController controller = new SongStandardVideoController(getActivity());
            controller.addDefaultControlComponent(videoInfoBean.getTitle(), false);
            dkPlayer.setVideoController(controller);
            dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
            dkPlayer.start();
            classAdapter.updatePlay(currentPosition);

    }

    private void setDataList() {
        try{
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                String apiUrl = domain+categoryId;
                apiUrl = String.format(apiUrl,pageIndex);
                Log.d(TAG," api url  = "+apiUrl);
                try{
                    Document document = Jsoup.connect(apiUrl)
                            .userAgent(UserAgentUtils.getUserAgent())
                            .ignoreContentType(true).timeout(60000).get();
                    if(null == document){
                        return;
                    }
                  //  Log.d(TAG,"dd="+document);
                    Elements elements = document.select(".threadlist li");
                    for(Element element: elements) {
                        VideoInfoBean info =  new VideoInfoBean();

                        String link = element.attr("data-href");
                        String dataId = element.attr("data-tid");
                        String img = element.select("img").first().attr("src");
                        String title = element.select(".subject").text();
                        Elements top = element.select(".subject .icon-top-3");

                        String author = element.select("span.username").text();
                        String time  = element.select("span.time").text();
                        if(StringUtils.isEmpty(time)){
                            time  = element.select("span.date").text();
                        }
                        info.setTitle(title);
                        info.setLink(domain+link);
                        info.setAuthor(author);
                        info.setKeywords(time);
                        info.setVideoId(dataId);
                        if(!StringUtils.isEmpty(img)){
                            if(img.startsWith("http")){
                                info.setCoverImage(img);
                            }else{
                                info.setCoverImage(domain+img);
                            }
                        }
                        info.setCategoryId(categoryId);
                        if(null != top && top.size() > 0) {
                            System.out.println("-----");
                        }else {
                            dataListTemp.add(info);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(()->{
                    initFlag = true;
                    if(dataListTemp.size() > 0){
                        loadFlag = true;
                        if(pageIndex == 1){
                            videoInfoList = dataListTemp;
                            classAdapter.updateData(dataListTemp);
                        }else{
                            videoInfoList.addAll(dataListTemp);
                            classAdapter.addData(dataListTemp);
                        }
                    }else{
                        // Log.d(TAG,"--未找到相关内容--");
                        Toast.makeText(getActivity(),"--未找到相关内容--",Toast.LENGTH_LONG).show();
                    }
                });

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onPause() {
        Log.d(TAG,"onPause");
        super.onPause();
        //dkPlayer.pause();
    }


    @Override
    public void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
        //dkPlayer.pause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        // Do something 变速逻辑操作
        //0 上一曲 1 下一曲
        int payFlag =  event.getPayFlag();
       // Log.d(TAG,"payFlag+"+payFlag);
        if(payFlag == 0){
            int temp = currentPosition-1;
            if(temp <= 0){
                temp = 0;
            }
            if(temp >= 0 && temp < videoInfoList.size()){
                updateVideoInfo(temp);
            }
        }else if(payFlag == 1){
            int temp = currentPosition+1;
            if(temp <= 0){
                temp = 0;
            }
            if(temp >= 0 && temp < videoInfoList.size()){
                updateVideoInfo(temp);
            }
        }else if(payFlag == 2){
            int temp = currentPosition+1;
            if(temp <= 0){
                temp = 0;
            }
            if(temp >= 0 && temp < videoInfoList.size()){
                updateVideoInfo(temp);
            }
        }

        //dkPlayer.setSpeed(event.getSpeed());
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        if (EventBus.getDefault().isRegistered(this)){//加上判断
            EventBus.getDefault().unregister(this);
         }
        dkPlayer.release();
    }

}
