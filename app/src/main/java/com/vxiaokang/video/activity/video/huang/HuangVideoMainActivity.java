package com.vxiaokang.video.activity.video.huang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.bean.CategoryBean;
import com.vxiaokang.video.activity.video.bean.ResultLineBean;
import com.vxiaokang.video.activity.video.frament.HuangCateFrament;
import com.vxiaokang.video.activity.video.frament.HuangIndexFrament;
import com.vxiaokang.video.base.TopTabView;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.util.codec.Base64;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuangVideoMainActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG = "HuangVideoMainActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.my_topbar)
    View mybar;
    @BindView(R.id.topTabView)
    TopTabView topTabView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.go_video_line)
    TextView go_video_line;
    private FragmentPagerAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<TopTabView.TabItemView> tabItemViews = new ArrayList<>();
    private boolean loadFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_main);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        ButterKnife.bind(this);
        StatusBarUtil.setMyBarHeight(mybar,this);
        initView();
    }
    private void initView() {
        ArrayList<CategoryBean> cateList = new ArrayList<>();
        cateList.add(new CategoryBean("3","首页"));
        cateList.add(new CategoryBean("13","优选"));
        cateList.add(new CategoryBean("15","专题"));
        cateList.add(new CategoryBean("8","国产"));
        cateList.add(new CategoryBean("12","日本"));
       // cateList.add(new CategoryBean("17","无码"));
        cateList.add(new CategoryBean("11","欧美"));
        cateList.add(new CategoryBean("16","动漫"));
        cateList.add(new CategoryBean("10","10"));
        cateList.add(new CategoryBean("17","17"));
        cateList.add(new CategoryBean("19","19"));
        cateList.add(new CategoryBean("20","20"));
        cateList.add(new CategoryBean("22","22"));
        cateList.add(new CategoryBean("24","24"));
        cateList.add(new CategoryBean("27","27"));
        fragments = new ArrayList<>();
        int dColor = R.color.color_tabTitle;
        int sColor = R.color.color_FFFFFF;
        fragments.add(new HuangCateFrament());
        tabItemViews.add(new TopTabView.TabItemView(this, "首页", dColor, sColor));
        for(CategoryBean item: cateList){
            fragments.add( HuangIndexFrament.newInstance(item.getCategoryId()));
            tabItemViews.add(new TopTabView.TabItemView(this, item.getCategoryName(), dColor, sColor));
        }
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

       /* tabItemViews.add(new TopTabView.TabItemView(this, getResources().getString(R.string.tab_index_name),dColor , sColor));
        tabItemViews.add(new TopTabView.TabItemView(this, getResources().getString(R.string.tab_tv_name), dColor, sColor));
        tabItemViews.add(new TopTabView.TabItemView(this, getResources().getString(R.string.tab_hot_name), dColor, sColor));
        tabItemViews.add(new TopTabView.TabItemView(this, getResources().getString(R.string.tab_class_huang), dColor, sColor));
        tabItemViews.add(new TopTabView.TabItemView(this, getResources().getString(R.string.tab_class_name), dColor, sColor));
        tabItemViews.add(new TopTabView.TabItemView(this, getResources().getString(R.string.tab_my_name), dColor, sColor));*/
        topTabView.setTabItemViews(tabItemViews);
        topTabView.setUpWithViewPager(viewPager);

    }
    @OnClick({R.id.iv_back,R.id.go_video_line})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.go_video_line:
                    AttachPopupView attachPopupView = new XPopup.Builder(HuangVideoMainActivity.this)
                            .hasShadowBg(false)
                            .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                            .asAttachList(new String[]{ "线路1", "线路2", "线路3", "线路4","路线5"},null,new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    Log.d(TAG,"text"+text+" position = "+position);
                                    /**
                                     *  https://app.ivlutt.com
                                     *  https://app.gggkkg.com
                                     *  https://app.ei751.com
                                     */
                                    if(position == 0){
                                        //  goViewTemplete(holderPosition);
                                       HuangConstant.setReqDomain("appz.dtkjxwjvf6.com");
                                       Intent intent = new Intent(HuangVideoMainActivity.this, HuangVideoMainActivity.class);
                                       startActivity(intent);
                                       finish();
                                    }else if(position == 1){
                                        // goDelTemplete(holderPosition);
                                        HuangConstant.setReqDomain("app.gggkkg.com");
                                        Intent intent = new Intent(HuangVideoMainActivity.this, HuangVideoMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else if(position == 2){
                                        HuangConstant.setReqDomain("app.ei751.com");
                                        Intent intent = new Intent(HuangVideoMainActivity.this, HuangVideoMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else if(position == 3){
                                        HuangConstant.setReqDomain("app.rk5ck5dzx.com");
                                        Intent intent = new Intent(HuangVideoMainActivity.this, HuangVideoMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else if(position == 4){
                                        HuangConstant.setReqDomain("app.ivlutt.com");
                                        Intent intent = new Intent(HuangVideoMainActivity.this, HuangVideoMainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }, 0, 0);
                    attachPopupView.show();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!loadFlag){
            //getApiDomain();
        }
    }


    private void getDomain() {
        try{
            new Thread(() -> {
                String apiUrl = "https://h5.gabrielroybal.com/main.dart.js";
                try{
                    String document = Jsoup.connect(apiUrl).ignoreContentType(true).timeout(60000).get().text();
                    if(!StringUtils.isEmpty(document)){
                        String regString = "(\"url\",\")(.+?)(\"])";
                        Pattern pattern = Pattern.compile(regString);
                        Matcher matcher = pattern.matcher(document);
                        while (matcher.find()) {
                            Log.d(TAG,"sss"+matcher.group(0));
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(()->{

                });
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getApiDomain() {
        try{
            new Thread(() -> {
                String apiUrl = "https://app.ivlutt.com/api/video/video_line?uid=&d_device=h5&v_code=254";
                try{
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).get().text();
                    String string = new String(Base64.decodeBase64(document));
                    if(!StringUtils.isEmpty(string)){
                        ResultLineBean result = new Gson().fromJson(string,new TypeToken<ResultLineBean>(){}.getType());
                        if(null != result && null!= result.getData() && null != result.getData().getList() && result.getData().getList().size() > 0){
                            String temp = result.getData().getList().get(0).getDomain();
                            if(!StringUtils.isEmpty(temp)){
                                runOnUiThread(()->{
                                    Toast.makeText(HuangVideoMainActivity.this,"域名："+temp,Toast.LENGTH_LONG).show();
                                    loadFlag = true;
                                    HuangConstant.setReqDomain(temp);
                                });
                            }
                        }
                    }
                    Log.d(TAG,"sss"+string);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
