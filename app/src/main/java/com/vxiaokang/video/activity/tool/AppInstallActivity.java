package com.vxiaokang.video.activity.tool;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.tool.AppInfoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用安装
 */
public class AppInstallActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "AppInstallActivity";
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private AppInfoListDataAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView(){
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        if(null == videoInfoList || videoInfoList.size() == 0){
            VideoInfoBean bean = new VideoInfoBean();
            bean.setUpVote("678 万次安装  5954人评分  4.9分高分  ");
            bean.setCoverImage("https://appimg.dbankcdn.com/application/icon144/4e08aa2b8de1490fae96f08f67467778.png");
            bean.setTitle("电子木鱼");
            bean.setDescription("海量音乐，下载，试听...");
            bean.setKeywords("com.sf.wooden.fishfish");
            videoInfoList.add(bean);

            bean = new VideoInfoBean();
            bean.setUpVote("26 万次安装  ");
            bean.setCoverImage("https://appimg.dbankcdn.com/application/icon144/ddf941d8db0b49dda5d6696e829422d4.png");
            bean.setTitle("手机电子琴");
            bean.setDescription("学钢琴，钢琴块，刚琴，钢琴键盘，钢琴谱，钢琴练习，钢琴游戏，钢琴师，钢琴老师，钢琴大全，钢琴大师，手机钢琴，完美钢琴，电子琴模拟器ai，piano，极品钢琴");
            bean.setKeywords("com.guanheng.piano");
            videoInfoList.add(bean);

            bean = new VideoInfoBean();
            bean.setUpVote("48 万次安装  ");
            bean.setCoverImage("https://appimg.dbankcdn.com/application/icon144/b766a98c861f4d569ae27af3495bf826.png");
            bean.setTitle("五子棋双人");
            bean.setDescription("五子连珠天地动黑白两色竞争辉");
            bean.setKeywords("com.guanheng.gobang");
            videoInfoList.add(bean);
        }


        classRecView = findViewById(R.id.class_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        classAdapter = new AppInfoListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        classAdapter.setOnItemClickListener(position -> {
            try{
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


}