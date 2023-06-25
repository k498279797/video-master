package com.vxiaokang.video.activity.video.huang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.bean.ResultKeywordBean;
import com.vxiaokang.video.activity.video.bean.ResultKeywordItemInfoBean;
import com.vxiaokang.video.activity.video.bean.ResultVideoBean;
import com.vxiaokang.video.activity.video.bean.ResultVideoItemInfoBean;
import com.vxiaokang.video.adapter.HuangVideoSearchListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;
import com.vxiaokang.video.util.codec.Base64;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

/****
 * 推荐列表
 */
public class HuangVideoSearchListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "HuangVideoSearchListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private HuangVideoSearchListDataAdapter classAdapter;
    private int pageIndex = 1;
    private int pageSize = 50;
    private int total = 0;
    private int totalPage = 1;
    private boolean loadFlag = true;

    private String topTitle = "";

    private EditText search_edit;
    private ImageView search_button;
    private String searchVal = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huang_video_search_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView() {

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        search_edit = findViewById(R.id.search_edit);
        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        search_edit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyBoardUtils.closeSoftKeyboard(v);
                String query =  v.getText().toString();
                if(!EmptyUtils.isEmpty(query)){
                    searchVal = query.trim();
                    pageIndex = 1;
                    total = 0;
                    totalPage = 1;
                    searchVal = query;
                    setDataList();
                }
                return true;
            }
            return false;
        });

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        classRecView = findViewById(R.id.class_rec_view);

        String title = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)){
            txt_show_set_back.setText(title);
        }

        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if(totalPage > pageIndex && !StringUtils.isEmpty(searchVal)){
                if(loadFlag){
                    pageIndex = pageIndex+1;
                    setDataList();
                    refreshlayout.finishLoadMore(5000);
                }else{
                    refreshlayout.finishLoadMore(5000);
                }
            }else{
                refreshlayout.finishLoadMore(2000,true,true);
            }
        });
        keywordSearch();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        classAdapter = new HuangVideoSearchListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                Intent intent = new Intent(this, HuangVideoDetailActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
               // intent.putExtra("topTitle", topTitle);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.sourceItemId, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceViews, clickBean.getUpVote());
                intent.putExtra(Constants.sourceDuration, clickBean.getAuthor());
                intent.putExtra(Constants.sourceId, clickBean.getVideoId());
                intent.putExtra("searchVal", searchVal);
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
            case R.id.search_button:
                KeyBoardUtils.closeSoftKeyboard(v);
                if(!StringUtils.isEmpty(search_edit.getText())){
                    pageIndex = 1;
                    total = 0;
                    totalPage = 1;
                    searchVal = search_edit.getText().toString().trim();
                    setDataList();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    private void keywordSearch() {
        try{
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                String apiUrl = "https://"+ HuangConstant.getReqDomain()+"/api/video/keyword?uid=2&d_device=h5&v_code=252";
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
                    if(dataListTemp.size() > 0){
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
            Log.d(TAG,"返回 -searchVal :" + searchVal);
            Log.d(TAG,"返回 -page :" + pageIndex);
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
                        if(!StringUtils.isEmpty(document)){
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
                        }
                    }catch (Exception e){
                    e.printStackTrace();
                }
                    runOnUiThread(()->{
                        if(dataListTemp.size() > 0){
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
                        }else{
                            Toast.makeText(App.getContext(),"--未找到相关内容--",Toast.LENGTH_LONG).show();
                        }
                    });
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
