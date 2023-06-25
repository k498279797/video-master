package com.vxiaokang.video.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.ClassListDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.AdBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.bean.req.ReqGetAdListBean;
import com.vxiaokang.video.bean.req.ReqVideoBean;
import com.vxiaokang.video.bean.resp.RespAdBean;
import com.vxiaokang.video.bean.resp.RespVideoBean;
import com.vxiaokang.video.request.RequestGetAdList;
import com.vxiaokang.video.request.RequestGetVideo;
import com.vxiaokang.video.util.EmptyUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;

/****
 * 课程类别列表
 */
public class ClassListActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClassListActivity";
    private TextView txt_show_set_back; //标题
    private ImageView ivBack;

    private RecyclerView classRecView;
    private List<VideoInfoBean> videoInfoList = new ArrayList<>();
    private ClassListDataAdapter classAdapter;
    private String currentFromType = "index_class";
    private int pageIndex = 1;
    private int pageSize = 10;
    private int total = 0;
    private int totalPage = 1;
    private boolean loadFlag = true;
    private String categoryId = "";
    private boolean showMore = true;
    private PromptDialog promptDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView() {
        promptDialog = new PromptDialog(this);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        txt_show_set_back = findViewById(R.id.txt_show_set_back);
        classRecView = findViewById(R.id.class_rec_view);

        String title = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(title)){
            txt_show_set_back.setText(title);
        }
        String formType = getIntent().getStringExtra("formType");
        if(!TextUtils.isEmpty(title)){
            currentFromType = formType;
        }

        setDataList(currentFromType);
       // VideoInfoBean bean1 = new VideoInfoBean();
       // bean1.setTitle("必胜篇：马高兵胜炮单象");
       // bean1.setCoverImage("http://119.23.211.203/images/chess/introduction/6.png");
       // bean1.setUpVote("23541");
       // videoInfoList.add(bean1);
       // videoInfoList.add(bean1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        classAdapter = new ClassListDataAdapter(this,videoInfoList);
        classRecView.setLayoutManager(gridLayoutManager);
        classRecView.setAdapter(classAdapter);
        classRecView.setFocusable(false);
        classRecView.setNestedScrollingEnabled(false);
        classRecView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int currentPos = gridLayoutManager.findLastVisibleItemPosition();
                Log.d(TAG,"currentPos = "+currentPos);
                Log.d(TAG,"videoInfoList = size "+videoInfoList.size());
                // mPagedList.loadAround(lastPos);//触发Android Paging的加载事务逻辑。
                if(currentPos > 0  && currentPos+1 == videoInfoList.size()){
                    if(loadFlag && totalPage > pageIndex ){
                        pageIndex = pageIndex+1;
                        setVideoList(categoryId);
                        loadFlag = false;
                    }else if(loadFlag && pageIndex >= totalPage){
                        if(showMore){
                            promptDialog.showInfo("没有更多数据...");
                            showMore = false;
                        }
                    }
                }
            }
        });
        classAdapter.setOnItemClickListener(position -> {
            try{
               /* Intent intent = new Intent(this, ClassDetailActivity.class);
                VideoInfoBean clickBean = videoInfoList.get(position);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceViews, clickBean.getUpVote());
                intent.putExtra(Constants.sourceDuration, clickBean.getDuration());
                intent.putExtra(Constants.sourceItemId, clickBean.getCategoryId());
                startActivity(intent);*/
                getVideoHtml();
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

    public void setDataList(String code){
        ReqGetAdListBean params = new ReqGetAdListBean();
        params.setCode(code);
        RequestGetAdList.getData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //  promptDialog.dismiss();
                Logger.e(TAG,"网络错误:" + e.getMessage());
            }
            @Override
            public void onResponse(String response, int id) {
                //  promptDialog.dismiss();
                Logger.d(TAG,"返回:" + response);
                RespAdBean bean = new Gson().fromJson(response, RespAdBean.class);
                if (null != bean && "0".equals(bean.getCode())){
                    if(null != bean.getData()){
                        AdBean adBean = (AdBean)bean.getData();
                        if(EmptyUtils.isNotEmpty(adBean.getItemsId())){
                           categoryId = adBean.getItemsId();
                            setVideoList(categoryId);
                        }
                    }
                }
            }
        });
    }

    public void setVideoList(String categoryId){
        try{
            promptDialog.showLoading("加载中...");
            ReqVideoBean params = new ReqVideoBean();
            params.setCategoryId(categoryId);
            params.setPageSize(String.valueOf(pageSize));
            params.setPageIndex(String.valueOf(pageIndex));
            RequestGetVideo.getData(params, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    promptDialog.dismiss();
                    Log.d(TAG,"TAG 网络错误:" + e.getMessage());
                }
                @Override
                public void onResponse(String response, int id) {
                    promptDialog.dismiss();
                    Log.d(TAG,"返回:" + response);
                    RespVideoBean bean = new Gson().fromJson(response, RespVideoBean.class);
                    if (null != bean && "0".equals(bean.getCode())){
                        if(EmptyUtils.isNotEmpty(bean.getTotal()) && pageIndex == 1){
                            total = Integer.valueOf(bean.getTotal());
                            totalPage = total/pageSize + (total%pageSize > 0 ? 1 : 0);
                            Log.d(TAG,"返回 -total :" + total);
                            Log.d(TAG,"返回 -totalPage :" + totalPage);
                        }
                        loadFlag = true;
                        if(null != bean.getRows()){
                            if(pageIndex == 1){
                                videoInfoList = bean.getRows();
                                classAdapter.updateData(bean.getRows());
                            }else{
                                videoInfoList.addAll(bean.getRows());
                                classAdapter.addData(bean.getRows());
                            }
                        }
                    }else{
                        promptDialog.showInfo("没有更多数据...");
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            promptDialog.dismiss();
        }
    }

    private void getVideoHtml(){
        try {
            Log.d(TAG,"getVideoHtml----");
            Document doc = (Document) Jsoup.connect("https://www.px6080.net").timeout(60000).get();//解析html
            Elements links = doc.select("ul[class=w_newslistpage_list]");//获取li标签且class为w_newslistpage_list的标签
            for (Element link : links) {
                Elements li = link.select("li");//查找li标签
                for (Element element : li) {//遍历
                    Elements select = element.select("a[title]");//查找a标签且带有title属性的标签
                    if (select!=null&&select.size()>0){
                        String linkHref = select.get(0).attr("href");//获取href值
                        String linkText = select.get(0).text();//获取text
                        //System.out.println("爬虫结果 1 -->  " + linkHref +linkText);
                    }
                    Elements select1 = link.select("span[class=date]");//获取span标签且class为date的标签
                    if (select1!=null&&select1.size()>0){
                        String date = select1.get(0).text();
                        //System.out.println("爬虫结果 2--> " + date);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
