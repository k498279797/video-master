package com.vxiaokang.video.activity.video.meijugua;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.R;

import com.vxiaokang.video.activity.video.common.UserAgentUtils;
import com.vxiaokang.video.activity.video.meijugua.bean.MeijuguaConstant;
import com.vxiaokang.video.adapter.meijugua.MeijuguaVideoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/****
 * 列表
 */
public class MeijuguaVideoListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "MeijuguaVideoListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private MeijuguaVideoListDataAdapter classAdapter;
    private int pageIndex = 1;
    private boolean loadFlag = true;
    private String topTitle = "";
    private String searchword = "";
    // private TextView search_tab;

    private ImageView search_button;
    private EditText search_edit;

    private String categoryId = "type1";
    private String orderBy = "addtime";
    private String searchYear = "";
    private String searchRegion = "";
    private String searchType = "";
    private String searchLanguage = "";


    private Spinner tabSp;
    private Spinner yearSp;
    private Spinner orderSp;
    private Spinner regionSp;
    private Spinner typeSp;
    private Spinner languageSp;

    private Context context;
    private boolean initFlag = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meijugua_video_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        context = this;
       // initSpinnerTab(); //分类
       // initSpinnerRegion(); //地区
       // initSpinnerType();//剧情
       // initSpinnerLanguage();//语言
        initSpinnerYear();//年份
        initSpinnerOrder();//排序
        initView();
    }


    private void initSpinnerRegion(){
        String[] tabName = MeijuguaConstant.regionName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        regionSp = findViewById(R.id.region_tab);
        //设置下拉框的标题，不设置就没有难看的标题了
        regionSp.setPrompt("地区");
        //设置下拉框的数组适配器
        regionSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        //regionSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        regionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchRegion = MeijuguaConstant.regionId[position]+"";
                if(initFlag){
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initSpinnerType(){
        String[] tabName = MeijuguaConstant.typeName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        typeSp = findViewById(R.id.type_tab);
        //设置下拉框的标题，不设置就没有难看的标题了
        typeSp.setPrompt("类型");
        //设置下拉框的数组适配器
        typeSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        // typeSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchType = MeijuguaConstant.typeId[position]+"";
                if(initFlag){
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initSpinnerLanguage(){
        String[] tabName = MeijuguaConstant.languageName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        languageSp = findViewById(R.id.language_tab);
        //设置下拉框的标题，不设置就没有难看的标题了
        languageSp.setPrompt("语言");
        //设置下拉框的数组适配器
        languageSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        // languageSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        languageSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchLanguage = MeijuguaConstant.languageId[position]+"";
                if(initFlag){
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void initSpinnerTab(){
        String[] tabName = MeijuguaConstant.tabName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        tabSp = findViewById(R.id.search_tab);
        //设置下拉框的标题，不设置就没有难看的标题了
        tabSp.setPrompt("类型");
        //设置下拉框的数组适配器
        tabSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        tabSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        tabSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = MeijuguaConstant.tabId[position]+"";
                if(initFlag){
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerYear(){
        String[] tabName = MeijuguaConstant.yearName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        yearSp = findViewById(R.id.search_year);
        //设置下拉框的标题，不设置就没有难看的标题了
        yearSp.setPrompt("年份");
        //设置下拉框的数组适配器
        yearSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        yearSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        yearSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchYear = MeijuguaConstant.yearId[position]+"";
                if(initFlag){
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerOrder(){
        String[] tabName = MeijuguaConstant.orderName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        orderSp = findViewById(R.id.search_order);
        //设置下拉框的标题，不设置就没有难看的标题了
        orderSp.setPrompt("排序");
        //设置下拉框的数组适配器
        orderSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        orderSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        orderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                orderBy = MeijuguaConstant.orderId[position]+"";
                if(initFlag){
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                    setDataList();
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
        String tabName = getIntent().getStringExtra("tabName");
        if(!TextUtils.isEmpty(title)){
            topTitle = title;
            txt_show_set_back.setText(title);
        }
        if(!TextUtils.isEmpty(tabName)){
            // search_tab.setText(tabName);
            for(int i = 0 ; i < MeijuguaConstant.tabName.length ; i++){
                if(StringUtils.equals(tabName,MeijuguaConstant.tabName[i])){
                    tabSp.setSelection(i);
                }
            }
        }
        String formType = getIntent().getStringExtra("categoryId");
        // Log.d(TAG,"categoryId = "+formType);
        if(!TextUtils.isEmpty(formType)){
            categoryId = formType;
        }
        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            // refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            if(loadFlag){
                pageIndex = pageIndex+1;
                setDataList();
                refreshlayout.finishLoadMore(5000);
            }else{
                refreshlayout.finishLoadMore(2000,true,true);
            }
        });
        setDataList();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        classAdapter = new MeijuguaVideoListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                Intent intent = new Intent(this, MeijuguaVideoDetailActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
                intent.putExtra("topTitle", clickBean.getTitle());
                intent.putExtra("url", clickBean.getLink());
                intent.putExtra("coverImage", clickBean.getCoverImage());
                intent.putExtra("title", clickBean.getTitle());
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
                    setDataList();
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


    private void setDataList() {
        try{
            //  Log.d(TAG,"返回 -page :" + page); // 56
            new Thread(() -> {
                List<VideoInfoBean> dataListTemp = new ArrayList<>();
                String searchtype = "5";

              /*  String searchRegionTemp = ""; // area
                if(!StringUtils.isEmpty(searchRegion)){
                    searchRegionTemp = URLEncoder.encode(searchRegion);
                }
                String searchTypeTemp = ""; // class
                if(!StringUtils.isEmpty(searchType)){
                    searchTypeTemp = URLEncoder.encode(searchType);
                }
                String searchLanguageTemp = ""; //lang
                if(!StringUtils.isEmpty(searchLanguage)){
                    searchLanguageTemp = URLEncoder.encode(searchLanguage);
                }
                String searchYearTemp = "";
                if(!StringUtils.isEmpty(searchYear)){
                    searchYearTemp = searchYear;
                }
                String searchOrderByTemp = "";
                if(!StringUtils.isEmpty(orderBy)){
                    searchOrderByTemp = orderBy;
                }*/
                //private String orderBy = "time"; // by
               // private String searchYear = "";  // year
               // private String searchRegion = "";
               // private String searchType = "";
               // private String searchLanguage = "";

                String apiUrl = "";
                if(StringUtils.isEmpty(searchword)){
                    // http://www.meijugua.com/type1/--2021---gold-1.html
                    apiUrl = MeijuguaConstant.getReqDomain() +"/%s/--%s---%s-%s.html";
                    apiUrl = String.format(apiUrl,categoryId,searchYear,orderBy,pageIndex);
                    //apiUrl = MeijuguaConstant.getReqDomain() +"/index.php/vod/type/id/%s/page/%s.html";
                   // apiUrl = String.format(apiUrl,categoryId,pageIndex);
                }else{
                    //String searchwordTemp = URLEncoder.encode(searchword);
                    String searchwordTemp = searchword;
                    apiUrl = MeijuguaConstant.getReqDomain() +"/search/search/%s/%s.html";
                    apiUrl = String.format(apiUrl,searchwordTemp,pageIndex);
                }
               // Log.d(TAG,"url = "+apiUrl);
                //apiUrl = "https://www.80yy.la/search.php?searchtype=&tid=2&area=%E5%A4%A7%E9%99%86&order=time&jq=%E5%89%A7%E6%83%85&yuyan=%E5%9B%BD%E8%AF%AD&page=1&year=&searchword=卿卿日常";
                try{
                    Document document = Jsoup.connect(apiUrl)
                            .userAgent(UserAgentUtils.getUserAgent())
                            .ignoreContentType(true).timeout(60000).get();
                    if(null == document){
                        return;
                    }
                 //   if(StringUtils.isEmpty(searchword)){
                        Elements elements = document.select("ul.itemtwo li");
                        for(Element element: elements) {
                            VideoInfoBean info =  new VideoInfoBean();
                            Element al = element.select("div.itemimg a").first();
                            String link = al.attr("href");
                            String img = al.select("img").first().attr("data-src");
                            String name = element.select(".itemname").first().text();
                            String actor = element.select("p.iteminfo").text();
                            String keywords = element.select("i.continu").text();
                            info.setTitle(name);
                            if(!StringUtils.isEmpty(link)){
                                if(link.startsWith("http")){
                                    info.setLink(link);
                                }else{
                                    info.setLink(MeijuguaConstant.getReqDomain()+link);
                                }
                            }
                            info.setAuthor(actor);
                            info.setKeywords(keywords);
                            if(!StringUtils.isEmpty(img)){
                                if(img.startsWith("http")){
                                    info.setCoverImage(img);
                                }else{
                                    info.setCoverImage(MeijuguaConstant.getReqDomain()+img);
                                }
                            }
                            info.setCategoryId(categoryId);
                            dataListTemp.add(info);
                        }
                 /*   }else{
                        Elements elements = document.select("ul.myui-vodlist__media li");
                        for(Element element: elements) {
                            VideoInfoBean info =  new VideoInfoBean();
                            Element al = element.select("a.myui-vodlist__thumb").first();
                            String link = al.attr("href");
                            String img = al.attr("data-original");
                            String name = al.attr("title");
                            String actor = al.text();
                            String keywords = ""; //element.select("span.pic-text").text();
                        *//*System.out.println(element);
                        System.out.println(link);
                        System.out.println(img);
                        System.out.println(name);
                        System.out.println(actor);
                        System.out.println(keywords);
                        System.out.println();*//*
                            info.setTitle(name);
                            info.setLink(MeijuguaConstant.getReqDomain()+link);
                            info.setAuthor(actor);
                            info.setKeywords(keywords);
                            info.setCoverImage(img);
                            info.setCategoryId(categoryId);
                            dataListTemp.add(info);
                        }
                    }
*/


                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(()->{
                    initFlag = true;
                    if(dataListTemp.size() > 0){
                        loadFlag = true;
                        if(pageIndex == 1){
                            videoInfoList = dataListTemp;
                            classAdapter.updateData(dataListTemp);
                        }else{
                            videoInfoList.addAll(dataListTemp);
                            classAdapter.addData(dataListTemp);
                        }
                    }else{
                       // Log.d(TAG,"--未找到相关内容--");
                        Toast.makeText(context,"--未找到相关内容--",Toast.LENGTH_LONG).show();
                    }
                });

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
