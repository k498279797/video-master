package com.vxiaokang.video.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.KeyBoardDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.KeyBoardBean;
import com.vxiaokang.video.bean.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

/****
 * 常用快捷
 */
public class KeyBoardActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "KeyBoardActivity";
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<KeyBoardBean> dataList = new ArrayList<>();
    private KeyBoardDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);


        dataList.addAll(KeyBoardUtils.getDataList());
        classRecView = findViewById(R.id.keyboard_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        adapter = new KeyBoardDataAdapter(this,dataList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(adapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        adapter.setOnItemClickListener(position -> {
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
