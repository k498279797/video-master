package com.vxiaokang.video.activity.music;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.common.UserAgentUtils;
import com.vxiaokang.video.adapter.MusicListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.fragment.Utlis.MusicUtil;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;
import com.vxiaokang.video.view.SongStandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

/****
 * 音乐查询
 */
public class MusicResultListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MusicResultListActivity";
    private PromptDialog promptDialog;
    private ImageView ivBack;
    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private MusicListDataAdapter classAdapter;

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;

    private String categoryId = "tag-254-%s.htm";

    private int pageIndex = 1;
    private boolean loadFlag = true;
    private String domain = "https://www.hifini.com/";
    private int currentPosition = -1;
    private EditText search_edit;
    private ImageView search_button;
    private int type = 0; // 0按字 1 按id
    private String tempTtile = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_result_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }

    private void initView() {
        promptDialog = new PromptDialog(this);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        search_edit = findViewById(R.id.search_edit);
        search_edit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyBoardUtils.closeSoftKeyboard(v);
                String query =  v.getText().toString();
                if(!EmptyUtils.isEmpty(query) && !StringUtils.equals(query,tempTtile)){
                    tempTtile = query.trim();
                    pageIndex = 1;
                    type = 0;
                    String keywords = URLEncoder.encode(tempTtile);
                   // System.out.println(keywords);
                    keywords = keywords.replace("%", "_");
                    categoryId = "search-"+keywords+"-%s.htm";
                    setDataList();
                }
                return true;
            }
            return false;
        });
        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(this);

        String title =  getIntent().getStringExtra("title");
        tempTtile = title;
        search_edit.setText(title);
        String dataId =  getIntent().getStringExtra("dataId");
        if(!StringUtils.isEmpty(dataId)){
            type = 1;
            categoryId = "tag-"+dataId+"-%s.htm";
        }else{
            type = 0;
            String keywords = URLEncoder.encode(title);
            System.out.println(keywords);
            keywords = keywords.replace("%", "_");
            categoryId = "search-"+keywords+"-%s.htm";
        }

        dkPlayer = findViewById(R.id.dk_player);
        videoContainer = findViewById(R.id.video_detail_container);
        currVideoImg = findViewById(R.id.curr_video_img);
        icPlayBtn = findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);
        currVideoTitle = findViewById(R.id.curr_video_title);
        String img = "https://y.gtimg.cn/music/photo_new/T002R300x300M000002bvLan3nRMoQ.jpg";
       // Glide.with(this).load(img).into(currVideoImg);

        currVideoTitle.setText(title);
        dkPlayer.setUrl("https://ws.stream.qqmusic.qq.com/C400003Z6QPF4elpHi.m4a?guid=920781667&vkey=55DC09D2CC808408B09789285E473216E57742688316CEB94BD1DC3E441D17653DDDE165F2DE211B25DBF4AF62EC18902032BCA488FD8E77&uin=626567678&fromtag=103032");
        //必须设置
        dkPlayer.setEnableAudioFocus(true);
        SongStandardVideoController controller = new SongStandardVideoController(this);
        controller.addDefaultControlComponent(title, false);
        controller.setOnFocusChangeListener((v, hasFocus) -> Log.d(TAG,"hasFocus"+hasFocus));
        controller.hide();
        dkPlayer.setVideoController(controller);
        classRecView = findViewById(R.id.class_rec_view);
        try{
            RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
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
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            classAdapter = new MusicListDataAdapter(this,videoInfoList);
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
        setDataList();
    }



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
            case R.id.search_button:
                String query =  search_edit.getText().toString();
                if(!EmptyUtils.isEmpty(query) && !StringUtils.equals(query,tempTtile)){
                    tempTtile = query.trim();
                    pageIndex = 1;
                    type = 0;
                    String keywords = URLEncoder.encode(tempTtile);
                    // System.out.println(keywords);
                    keywords = keywords.replace("%", "_");
                    categoryId = "search-"+keywords+"-%s.htm";
                    setDataList();
                }
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
                        runOnUiThread(() -> playVideo(videoInfoBean, path, position));
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
        Glide.with(this).load(R.mipmap.banner).into(currVideoImg);
        currVideoTitle.setText(videoInfoBean.getTitle());
        dkPlayer.release();
        // Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
        dkPlayer.setUrl(url);
        currVideoImg.setVisibility(View.GONE);
        videoContainer.setVisibility(View.GONE);
        icPlayBtn.setVisibility(View.GONE);
        SongStandardVideoController controller = new SongStandardVideoController(this);
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
                String apiUrl = apiUrl = domain+categoryId;;
                //if(type == 1){
                // https://www.hifini.com/tag-254-1.htm
                // https://www.hifini.com/search-%s-%s.htm
                apiUrl = String.format(apiUrl,pageIndex);
                Log.d(TAG," api url  = "+apiUrl);
                try{
                    Document document = Jsoup.connect(apiUrl)
                            //.header("Content-Type", "application/json;charset=UTF-8")
                            .userAgent(UserAgentUtils.getUserAgent())
                            .ignoreContentType(true)
                            .timeout(60000).get();
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
                           // System.out.println("-----");
                        }else {
                            dataListTemp.add(info);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(()->{
                   // initFlag = true;
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
                        Toast.makeText(this,"--未找到相关内容--",Toast.LENGTH_LONG).show();
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
