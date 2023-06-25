package com.vxiaokang.video.activity.video.huang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.bean.ResultVideoBean;
import com.vxiaokang.video.activity.video.bean.ResultVideoItemInfoBean;
import com.vxiaokang.video.adapter.HuangVideoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.util.codec.Base64;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

/****
 * 推荐列表
 */
public class HuangVideoListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "HuangVideoListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private HuangVideoListDataAdapter classAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int total = 0;
    private int totalPage = 1;
    private boolean loadFlag = true;
    private String categoryId = "15";
    private String topTitle = "";

    private LinearLayout top_search_layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huang_video_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView() {

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        top_search_layout = findViewById(R.id.top_search_layout);
        top_search_layout.setOnClickListener(this);

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        classRecView = findViewById(R.id.class_rec_view);

        String title = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)){
            topTitle = title;
            txt_show_set_back.setText(title);
        }
        String formType = getIntent().getStringExtra("categoryId");
       // Log.d(TAG,"categoryId = "+formType);
        if(!TextUtils.isEmpty(formType)){
            categoryId = formType;
        }

        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if(totalPage > pageIndex ){
               // refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                if(loadFlag){
                    pageIndex = pageIndex+1;
                    setDataList(categoryId,pageIndex);
                    refreshlayout.finishLoadMore(5000);
                }else{
                    refreshlayout.finishLoadMore(5000);
                }
            }else{
                refreshlayout.finishLoadMore(2000,true,true);
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        classAdapter = new HuangVideoListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                Intent intent = new Intent(this, HuangVideoDetailActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
                intent.putExtra("topTitle", topTitle);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceViews, clickBean.getUpVote());
                intent.putExtra(Constants.sourceDuration, clickBean.getAuthor());
                intent.putExtra(Constants.sourceItemId, clickBean.getCategoryId());
                intent.putExtra(Constants.sourceId, clickBean.getVideoId());
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        initData();
        setDataList(categoryId,pageIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.top_search_layout:
                Intent intent = new Intent(this, HuangVideoSearchListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }


    private void setDataList(String categoryId,int page) {
        try{
            Log.d(TAG,"返回 -page :" + page); // 56
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                 //最新 order=time  最热 order=hit   好评 order=commend
                String apiUrl = "https://"+ HuangConstant.getReqDomain() +"/api/home/recommend_list?uid=2&page=%s&id=%s&d_device=h5&v_code=252";
                apiUrl = String.format(apiUrl,page,categoryId);
                //apiUrl = "https://app.rk5ck5dzx.com/api/visualization/get&uid=98833266&tab_id=12&d_device=h5&v_code=252";
                try{
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).get().text();
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
                    runOnUiThread(()->{
                        if(dataListTemp.size() > 0){
                            loadFlag = true;
                            if(page == 1){
                                totalPage = total/pageSize + (total%pageSize > 0 ? 1 : 0);
                                Log.d(TAG,"返回 -total :" + total);
                                Log.d(TAG,"返回 -totalPage :" + totalPage);
                            }
                            if(page == 1){
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

    private void initData(){
        try{
            List<VideoInfoBean> dataListTemp = new ArrayList<>();
            String document = ResourceUtils.readAssets2String("data/video/video_"+categoryId+".txt","utf-8");
            if(StringUtils.isEmpty(document)){
                return;
            }
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
            runOnUiThread(()->{
                if(dataListTemp.size() > 0){
                    videoInfoList = dataListTemp;
                    classAdapter.updateData(dataListTemp);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
