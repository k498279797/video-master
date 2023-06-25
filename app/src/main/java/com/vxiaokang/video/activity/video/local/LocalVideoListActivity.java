package com.vxiaokang.video.activity.video.local;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.vxiaokang.video.activity.VideoPlayActivity;
import com.vxiaokang.video.activity.video.bean.ResultVideoItemInfoBean;
import com.vxiaokang.video.adapter.LocalVideoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

/****
 * 列表
 */
public class LocalVideoListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "LocalVideoListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<ResultVideoItemInfoBean> videoInfoList = new ArrayList<>();
    private List<ResultVideoItemInfoBean> allList = new ArrayList<>();
    private LocalVideoListDataAdapter classAdapter;
    private int pageIndex = 1;
    private int pageSize = 20;
    private boolean loadFlag = true;
    private String searchword = "";

    private ImageView search_button;
    private EditText search_edit;
    private String categoryId = "id_all";
    private String topTitle = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }


    private void initView() {

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        search_edit = findViewById(R.id.search_edit);
        search_edit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyBoardUtils.closeSoftKeyboard(v);
                String query =  v.getText().toString();
                if(!EmptyUtils.isEmpty(query)){
                    searchword = query.trim();
                    pageIndex = 1;
                    setDataList(pageIndex,searchword);
                }
                return true;
            }
            return false;
        });

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        //search_tab = findViewById(R.id.search_tab);
        //search_tab.setText("全部");
        //search_tab.setOnClickListener(this);
        classRecView = findViewById(R.id.class_rec_view);

        String title = getIntent().getStringExtra("title");
        String categoryIdTemp = getIntent().getStringExtra("categoryId");
        if(!TextUtils.isEmpty(title)){
            topTitle = title;
            txt_show_set_back.setText(title);
        }
        if(!StringUtils.isEmpty(categoryIdTemp)){
            categoryId = categoryIdTemp;
        }
        if(allList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/local/"+categoryId+".json","utf-8");
            if(!StringUtils.isEmpty(json)){
                allList = new Gson().fromJson(json,new TypeToken<List<ResultVideoItemInfoBean>>(){}.getType());
            }
        }
        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
               // refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                if(loadFlag){
                    pageIndex = pageIndex+1;
                    setDataList(pageIndex,searchword);
                    refreshlayout.finishLoadMore(5000);
                }else{
                    refreshlayout.finishLoadMore(2000,true,true);
                }
        });
        setDataList(pageIndex,searchword);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        classAdapter = new LocalVideoListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                Intent intent = new Intent(this, VideoPlayActivity.class);
                ResultVideoItemInfoBean clickBean = videoInfoList.get(position);
                intent.putExtra("url", clickBean.getPlayurl());
                intent.putExtra("title", topTitle);
                intent.putExtra("videoTitle", clickBean.getName());
                intent.putExtra("views", clickBean.getPlays());
                intent.putExtra("imgUrl", clickBean.getImg3());
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
                    searchword = search_edit.getText().toString().trim();
                    setDataList(pageIndex,searchword);
                    //setSearchDataList(categoryId,pageIndex,searchword);
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


    private void setDataList(int page,String keywords) {
        try{
            if(allList.isEmpty()){
                return;
            }
            // 从所有是数据里面拿数据
            new Thread(() -> {
                List<ResultVideoItemInfoBean> dataListTemp = new ArrayList<>();
                List<ResultVideoItemInfoBean> firstSelectList = new ArrayList<>();
                if(!StringUtils.isEmpty(keywords)){
                    for(ResultVideoItemInfoBean  temp:  allList){
                        if(temp.getName().contains(keywords) || keywords.contains(temp.getName())){
                            firstSelectList.add(temp);
                        }
                    }
                }else{
                    firstSelectList.addAll(allList);
                }
                if(firstSelectList.size() > 0){
                    if(page == 1){
                        if(firstSelectList.size() < pageSize){
                            dataListTemp = firstSelectList;
                        }else{
                            dataListTemp = firstSelectList.subList(0,pageSize);
                        }
                    }else{
                        if(firstSelectList.size() >(page-1)*pageSize){//是否有下一页
                            if(firstSelectList.size() >= page*pageSize){
                                dataListTemp = firstSelectList.subList(((page-1)*pageSize),page*pageSize);
                            }else{
                                dataListTemp = firstSelectList.subList(((page-1)*pageSize),firstSelectList.size());
                            }
                        }
                    }
                }
                List<ResultVideoItemInfoBean> finalDataListTemp = dataListTemp;
                runOnUiThread(()->{
                    if(finalDataListTemp.size() > 0){
                        loadFlag = true;
                        if(page == 1){
                            videoInfoList = finalDataListTemp;
                            classAdapter.updateData(finalDataListTemp);
                        }else{
                            videoInfoList.addAll(finalDataListTemp);
                            classAdapter.addData(finalDataListTemp);
                        }
                    }
                });

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
