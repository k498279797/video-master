package com.vxiaokang.video.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.BottomTabView;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.fragment.ClassFrament;
import com.vxiaokang.video.fragment.HomeFrament;
import com.vxiaokang.video.fragment.LinksFrament;
import com.vxiaokang.video.fragment.MusicFrament;
import com.vxiaokang.video.fragment.TVFrament;
import com.vxiaokang.video.fragment.myFrament;

import java.util.ArrayList;


/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LuckMainActivity";

    private BottomTabView bottomTabView;
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
   // private View mainTopbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //任务状态的获取 将对象注册到Aria
        AndroidNetworking.initialize(this);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
      /*  if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LuckMainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }*/
        runOnUiThread(() -> {
          try{
              //后台统计
              //DeviceUtils.addDevice(this);
          }catch (Exception e){}
        });
    }
    protected void initView() {
        viewPager = findViewById(R.id.viewPager);
        bottomTabView = findViewById(R.id.bottomTabView);
       // mainTopbar = findViewById(R.id.main_topbar);
        initTab();
    }

   // @Override
   /* protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    //底部Tab加载Fragment
    private void initTab() {
        fragments = new ArrayList<>();
      //  fragments.add(new IndexFrament());
        fragments.add(new HomeFrament());
        fragments.add(new TVFrament());
        fragments.add(new ClassFrament());
       // fragments.add(new HuangFrament());
        fragments.add(new MusicFrament());
        fragments.add(new LinksFrament());
        fragments.add(new myFrament());
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(adapter);
        tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_index_name), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_index_n, R.mipmap.tab_index_s));
        tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_tv_name), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_share_n, R.mipmap.tab_share_s));
        tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_hot_name), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_hot_n, R.mipmap.tab_hot_s));
       // tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_class_huang), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_icon_my_n, R.mipmap.tab_icon_my_s));
        tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_class_music), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_icon_music_n, R.mipmap.tab_icon_music_s));
        tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_class_name), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_icon_curriculum_n, R.mipmap.tab_icon_curriculum_s));
        tabItemViews.add(new BottomTabView.TabItemView(this, getResources().getString(R.string.tab_my_name), R.color.color_tabTitle, R.color.color_tabTitle_selected, R.mipmap.tab_mine_n, R.mipmap.tab_mine_s));
        bottomTabView.setTabItemViews(tabItemViews);
        bottomTabView.setUpWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(6);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy----");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop----");
        super.onStop();
    }
}
