package com.vxiaokang.video.activity.video.tube;

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
import com.vxiaokang.video.activity.video.kdy.KdyVideoDetailPlayActivity;
import com.vxiaokang.video.activity.video.tube.bean.TubeConstant;
import com.vxiaokang.video.adapter.kdy.KdyVideoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.HttpUrl;

/****
 * 列表
 */
public class TubeVideoListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "GaytubeVideoListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private KdyVideoListDataAdapter classAdapter;
    private int pageIndex = 1;
    private boolean loadFlag = true;
    private String topTitle = "";
    private String searchword = "";
    // private TextView search_tab;

    private ImageView search_button;
    private EditText search_edit;

    private String pageCategoryId = "/videos/index/";

    private String categoryId = "/videos/index/";
    private String orderBy = "";
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
        setContentView(R.layout.activity_tube_video_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        context = this;
        initSpinnerTab(); //分类
       // initSpinnerRegion(); //地区
       // initSpinnerType();//剧情
       // initSpinnerLanguage();//语言
        initSpinnerOrder();//标签
        initSpinnerYear();//线路
        initView();
    }


    private void initSpinnerRegion(){
        String[] tabName = TubeConstant.regionName;
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
                searchRegion = TubeConstant.regionId[position]+"";
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
        String[] tabName = TubeConstant.typeName;
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
                searchType = TubeConstant.typeId[position]+"";
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
        String[] tabName = TubeConstant.languageName;
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
                searchLanguage = TubeConstant.languageId[position]+"";
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
        String[] tabName = TubeConstant.tabName;
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
                categoryId = TubeConstant.tabId[position]+"";
                pageIndex = 1;
                orderBy = "";
                searchword = "";
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
        String[] tabName = TubeConstant.yearName;
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
               String  routLine = TubeConstant.yearId[position]+"";
               if(!StringUtils.isEmpty(routLine)){
                   TubeConstant.setReqDomain(routLine);
               }
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
        String[] tabName = TubeConstant.orderName;
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
                orderBy = TubeConstant.orderId[position]+"";
                if(initFlag){
                    pageIndex = 1;
                    searchword = "";
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
            for(int i = 0 ; i < TubeConstant.tabName.length ; i++){
                if(StringUtils.equals(tabName,TubeConstant.tabName[i])){
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
        classAdapter = new KdyVideoListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                //Intent intent = new Intent(this, KdyVideoDetailActivity.class);
                Intent intent = new Intent(this,TubeVideoDetailPlayActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
                intent.putExtra("topTitle", clickBean.getTitle());
                intent.putExtra("url", clickBean.getLink());
                intent.putExtra("coverImage", clickBean.getCoverImage());
                intent.putExtra("sourceTitle", clickBean.getTitle());
                intent.putExtra("sourceUrl", clickBean.getLink());
                intent.putExtra("keywords", clickBean.getKeywords());
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
                //https://vip.aqdz13.com/videos/category/cn/1
                String apiUrl = "";
                if(!StringUtils.isEmpty(searchword)){
                    // /videos/search?key=
                    apiUrl = TubeConstant.getReqDomain() +"/videos/search?key=%s&page=%s";
                    apiUrl = String.format(apiUrl,searchword,pageIndex);
                }else{
                    apiUrl = TubeConstant.getReqDomain() +"%s%s";
                    apiUrl = String.format(apiUrl,categoryId,pageIndex);
                    /*if(!StringUtils.isEmpty(orderBy)){
                        apiUrl = TubeConstant.getReqDomain() +"%s";
                        apiUrl = String.format(apiUrl,orderBy);
                    }else{
                        apiUrl = TubeConstant.getReqDomain() +"%s";
                        apiUrl = String.format(apiUrl,categoryId);
                    }*/
                }


               /* if(pageIndex > 1){
                    apiUrl = TubeConstant.getReqDomain() +"%s%s";
                    apiUrl = String.format(apiUrl,pageCategoryId,pageIndex);
                }else{
                    if(!StringUtils.isEmpty(searchword)){
                        // /videos/search?key=
                        apiUrl = TubeConstant.getReqDomain() +"/videos/search?key="+"%s";
                        apiUrl = String.format(apiUrl,searchword);
                    }else{
                        if(!StringUtils.isEmpty(orderBy)){
                            apiUrl = TubeConstant.getReqDomain() +"%s";
                            apiUrl = String.format(apiUrl,orderBy);
                        }else{
                            apiUrl = TubeConstant.getReqDomain() +"%s";
                            apiUrl = String.format(apiUrl,categoryId);
                        }
                    }
                }*/
               Log.d(TAG,"url = "+apiUrl);
                try{

                   /* OkHttpUtils.get().url(apiUrl).build().execute(new StringCallback(){

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.d(TAG,"response onError= "+e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.d(TAG,"response = "+response);
                        }
                    });*/
                    apiUrl = "https://vip.aqdw195.com/?f=vip.aqdw130.com";
                    Document document = Jsoup.connect(apiUrl)
                            .userAgent(UserAgentUtils.getUserAgent())
                            //.userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                            //.followRedirects(true)
                            .ignoreContentType(true).timeout(60000).get();
                    String urlTemp = document.location();
                    Log.d(TAG,"urlTemp = "+urlTemp);
                    if(null == document){
                        return;
                    }
                        Elements elements = document.select("div.videos-item");
                        for(Element element: elements) {
                            VideoInfoBean info =  new VideoInfoBean();
                            Element aElement = element.select("a.thumbnail-cover-link").first();
                            String link = aElement.attr("href");
                            String name = aElement.attr("alt");
                            String img = element.select("img.videos-thumbnail").first().attr("data-original");
                            String actor = element.select("span.video-duration").first().text();
                            String keywords = element.select("span.metadata-view").first().text(); //element.select("span.pic-text").text();
                            info.setTitle(name);
                            info.setLink(TubeConstant.getReqDomain()+link);
                            info.setAuthor(actor);
                            info.setKeywords(keywords);
                            if(!StringUtils.isEmpty(img)){
                                if(img.startsWith("http")){
                                    info.setCoverImage(img);
                                }else{
                                    info.setCoverImage(TubeConstant.getReqDomain()+img);
                                }
                            }
                            info.setCategoryId(categoryId);
                            dataListTemp.add(info);
                        }

                }catch (Exception e){
                    Log.e(TAG,"setDataList = "+e.getMessage());
                    e.printStackTrace();
                }
                runOnUiThread(()->{
                    initFlag = true;
                    Log.d(TAG,"url = "+dataListTemp.size());
                    if(dataListTemp.size() > 0){
                        loadFlag = true;
                        if(pageIndex == 1){
                            Log.d(TAG,"新增");
                            videoInfoList = dataListTemp;
                            classAdapter.updateData(dataListTemp);
                        }else{
                            Log.d(TAG,"更新");
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
