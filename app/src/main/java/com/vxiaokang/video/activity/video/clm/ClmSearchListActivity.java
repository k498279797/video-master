package com.vxiaokang.video.activity.video.clm;

import android.content.ClipboardManager;
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

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.kongzue.dialog.v2.BottomMenu;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.clm.bean.CLMConstant;
import com.vxiaokang.video.activity.video.shipin.ShipinVideoDetailActivity;
import com.vxiaokang.video.activity.video.shipin.bean.ShipinConstant;
import com.vxiaokang.video.adapter.CLMSearchListDataAdapter;
import com.vxiaokang.video.adapter.ShipinVideoListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.EmptyUtils;
import com.vxiaokang.video.util.KeyBoardUtils;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/****
 * 磁力猫
 */
public class ClmSearchListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClmSearchListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private CLMSearchListDataAdapter classAdapter;
    private int pageIndex = 1;
    private boolean loadFlag = true;
    private String searchword = "色戒";

    private ImageView search_button;
    private EditText search_edit;
    /// https://clm292.buzz
    private String categoryId = "https://clm300.buzz";
    private String searchType = "1";
    private String searchOrder= "3";

    private Spinner tabSp;
    private Spinner typeSp;
    private Spinner orderSp;
    private boolean initFlag = false;
    private ArrayAdapter<String> linesAdapter;
    private String[] linesTabName = CLMConstant.getReqTabName();
    private String[] linesTabValue = CLMConstant.getReqTabValue();
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clm_search_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        context = this;
        initSpinnerTab();//初始路由
        initSpinnerType();//类型
        initSpinnerOrder();//排序
        initView();
    }

    private void initSpinnerTab(){
        //声明一个下拉列表的数组适配器
        linesAdapter = new ArrayAdapter<String>(this,R.layout.item_select,linesTabName);
        //设置数组适配器的布局样式
        linesAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        tabSp = findViewById(R.id.search_tab);
        //设置下拉框的标题，不设置就没有难看的标题了
        tabSp.setPrompt("路由");
        //设置下拉框的数组适配器
        tabSp.setAdapter(linesAdapter);
        //设置下拉框默认的显示第一项
        tabSp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        tabSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(initFlag){
                    categoryId = linesTabValue[position];
                    CLMConstant.setReqLineDomain(categoryId);
                    setDataList();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        linesDataList();
    }

    private void initSpinnerType(){
        String[] tabName = CLMConstant.typeName;
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,tabName);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        typeSp = findViewById(R.id.search_type);
        //设置下拉框的标题，不设置就没有难看的标题了
        typeSp.setPrompt("类型");
        //设置下拉框的数组适配器
        typeSp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        typeSp.setSelection(1);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchType =  CLMConstant.typeValue[position];
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
        String[] tabName = CLMConstant.orderName;
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
                searchOrder = CLMConstant.orderValue[position];
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
        classRecView = findViewById(R.id.class_rec_view);

        String formType = getIntent().getStringExtra("categoryId");
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        classAdapter = new CLMSearchListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classAdapter.setOnItemClickListener(position -> {
            try{
                VideoInfoBean bean = videoInfoList.get(position);
                clipboardContent(bean.getLink());
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
    public void clipboardContent(String content){
        try{
            if(EmptyUtils.isNotEmpty(content)){
                ClipboardManager cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(content); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
                String apiUrl = "";
                String searchString = "";
                if(StringUtils.isEmpty(searchword)){
                    searchString = URLEncoder.encode("色戒");
                }else{
                    searchString = URLEncoder.encode(searchword);
                }
                apiUrl = CLMConstant.getReqLineDomain() +"/search-%s-%s-%s-%s.html";
                apiUrl = String.format(apiUrl,searchString,searchType,searchOrder,pageIndex);
                Log.d(TAG,"apiUrl = "+apiUrl);
                //apiUrl = "https://clm292.buzz/search-色戒-0-0-1.html";
                try{
                    Document document = Jsoup.connect(apiUrl).ignoreContentType(true).timeout(60000).get();
                    if(null == document){
                        return;
                    }
                    Element pages = document.select(".pager span").first();
                    //System.out.println(pages.text());
                    Elements ssboxs = document.select(".ssbox");
                    for(Element box : ssboxs) {
                        VideoInfoBean info =  new VideoInfoBean();
                       // System.out.println(box);
                        String title = box.select(".title").first().text();
                        String desc = box.select("ul").first().text();
                        String sbar = box.select(".sbar").first().text();
                        String href = box.select(".sbar a").first().attr("href");

                        info.setTitle(title);
                        info.setLink(href);
                        info.setDescription(desc);
                        info.setKeywords(sbar);
                        // info.setUpVote(data.getPlays());
                        dataListTemp.add(info);
                     //   System.out.println(title);
                      //  System.out.println(desc);
                       // System.out.println(sbar);
                       // System.out.println(href);
                    }
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
                            Toast.makeText(App.getContext(),"--未找到相关内容--",Toast.LENGTH_LONG).show();
                        }
                    });

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void linesDataList(){
        try {
            new Thread(() -> {
                String url = "http://www.磁力猫.com";
                List<String> dataListTemp = new ArrayList<>();
               try{
                   Document document = Jsoup.connect(url).timeout(60000).get();
                   //System.out.println(doc2);
                   if(null == document){
                       return;
                   }
                   Elements al = document.select("ul li a");
                   for(Element itElement : al) {
                       String hrefUrl = itElement.attr("href");
                       if(!StringUtils.isEmpty(hrefUrl) && hrefUrl.startsWith("http")) {
                           // System.out.println(hrefUrl);
                           dataListTemp.add(hrefUrl);
                       }
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
                if(dataListTemp.size() >0){
                    runOnUiThread(()->{
                        JSONArray array = new JSONArray(dataListTemp);
                        String[] arr = dataListTemp.toArray(new String[dataListTemp.size()]);
                        linesTabName = arr;
                        linesTabValue = arr;
                        categoryId = dataListTemp.get(0);
                        CLMConstant.setReqTabName(array.toString());
                        CLMConstant.setReqTabValue(array.toString());
                        CLMConstant.setReqLineDomain(dataListTemp.get(0));
                        linesAdapter.notifyDataSetChanged();
                    });
                }
            }).start();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}
