package com.vxiaokang.video.activity.video.pkmp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.common.UserAgentUtils;
import com.vxiaokang.video.activity.video.pkmp.bean.PkmpConstant;
import com.vxiaokang.video.activity.video.shipin.ShipinVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.shipin.bean.ShipinConstant;
import com.vxiaokang.video.adapter.PkmpVideoDetailAdapter;
import com.vxiaokang.video.adapter.PkmpVideoDetailTabAdapter;
import com.vxiaokang.video.adapter.ShipinVideoDetailAdapter;
import com.vxiaokang.video.adapter.ShipinVideoDetailTabAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;
import com.vxiaokang.video.util.commons.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/****
 * 详情页面
 */
public class PkmpVideoDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "PkmpVideoDetailActivity";
    private ImageView ivBack;

    private TextView txt_show_set_back; //标题

    private RecyclerView classRecView;
    private PkmpVideoDetailAdapter classAdapter;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private List<String> tabList = new ArrayList<>();
    private List<List<VideoInfoBean>> videoInfoAllList = new ArrayList<>();

    private RecyclerView tabRecView;
    private PkmpVideoDetailTabAdapter tabAdapter;

    private ImageView class_item_img;
    private TextView class_item_title;
    private TextView class_item_zz;
    private TextView class_item_bz;
    private TextView class_item_gk;
    private TextView class_item_dy;
    private TextView class_item_nd;
    private TextView class_item_gx;
    private TextView class_item_yy;
    private String currentTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipin_video_detail);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initUIText();
        initView();
    }

    private void initUIText(){
       class_item_img = findViewById(R.id.class_item_img);
       class_item_title = findViewById(R.id.class_item_title);
       class_item_zz = findViewById(R.id.class_item_zz);
       class_item_bz = findViewById(R.id.class_item_bz);
       class_item_gk = findViewById(R.id.class_item_gk);
       class_item_dy = findViewById(R.id.class_item_dy);
       class_item_nd = findViewById(R.id.class_item_nd);
       class_item_gx = findViewById(R.id.class_item_gx);
       class_item_yy = findViewById(R.id.class_item_yy);
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);


        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        classRecView = findViewById(R.id.detail_rec_view);
        tabRecView = findViewById(R.id.tab_rec_view);

        String url = getIntent().getStringExtra("url");
        String coverImage = getIntent().getStringExtra("coverImage");
        String title = getIntent().getStringExtra("title");
        if(!StringUtils.isEmpty(coverImage)){
            if(!this.isDestroyed()) {
               /* Glide.with(this).load(coverImage).placeholder(R.mipmap.error)
                        .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                        .error(R.mipmap.error).into(class_item_img);*/

                GlideNetWorkImageUtils.showNetworkImage(this,coverImage,class_item_img);
            }
        }
        if(!StringUtils.isEmpty(title)){
            class_item_title.setText(title);
        }
      //  String  title = getIntent().getStringExtra("title");
        setDataList(url);
        //GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 5);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW); //主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP); //按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START); //交叉轴的起点对齐。

        tabAdapter = new PkmpVideoDetailTabAdapter(this,tabList,0);
        tabRecView.setLayoutManager(flexboxLayoutManager);
        tabRecView.setAdapter(tabAdapter);
        tabRecView.setFocusable(false);
        tabRecView.setNestedScrollingEnabled(false);
        tabAdapter.setOnItemClickListener(position -> {
            try{
                videoInfoList = videoInfoAllList.get(position);
                classAdapter.updateData(videoInfoAllList.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        classAdapter = new PkmpVideoDetailAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                VideoInfoBean clickBean = videoInfoList.get(position);
                Intent intent = new Intent(this, PkmpVideoDetailPlayActivity.class);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceItemName, currentTitle);
                intent.putExtra("categoryId", "");
                startActivity(intent);
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
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }


    private void setDataList(String apiUrl) {
        try{
            new Thread(() -> {
                List<List<VideoInfoBean>> dataListAllTemp = new ArrayList<>();
                List<String> tabListTemp = new ArrayList<>();
                VideoInfoBean itemTemp = new VideoInfoBean();
                List<String> tagsTemp = new ArrayList<>();
                try{
                    Document document = Jsoup.connect(apiUrl)
                                        .userAgent(UserAgentUtils.getUserAgent())
                                        .ignoreContentType(true).timeout(60000)
                                        .get();
                    if(null == document){
                        return;
                    }

                    Elements metas = document.select("div.main-ui-meta div");
                    for(Element meta: metas) {
                        tagsTemp.add(meta.text());
                    }
                    String img = "";
                    String title = "";
                    Element imgEl =  document.select("div.img img").first();
                    if(null != imgEl) {
                        img = imgEl.attr("src");
                        title = imgEl.attr("alt");
                    }
                    itemTemp.setTitle(title);
                    itemTemp.setCoverImage(PkmpConstant.getReqDomain() +img);
                    itemTemp.setTags(tagsTemp);

                    Elements tabs = document.select("ul.py-tabs li");
                    for(Element itElement: tabs) {
                        tabListTemp.add(itElement.text());
                    }

                    Elements elements = document.select("ul.player");
                    for(Element itElement :  elements) {
                        List<VideoInfoBean> dataListTemp = new ArrayList<>();
                        Elements aElements = itElement.select("li a");
                        for(Element info : aElements) {
                           // System.out.println(info.attr("href"));
                           // System.out.println(info.text());
                            VideoInfoBean infoBean = new VideoInfoBean();
                            infoBean.setCoverImage(PkmpConstant.getReqDomain()+img);
                            infoBean.setLink(PkmpConstant.getReqDomain()+info.attr("href"));
                            infoBean.setTitle(info.text());
                            //  System.out.println(info.attr("href"));
                            // System.out.println(info.text());
                            dataListTemp.add(infoBean);
                        }
                        dataListAllTemp.add(dataListTemp);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(()->{
                    if(dataListAllTemp.size() > 0){
                        videoInfoAllList = dataListAllTemp;
                        videoInfoList = dataListAllTemp.get(0);
                        classAdapter.updateData(videoInfoList);
                        tabList = tabListTemp;
                        tabAdapter.updateData(tabList);
                        updateUI(itemTemp);
                    }else{
                        Log.d(TAG,"获取数据失败");
                    }
                });
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateUI(VideoInfoBean info){
        if(null != info){
            if(!this.isDestroyed()) {
              /*  Glide.with(this).load(info.getCoverImage()).placeholder(R.mipmap.error)
                        .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                        .error(R.mipmap.error).into(class_item_img);*/
                GlideNetWorkImageUtils.showNetworkImage(this,info.getCoverImage(),class_item_img);
            }
            currentTitle = info.getTitle();
            class_item_title.setText(info.getTitle());
            if(null != info.getTags() && info.getTags().size() > 0){
                class_item_zz.setText(info.getTags().get(0));
            }else{
                class_item_zz.setVisibility(View.GONE);
            }
            if(null != info.getTags() && info.getTags().size() > 1){
                class_item_bz.setText(info.getTags().get(1));
            }else{
                class_item_bz.setVisibility(View.GONE);
            }
            if(null != info.getTags() && info.getTags().size() > 2){
                class_item_gk.setText(info.getTags().get(2));
            }else{
                class_item_gk.setVisibility(View.GONE);
            }
            if(null != info.getTags() && info.getTags().size() > 3){
                class_item_dy.setText(info.getTags().get(3));
            }else{
                class_item_dy.setVisibility(View.GONE);
            }
            if(null != info.getTags() && info.getTags().size() > 4){
                class_item_nd.setText(info.getTags().get(4));
            }else{
                class_item_nd.setVisibility(View.GONE);
            }
            if(null != info.getTags() && info.getTags().size() > 5){
                class_item_gx.setText(info.getTags().get(5));
            }else{
                class_item_gx.setVisibility(View.GONE);
            }
            if(null != info.getTags() && info.getTags().size() > 6){
                class_item_yy.setText(info.getTags().get(6));
            }else{
                class_item_yy.setVisibility(View.GONE);
            }
        }
    }

}
