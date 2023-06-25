package com.vxiaokang.video.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.hdtv.HdtvVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.kantv.KantvVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.kdy.KdyVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.meijugua.MeijuguaVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.pkmp.PkmpVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.shipin.ShipinVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.yinshi.YinshiVideoDetailPlayActivity;
import com.vxiaokang.video.adapter.HistroyListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.entity.PlayRecord;
import com.vxiaokang.video.entity.dao.DaoSession;
import com.vxiaokang.video.entity.dao.PlayRecordDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/****
 * 历史记录
 */
public class HistoryListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClassListActivity";
    private TextView txt_show_set_back; //标题
    private TextView clear_btn; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<PlayRecord> videoInfoList = new ArrayList<>();
    private HistroyListDataAdapter classAdapter;

    private LinearLayout dataMessage;
    private LinearLayout noMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView() {
        dataMessage = findViewById(R.id.data_message);
        noMessage = findViewById(R.id.no_message);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        classRecView = findViewById(R.id.class_rec_view);

        clear_btn = findViewById(R.id.clear_btn);
        clear_btn.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        classAdapter = new HistroyListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
        classAdapter.setOnItemClickListener((position,type) -> {
            try{
                if(type == 1){
                    //删除
                    delRecord(position);
                }else{
                    PlayRecord clickBean =  videoInfoList.get(position);
                    Intent intent = null;
                    if(clickBean.getType() == 0){
                        intent = new Intent(this, ShipinVideoDetailPlayActivity.class);
                    }else if(clickBean.getType() == 1){
                        intent = new Intent(this, PkmpVideoDetailPlayActivity.class);
                    }else if(clickBean.getType() == 2){
                        intent = new Intent(this, YinshiVideoDetailPlayActivity.class);
                    }else if(clickBean.getType() == 3){
                        intent = new Intent(this, KantvVideoDetailPlayActivity.class);
                    }else if(clickBean.getType() == 4){
                        intent = new Intent(this, KdyVideoDetailPlayActivity.class);
                    }else if(clickBean.getType() == 5){
                        intent = new Intent(this, MeijuguaVideoDetailPlayActivity.class);
                    }else if(clickBean.getType() == 6){
                        intent = new Intent(this, HdtvVideoDetailPlayActivity.class);
                    }
                    intent.putExtra(Constants.sourceUrl, clickBean.getSourceLink());
                    intent.putExtra(Constants.sourceLink, clickBean.getLink());
                    intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                    intent.putExtra(Constants.sourceTitle, clickBean.getKeywords());
                    intent.putExtra(Constants.sourceItemName,clickBean.getTitle());
                    intent.putExtra(Constants.sourcePosition,clickBean.getPosition());
                    intent.putExtra("categoryId", "");
                    startActivity(intent);
                }
                /*Intent intent = new Intent(this, ClassDetailActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceViews, clickBean.getUpVote());
                intent.putExtra(Constants.sourceDuration, clickBean.getDuration());
                intent.putExtra(Constants.sourceItemId, clickBean.getCategoryId());
                startActivity(intent);*/
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        getHistoryList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.clear_btn:
                clearData();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }



    private void getHistoryList(){
        try{
          //  Log.d(TAG,"加载历史记录...");
            DaoSession daoSession = App.getDaoSession();
            Query<PlayRecord> query = daoSession.queryBuilder(PlayRecord.class).orderDesc(PlayRecordDao.Properties.Update).build();
            if(null == query){
                return ;
            }
            List<PlayRecord> records = query.list();
            if(null != records && records.size() > 0){
               videoInfoList = records;
               classAdapter.updateData(records);
                dataMessage.setVisibility(View.VISIBLE);
                noMessage.setVisibility(View.GONE);
            }else{
                dataMessage.setVisibility(View.GONE);
                noMessage.setVisibility(View.VISIBLE);
              //  Log.d(TAG,"暂无历史数据数据");
            }
        }catch (Exception e){
           // Log.e(TAG,"加载历史数据异常："+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除记录
     */
    private void delRecord(int position){
       try{
           new XPopup.Builder(this).asConfirm("提示", "你确定要删除此记录吗?",
                   "取消", "确定",
                   () -> {
                       PlayRecord currentItem = videoInfoList.get(position);
                       Long currentId = currentItem.getId();
                       DaoSession daoSession = App.getDaoSession();
                       daoSession.getPlayRecordDao().deleteByKey(currentId);
                       getHistoryList();
                   }, null, false).show();
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    /**
     * 清除记录
     */
    private void clearData(){
        try{
            new XPopup.Builder(this).asConfirm("提示", "你确定要清除全部记录吗?",
                    "取消", "确定",
                    () -> {
                        DaoSession daoSession = App.getDaoSession();
                        daoSession.getPlayRecordDao().deleteAll();
                        getHistoryList();
                    }, null, false).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
