package com.vxiaokang.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.ToolsDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.ToolsBean;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
/****
 * 常用工具
 */
public class ToolsActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ToolsActivity";
    private ImageView ivBack;
    private PromptDialog promptDialog;

    private RecyclerView item_rec_view;
    private List<ToolsBean> dataList = new ArrayList<>();
    private ToolsDataAdapter toolsDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
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
            converData();
        }
        item_rec_view = findViewById(R.id.item_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        toolsDataAdapter = new ToolsDataAdapter(this,dataList);
        item_rec_view.setLayoutManager(gridLayoutManager);
        item_rec_view.setAdapter(toolsDataAdapter);
        item_rec_view.setFocusable(false);
        item_rec_view.setNestedScrollingEnabled(false);
        toolsDataAdapter.setOnItemClickListener(position -> {
            ToolsBean info = dataList.get(position);
            if(StringUtils.equals(info.getType(),"1")){
                Intent intent = new Intent(ToolsActivity.this, OpenWebSiteActivity.class);
                //Log.d(TAG,"link "+dataList.get(position).getUrl());
                intent.putExtra("url", dataList.get(position).getUrl());
                intent.putExtra("title", dataList.get(position).getTitle());
                startActivity(intent);
            } if(StringUtils.equals(info.getType(),"2")){
                Intent intent = new Intent(ToolsActivity.this, VideoActivity.class);
                //Log.d(TAG,"link "+dataList.get(position).getUrl());
                intent.putExtra("url", dataList.get(position).getUrl());
                intent.putExtra("title", dataList.get(position).getTitle());
                startActivity(intent);
            }else{
                Intent intent = new Intent(ToolsActivity.this, WebSiteViewActivity.class);
                Log.d(TAG,"link "+dataList.get(position).getUrl());
                intent.putExtra("url", dataList.get(position).getUrl());
                intent.putExtra("title", dataList.get(position).getTitle());
                startActivity(intent);
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


    private void converData(){
        ToolsBean info = new ToolsBean();
       /* info.setTitle("实用网站");
        info.setType("0");
        info.setUrl("https://www.aqd131.cc:8443/?f=www.aqd2021.cc");
        dataList.add(info);*/

        info = new ToolsBean();
        info.setTitle("88ys");
        info.setType("0");
        info.setUrl("https://m.88ys.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("88ysw");
        info.setType("0");
        info.setUrl("https://www.88ysw.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("片库");
        info.setType("0");
        info.setUrl("https://www.pkmp4.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("88影视");
        info.setType("0");
        info.setUrl("https://www.80yy.la");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("vip电影");
        info.setType("0");
        info.setUrl("http://www.vip1280.net/frim/index1.html");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("80s电影网");
        info.setType("0");
        info.setUrl("http://m.bayiyy.com");
        //dataList.add(info);

        info = new ToolsBean();
        info.setTitle("韩剧");
        info.setType("0");
        info.setUrl("https://www.juji.tv/index.html");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("日剧");
        info.setType("0");
        info.setUrl("https://m.rijutv.com");
        //dataList.add(info);

        info = new ToolsBean();
        info.setTitle("放放TV");
        info.setType("0");
        info.setUrl("https://m.fftv8.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("freemobile");
        info.setType("0");
        info.setUrl("https://www.freemobileporn.org");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("aqdx75");
        info.setType("0");
        info.setUrl("https://vip.aqdz13.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("bkf13");
        info.setType("0");
        info.setUrl("http://bkf13.com/");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("66摸");
        info.setType("0");
        info.setUrl("https://66ams.top/video/");
        dataList.add(info);


        info = new ToolsBean();
        info.setTitle("aqdtv527");
        info.setType("0");
       // info.setUrl("https://vip.aqdtv527.com");
        info.setUrl("https://vip.aqdz13.com");
        dataList.add(info);


        info = new ToolsBean();
        info.setTitle("fuckvideos");
        info.setType("0");
        info.setUrl("https://gay-fuckvideos.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("tubewatch");
        info.setType("0");
        info.setUrl("https://gaytubewatch.com");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("tubewatch");
        info.setType("0");
        info.setUrl("https://gayfuckhub.com/");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("lvye03");
        info.setType("0");
        info.setUrl("http://www.lvye03.com");
        //dataList.add(info);

        info = new ToolsBean();
        info.setTitle("cncape");
        info.setType("0");
        info.setUrl("http://www.cncape.com");
        //dataList.add(info);

        info = new ToolsBean();
        info.setTitle("xyydccz");
        info.setType("0");
        info.setUrl("http://www.xyydccz.com");
        //dataList.add(info);

        info = new ToolsBean();
        info.setTitle("61xbxb");
        info.setType("0");
        info.setUrl("http://www.61xbxb.com");
        //dataList.add(info);

        info = new ToolsBean();
        info.setTitle("打开网站");
        info.setType("1");
        info.setUrl("");
        dataList.add(info);

        info = new ToolsBean();
        info.setTitle("本地视频");
        info.setType("2");
        info.setUrl("");
        dataList.add(info);
    }
}
