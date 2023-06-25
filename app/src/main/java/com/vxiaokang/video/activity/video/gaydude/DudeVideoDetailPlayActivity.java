package com.vxiaokang.video.activity.video.gaydude;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.WebViewVideoPlayActivity;
import com.vxiaokang.video.activity.video.common.UserAgentUtils;
import com.vxiaokang.video.activity.video.gaydude.bean.DudeConstant;
import com.vxiaokang.video.adapter.HuangVideoDetailDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.IntentParamsBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.view.StandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;


/****
 * 详情列表
 */
public class DudeVideoDetailPlayActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "GaytubeVideoDetailPlayActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //标题

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;

    private RecyclerView classRecView;
    private HuangVideoDetailDataAdapter classAdapter;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();

    private IntentParamsBean paramsBean = new IntentParamsBean();

    private String currentPlayUrl = "";
    private String currentTitle = "";

    private TextView item_copy_btn;
    private TextView item_open_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdy_video_play_detail);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        EventBus.getDefault().register(this);
        initParams();
        initView();
    }
    private void initParams(){
        String sourceLink = getIntent().getStringExtra(Constants.sourceLink);
        String sourceUrl = getIntent().getStringExtra(Constants.sourceUrl);
        String coverImage = getIntent().getStringExtra(Constants.coverImage);
        String sourceTitle = getIntent().getStringExtra(Constants.sourceTitle);
        String sourceItemName = getIntent().getStringExtra(Constants.sourceItemName);
        String sourcePosition = getIntent().getStringExtra(Constants.sourcePosition);
        paramsBean.setSourceUrl(sourceUrl);
        paramsBean.setCoverImage(coverImage);
        paramsBean.setSourceTitle(sourceTitle);
        paramsBean.setSourceItemName(sourceItemName);
        if(!StringUtils.isEmpty(sourcePosition)){
           // currentPosition = Integer.valueOf(sourcePosition);
            paramsBean.setSourcePosition(sourcePosition);
        }
        if(!StringUtils.isEmpty(sourceLink)){
            paramsBean.setSourceLink(sourceLink);
        }
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        item_copy_btn = findViewById(R.id.item_copy_btn);
        item_copy_btn.setOnClickListener(this);
        item_open_btn = findViewById(R.id.item_open_btn);
        item_open_btn.setOnClickListener(this);

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        if(!StringUtils.isEmpty(paramsBean.getSourceItemName())){
            txt_show_set_back.setText(paramsBean.getSourceItemName());
        }
        dkPlayer = findViewById(R.id.dk_player);
        videoContainer = findViewById(R.id.video_detail_container);
        currVideoImg = findViewById(R.id.curr_video_img);
        icPlayBtn = findViewById(R.id.ic_play_btn);
        videoContainer.setOnClickListener(this);
        icPlayBtn.setOnClickListener(this);

        currVideoTitle = findViewById(R.id.curr_video_title);
        classRecView = findViewById(R.id.detail_rec_view);
        if(!this.isDestroyed()) {
            //Glide.with(this).load(paramsBean.getCoverImage()).into(currVideoImg);
            GlideNetWorkImageUtils.showNetworkImage(this,paramsBean.getCoverImage(),currVideoImg);
        }
        String keywords = getIntent().getStringExtra("keywords");
        if(StringUtils.isEmpty(keywords)){
            keywords = "";
        }
        currentTitle = (paramsBean.getSourceTitle()+keywords);
        currVideoTitle.setText(currentTitle);

        //必须设置
        dkPlayer.setEnableAudioFocus(false);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(paramsBean.getSourceTitle(), false);
        dkPlayer.setVideoController(controller);
        if(!StringUtils.isEmpty(paramsBean.getSourceLink())){
            dkPlayer.setUrl(paramsBean.getSourceLink());
            //dkPlayer.start();
        }

        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
                refreshlayout.finishLoadMore(2000,true,true);
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


        setDataList(paramsBean.getSourceUrl());
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
              //  dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
                dkPlayer.setPlayerFactory(AndroidMediaPlayerFactory.create());
                dkPlayer.start();
                break;
            case R.id.item_copy_btn:
                if(!StringUtils.isEmpty(currentPlayUrl)){
                    clipboardContent(currentPlayUrl);
                }else{
                    Toast.makeText(this, "地址为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_open_btn:
                Intent intent = new Intent(this, WebViewVideoPlayActivity.class);
                intent.putExtra("url", currentPlayUrl);
                intent.putExtra("title", currentTitle);
                startActivity(intent);
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
                try{
                    currVideoImg.setVisibility(View.VISIBLE);
                    videoContainer.setVisibility(View.VISIBLE);
                    icPlayBtn.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
               // if(StringUtils.isEmpty(videoInfoBean.getLink())){
                DudeUtil.getUrl(videoInfoBean.getLink(), new DudeUtil.DudeListener() {
                        @Override
                        public void success(String path) {
                            Log.d(TAG,"GaytubeUtil.getUrl --"+path);
                            playVideo(videoInfoBean,path,position);
                        }
                        @Override
                        public void error(String msg) {
                            Looper.prepare();
                            Toast.makeText(App.getContext(), "播放地址出错啦", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
               // }else{
               //     playVideo(videoInfoBean,videoInfoBean.getLink(),position);
               // }
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
                    if(!this.isDestroyed()) {
                       /* Glide.with(this).load(videoInfoBean.getCoverImage()).placeholder(R.mipmap.error)
                                .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                                .error(R.mipmap.error).into(currVideoImg);*/

                        GlideNetWorkImageUtils.showNetworkImage(this,videoInfoBean.getCoverImage(),currVideoImg);
                    }
                }
            currentTitle = (StringUtils.isEmpty(paramsBean.getSourceItemName()) ? "" :paramsBean.getSourceItemName())+videoInfoBean.getTitle();
            currVideoTitle.setText(currentTitle);
                // currVideoDuration.setText(videoInfoBean.getDuration());
                dkPlayer.release();
                //  Log.d(TAG,"dkPlayer url = "+videoInfoBean.getLink());
                currentPlayUrl = url;
                dkPlayer.setUrl(url);
                StandardVideoController controller = new StandardVideoController(this);
                controller.addDefaultControlComponent(videoInfoBean.getTitle(), false);
                dkPlayer.setVideoController(controller);
                //  playFlagMap.put("video_"+currentPosition,"1");
                //dkPlayer.start();
               // saveHistory(videoInfoBean,position);
          //  }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setDataList(String apiUrl) {
        try{
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                String bodyUrl = "";
                try{
                    Document document = Jsoup.connect(apiUrl)
                            .userAgent(UserAgentUtils.getUserAgent())
                            .ignoreContentType(true).timeout(60000)
                            .get();
                    if(null == document){
                        return;
                    }
                    Elements elements = document.select(".thumbs__item");
                    for(Element element: elements) {
                        VideoInfoBean info =  new VideoInfoBean();
                        Element video = element.select("a").first();
                        String link = video.attr("href");

                        Element imgElement = element.select(".thumb__screen img").first();
                        String img = imgElement.attr("src");
                        String name = imgElement.attr("alt");
                        if(StringUtils.isEmpty(img)){
                            img = imgElement.attr("data-src");
                        }
                        String duration = element.select(".thumb__details span").first().text();
                        info.setTitle(name);
                        info.setLink(DudeConstant.getReqDomain()+link);
                        info.setAuthor(duration);
                        info.setKeywords(duration);
                        if(!StringUtils.isEmpty(img)){
                            if(img.startsWith("http")){
                                info.setCoverImage(img);
                            }else{
                                info.setCoverImage(DudeConstant.getReqDomain()+img);
                            }
                        }
                        if(!StringUtils.isEmpty(link)) {
                            dataListTemp.add(info);
                        }
                    }

                    String body = document.toString();
                    String one = "<script type=\"application/ld+json\">";
                    if(body.indexOf(one)!= -1) {
                        body = body.substring(body.indexOf(one)+one.length());
                        body = body.substring(0, body.indexOf("</script>"));
                        body = body.trim();
                    }
                    String two = "\"contentUrl\": \"";
                    if(body.indexOf(two) != -1) {
                        String two_body = body.substring(body.indexOf(two)+two.length());
                        two_body = two_body.substring(0, two_body.indexOf("\","));
                        two_body = two_body.trim();
                        if(!StringUtils.isEmpty(two_body)) {
                            bodyUrl = two_body;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                String finalBodyUrl = bodyUrl;
                runOnUiThread(()->{
                    if(dataListTemp.size() > 0){
                        currentPlayUrl = finalBodyUrl;
                        videoInfoList = dataListTemp;
                        classAdapter.updateData(videoInfoList);
                        dkPlayer.setUrl(finalBodyUrl);
                        //dkPlayer.start();
                       // dkPlayer.setUrl(finalBodyUrl);
                       // updateUI(itemTemp);
                    }else{
                        Log.d(TAG,"获取数据失败");
                    }
                });
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
