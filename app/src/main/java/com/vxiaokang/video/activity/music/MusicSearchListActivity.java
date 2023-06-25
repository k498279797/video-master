package com.vxiaokang.video.activity.music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.music.MusicHotTabAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;

/****
 * 音乐查询
 */
public class MusicSearchListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MusicSearchListActivity";
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private MusicHotTabAdapter classAdapter;

    private ImageView search_button;
    private EditText search_edit;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_search_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }

    private void initView() {

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);


        if(videoInfoList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/music/taghot.json","utf-8");
            if(!StringUtils.isEmpty(json)){
                videoInfoList = new Gson().fromJson(json,new TypeToken<List<VideoInfoBean>>(){}.getType());
            }
        }
        search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        search_edit = findViewById(R.id.search_edit);
        search_edit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyBoardUtils.closeSoftKeyboard(v);
                String query =  v.getText().toString();
                if(!EmptyUtils.isEmpty(query)){

                }
                return true;
            }
            return false;
        });

        classRecView = findViewById(R.id.class_rec_view);
      //  GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);

       // WrapLayoutManager wrapLayoutManager = new WrapLayoutManager();
       // wrapLayoutManager.setAutoMeasureEnabled(true);
        //classRecView.setLayoutManager(wrapLayoutManager);
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW); //主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP); //按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START); //交叉轴的起点对齐。
        classAdapter = new MusicHotTabAdapter(this,videoInfoList,0);
        classRecView.setLayoutManager(flexboxLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                Intent intent = new Intent(this, MusicResultListActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
                String title = clickBean.getTitle();
                search_edit.setText(title);
                intent.putExtra("topTitle", clickBean.getTitle());
                intent.putExtra("dataId", clickBean.getLink());
                intent.putExtra("title", clickBean.getAuthor());
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
                    Intent intent = new Intent(this, MusicResultListActivity.class);
                    String title = search_edit.getText().toString();
                    intent.putExtra("topTitle", title);
                    intent.putExtra("dataId", "");
                    intent.putExtra("title", title);
                    startActivity(intent);
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

}
