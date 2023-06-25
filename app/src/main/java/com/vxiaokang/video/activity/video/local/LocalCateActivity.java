package com.vxiaokang.video.activity.video.local;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.OpenWebSiteActivity;
import com.vxiaokang.video.activity.VideoActivity;
import com.vxiaokang.video.activity.WebSiteViewActivity;
import com.vxiaokang.video.activity.video.bean.CategoryBean;
import com.vxiaokang.video.adapter.LocalCateDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.ToolsBean;
import com.vxiaokang.video.bean.VideoInfoBean;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;

/****
 * 分类数据
 */
public class LocalCateActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ToolsActivity";
    private ImageView ivBack;
    private PromptDialog promptDialog;

    private RecyclerView item_rec_view;
    private List<CategoryBean> dataList = new ArrayList<>();
    private LocalCateDataAdapter toolsDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_cate);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        promptDialog = new PromptDialog(this);
        promptDialog.setViewAnimDuration(1000);
        initView();
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        if(null == dataList || dataList.size() == 0){
           // converData();
            String json = ResourceUtils.readAssets2String("data/local/cate_list.json","utf-8");
            if(!StringUtils.isEmpty(json)){
                dataList = new Gson().fromJson(json,new TypeToken<List<CategoryBean>>(){}.getType());
            }
        }
        item_rec_view = findViewById(R.id.item_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        toolsDataAdapter = new LocalCateDataAdapter(this,dataList);
        item_rec_view.setLayoutManager(gridLayoutManager);
        item_rec_view.setAdapter(toolsDataAdapter);
        item_rec_view.setFocusable(false);
        item_rec_view.setNestedScrollingEnabled(false);
        toolsDataAdapter.setOnItemClickListener(position -> {
            CategoryBean info = dataList.get(position);
            Intent intent = new Intent(LocalCateActivity.this, LocalVideoListActivity.class);
            intent.putExtra("categoryId", info.getCategoryId());
            intent.putExtra("title", info.getCategoryName());
            startActivity(intent);
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

}
