package com.vxiaokang.video.activity.video.meijugua;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.WebViewVideoPlayActivity;

import com.vxiaokang.video.activity.video.common.UserAgentUtils;
import com.vxiaokang.video.activity.video.meijugua.bean.MeijuguaConstant;
import com.vxiaokang.video.adapter.meijugua.MeijuguaVideoDetailAdapter;
import com.vxiaokang.video.adapter.meijugua.MeijuguaVideoDetailTabAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.IntentParamsBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.entity.PlayRecord;
import com.vxiaokang.video.entity.dao.DaoSession;
import com.vxiaokang.video.entity.dao.PlayRecordDao;
import com.vxiaokang.video.eventbus.MessageEvent;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.view.StandardVideoController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;


/****
 * 课程详情列表
 */
public class MeijuguaVideoDetailPlayActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MeijuguaVideoDetailPlayActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //标题

    private RelativeLayout videoContainer;
    private VideoView dkPlayer;
    private ImageView icPlayBtn;
    private TextView currVideoTitle;
    private ImageView currVideoImg;

    private RecyclerView classRecView;
    private MeijuguaVideoDetailAdapter classAdapter;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();
    private List<List<VideoInfoBean>> videoInfoAllList = new ArrayList<>();

    private RecyclerView tabRecView;
    private MeijuguaVideoDetailTabAdapter tabAdapter;

    private IntentParamsBean paramsBean = new IntentParamsBean();

    private int currentPosition = 0;
    private String currentPlayUrl = "";
    private String currentTitle = "";
    private LinearLayout copyBtn;
    private TextView item_copy_btn;
    private TextView item_open_btn;
    private boolean isFirst = true;
    private Long recordId = -1l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meijugua_video_play_detail);
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
            currentPosition = Integer.valueOf(sourcePosition);
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

        copyBtn = findViewById(R.id.copyBtn);
        // copyBtn.setOnClickListener(this);
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
        tabRecView = findViewById(R.id.tab_rec_view);
        if(!this.isDestroyed()) {
           // Glide.with(this).load(paramsBean.getCoverImage()).into(currVideoImg);

            GlideNetWorkImageUtils.showNetworkImage(this,paramsBean.getCoverImage(),currVideoImg);
        }
        currentTitle = (paramsBean.getSourceItemName()+paramsBean.getSourceTitle());
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

        //StaggeredGridLayoutManager gridLayoutManager2 = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL);
        //GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 5);


        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW); //主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP); //按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START); //交叉轴的起点对齐。

        tabAdapter = new MeijuguaVideoDetailTabAdapter(this,tabList,currentPosition);
        tabRecView.setLayoutManager(flexboxLayoutManager);
        tabRecView.setAdapter(tabAdapter);
        tabRecView.setFocusable(false);
        tabRecView.setNestedScrollingEnabled(false);
        tabAdapter.setOnItemClickListener(position -> {
            try{
                videoInfoList = videoInfoAllList.get(position);
                if(currentPosition > videoInfoList.size()){
                    classAdapter.updateData(videoInfoAllList.get(position));
                }else{
                    classAdapter.updateData(videoInfoAllList.get(position),currentPosition);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        classAdapter = new MeijuguaVideoDetailAdapter(this,videoInfoList);
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
                dkPlayer.setPlayerFactory(IjkPlayerFactory.create());
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
                MeijuguaUtil.getUrl(videoInfoBean.getLink(), new MeijuguaUtil.MeijuguaListener() {
                        @Override
                        public void success(String path) {
                            Log.d(TAG,"MeijuguaUtil.getUrl --"+path);
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
                saveHistory(videoInfoBean,position);
          //  }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setDataList(String apiUrl) {
        try{
            new Thread(() -> {
                List<List<VideoInfoBean>> dataListAllTemp = new ArrayList<>();
                List<String> tabListTemp = new ArrayList<>();
                String bodyUrl = "";
                try{
                    Document document = Jsoup.connect(apiUrl)
                            .userAgent(UserAgentUtils.getUserAgent())
                            .ignoreContentType(true).timeout(60000)
                            .get();
                    if(null == document){
                        return;
                    }
                    Elements tabs = document.select("div.dropdown-menu a");
                    for(Element itElement : tabs) {
                        // System.out.print(itElement.text()+"  ");
                        tabListTemp.add(itElement.text());
                    }
                 //   Elements elements = document.select("ul.stui-content__playlist");
                    Elements elements = document.select("div.playurl-dropdown>ul");
                    for(Element itElement : elements) {
                        List<VideoInfoBean> dataListTemp = new ArrayList<>();
                        Elements aElements = itElement.select("li a");
                        for(Element info : aElements) {
                            VideoInfoBean infoBean = new VideoInfoBean();
                           // infoBean.setCoverImage(img);
                            infoBean.setLink(MeijuguaConstant.getReqDomain()+info.attr("href"));
                            infoBean.setTitle(info.text());
                            //  System.out.println(info.attr("href"));
                            // System.out.println(info.text());
                            dataListTemp.add(infoBean);
                        }
                        dataListAllTemp.add(dataListTemp);
                    }

                    String body = document.toString();
                    String one = "cms_player = ";
                    if(body.indexOf(one) != -1) {
                        body = body.substring(body.indexOf(one) + one.length());
                        body = body.substring(0, body.indexOf(";</script>"));
                        JSONObject json = new JSONObject(body);
                        body = json.getString("url");
                        bodyUrl = body;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                String finalBodyUrl = bodyUrl;
                runOnUiThread(()->{
                    if(dataListAllTemp.size() > 0){
                        videoInfoAllList = dataListAllTemp;
                        videoInfoList = dataListAllTemp.get(0);
                        classAdapter.updateData(videoInfoList);
                        dkPlayer.setUrl(finalBodyUrl);
                        //dkPlayer.start();
                        tabList = tabListTemp;
                        tabAdapter.updateData(tabList);
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

    private void saveHistory(VideoInfoBean info,int position){
        try{
            Log.d(TAG,"保存生成记录..."+info.getLink());
            String videoKey = info.getLink();
            PlayRecord historyRecord = null;
            if(recordId > 0){
                historyRecord = findRecordById(recordId);
            }
            // https://www.80yy.la/play/208353-4-0.html
            if(null == historyRecord && !StringUtils.isEmpty(info.getLink()) && info.getLink().lastIndexOf("/")!= -1){
                String  videoKeyTemp = info.getLink().substring(info.getLink().lastIndexOf("/")+1);
                if(!StringUtils.isEmpty(videoKeyTemp)){
                    if(videoKeyTemp.contains("-")){
                        videoKeyTemp = videoKeyTemp.substring(0,videoKeyTemp.indexOf("-"));
                    }
                    videoKey = videoKeyTemp.replace(".html", "");
                    historyRecord = findRecord(videoKey);
                }
            }
            Log.d(TAG,"保存生成记录..."+videoKey);
            PlayRecord record = new PlayRecord();

            record.setTitle(getIntent().getStringExtra(Constants.sourceItemName));
            record.setKeywords(info.getTitle());
            record.setType(5l);
            record.setCreateDate(new Date());
            record.setUpdate(new Date());
            record.setCoverImage(paramsBean.getCoverImage());
            record.setLink(currentPlayUrl);
            record.setSourceLink(info.getLink());
            record.setContent(currentPlayUrl);
            record.setPosition(position);
            DaoSession daoSession = App.getDaoSession();
            if(null == daoSession){
                runOnUiThread(() -> {
                    Toast.makeText(MeijuguaVideoDetailPlayActivity.this,"历史记录：daoSession is null",Toast.LENGTH_LONG).show();
                });
            }
            if(null != historyRecord && null != historyRecord.getId()){
                recordId = historyRecord.getId();
                record.setId(historyRecord.getId());
                daoSession.getPlayRecordDao().update(record);
            }else{
                record.setVideoKey(StringUtils.isEmpty(videoKey) ? info.getLink() : videoKey );
                recordId = daoSession.getPlayRecordDao().insert(record);
            }
        }catch (Exception e){
              Log.e(TAG,"保存生成记录出错..."+e.getMessage());
            runOnUiThread(() -> {
                Toast.makeText(MeijuguaVideoDetailPlayActivity.this,"历史记录出错："+e.getMessage(),Toast.LENGTH_LONG).show();
            });
            e.printStackTrace();
        }
    }

    private PlayRecord findRecord(String videoKey){
        if(!StringUtils.isEmpty(videoKey)){
            DaoSession daoSession = App.getDaoSession();
            Query<PlayRecord> query = daoSession.getPlayRecordDao().queryBuilder().where(PlayRecordDao.Properties.VideoKey.eq(videoKey)).orderDesc(PlayRecordDao.Properties.CreateDate).build();
            if(null != query ){
                List<PlayRecord> records = query.list();
                if(!records.isEmpty()){
                    return records.get(0);
                }
            }
        }
        return  null;
    }

    private PlayRecord findRecordById(Long id){
        if(null != id && id  > 0){
            DaoSession daoSession = App.getDaoSession();
            PlayRecord query = daoSession.getPlayRecordDao().load(id);
            if(null != query ){
              return  query;
            }
        }
        return  null;
    }
}
