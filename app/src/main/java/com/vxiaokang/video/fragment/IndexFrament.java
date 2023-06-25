package com.vxiaokang.video.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.kongzue.dialog.v2.SelectDialog;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.WebSiteViewActivity;
import com.vxiaokang.video.adapter.IndexVideoAdapter;
import com.vxiaokang.video.bean.IndexVideoBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 首页
 */
public class IndexFrament extends Fragment implements View.OnClickListener{
    private String TAG = "IndexFrament";

    private Banner myBanner;
    private List<Object> imageUrlData = new ArrayList<Object>();


    private List<IndexVideoBean>  dataList = new ArrayList<>();
    private IndexVideoAdapter indexVideoAdapter;
    private RecyclerView indexRecView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index, null);
        initView(view);
        return view;
    }
    private void initView(View view) {
        //轮播
        initBanner(view);

        indexRecView = view.findViewById(R.id.index_item_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        indexVideoAdapter = new IndexVideoAdapter(getActivity(),dataList);
        indexRecView.setLayoutManager(gridLayoutManager);
        indexRecView.setAdapter(indexVideoAdapter);
        indexRecView.setFocusable(false);
        indexRecView.setNestedScrollingEnabled(false);
       /* indexVideoAdapter.setOnItemClickListener(new IndexVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int childPosition) {
                SelectDialog.show(getContext(), "提示", "应用内打开或浏览器打开",
                        "应用内",(dialog, which) -> {
                            Intent intent = new Intent(getActivity(), WebSiteViewActivity.class);
                            Log.d(TAG,"link "+dataList.get(position).getVideoList().get(childPosition).getLink());
                            intent.putExtra("url", dataList.get(position).getVideoList().get(childPosition).getLink());
                            intent.putExtra("title", dataList.get(position).getVideoList().get(childPosition).getTitle());
                            startActivity(intent);
                        }, "浏览器", (dialog, which) -> {
                            Uri uri = Uri.parse(dataList.get(position).getVideoList().get(childPosition).getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }).setCanCancel(true);
            }
            @Override
            public void onItemClick(int position) {
                SelectDialog.show(getContext(), "提示", "应用内打开或浏览器打开",
                        "应用内",(dialog, which) -> {
                            Intent intent = new Intent(getActivity(), WebSiteViewActivity.class);
                            //Log.d(TAG,"link "+dataList.get(position).getUrl());
                            intent.putExtra("url", dataList.get(position).getUrl());
                            //intent.putExtra("url", "https://v.jsjinfu.com:8443/?v=https://v.qq.com/x/cover/mzc00200mp8vo9b/n0041aa087e.html");
                            intent.putExtra("title", dataList.get(position).getTitle());
                            startActivity(intent);
                        }, "浏览器", (dialog, which) -> {
                            Uri uri = Uri.parse(dataList.get(position).getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }).setCanCancel(true);
            }
        });*/

        indexVideoAdapter.setOnItemClickListener(new IndexVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int childPosition) {
                SelectDialog.show(getContext(), "提示", "应用内打开或浏览器打开",
                        "应用内",(dialog, which) -> {
                            Intent intent = new Intent(getActivity(), WebSiteViewActivity.class);
                            Log.d(TAG,"link "+dataList.get(position).getVideoList().get(childPosition).getLink());
                            intent.putExtra("url", dataList.get(position).getVideoList().get(childPosition).getLink());
                            intent.putExtra("title", dataList.get(position).getVideoList().get(childPosition).getTitle());
                            startActivity(intent);
                        }, "浏览器", (dialog, which) -> {
                            Uri uri = Uri.parse(dataList.get(position).getVideoList().get(childPosition).getLink());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }).setCanCancel(true);
            }
            @Override
            public void onItemClick(int position) {
                SelectDialog.show(getContext(), "提示", "应用内打开或浏览器打开",
                        "应用内",(dialog, which) -> {
                            Intent intent = new Intent(getActivity(), WebSiteViewActivity.class);
                            //Log.d(TAG,"link "+dataList.get(position).getUrl());
                            Log.d(TAG,"link "+dataList.get(position).getUrl());
                            intent.putExtra("url", dataList.get(position).getUrl());
                            //intent.putExtra("url", "https://v.jsjinfu.com:8443/?v=https://v.qq.com/x/cover/mzc00200mp8vo9b/n0041aa087e.html");
                            intent.putExtra("title", dataList.get(position).getTitle());
                            startActivity(intent);
                        }, "浏览器", (dialog, which) -> {
                            Uri uri = Uri.parse(dataList.get(position).getUrl());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }).setCanCancel(true);
            }
        });
        setDataList("https://www.88ysw.com");
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){

        }
    }
    /***
     * 轮播
     */
    private void initBanner(View view) {
        myBanner = view.findViewById(R.id.banner);
        if(imageUrlData.isEmpty()){
            imageUrlData.add(R.mipmap.banner);
            imageUrlData.add(R.mipmap.banner1);
            imageUrlData.add(R.mipmap.banner2);
        }
      //  imageUrlData.add("https://i.xddzshop.com/20220731/c57e197180941406b7278fb75e98d977.jps");
        myBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        myBanner.setImageLoader(new GlideImageLoader());
        myBanner.setImages(imageUrlData);
        myBanner.setBannerAnimation(Transformer.Default);
        myBanner.setDelayTime(2000); //切换频率
        myBanner.isAutoPlay(true);  //自动启动
        myBanner.setIndicatorGravity(BannerConfig.CENTER);  //位置设置
        myBanner.start();  //开始运行
        myBanner.setOnBannerListener(position -> {

        });
    }

    //private String requestUrl = "https://www.pkmp4.com"; //https://www.88kan.tv
   // private String domain = "https://www.pkmp4.com";
    private String requestUrl = "https://www.88ysw.com"; //https://www.88kan.tv
    private String domain = "https://www.88ysw.com";

    private void setDataList(String url) {
        try{
            new Thread(() -> {
                Document doc = null;
                try{
                    doc = Jsoup.connect(url).timeout(60000).get();
                }catch (Exception e){
                    e.printStackTrace();
                    return;
                }
                if(null == doc){
                    return;
                }
                List<IndexVideoBean>  dataListTemp = new ArrayList<>();
                Element mainE = doc.select("body>.wrap").first();
                if(null == mainE){
                    return;
                }
              //  Log.e("index ==",mainE.toString());
                Elements modElements = mainE.select("div.row");
                for(Element mod: modElements){
                    List<VideoInfoBean> tjTemp = new ArrayList<>();
                    Element tha = mod.select(".stit").first();
                    if(null == tha){
                        continue;
                    }
                    String cateTitle = tha.select(".title").first().text();
                    String cateUrl = tha.select(".title").first().attr("href");
                   // Log.d(TAG,"cateTitle = "+cateTitle);
                   // Log.d(TAG,"cateUrl = "+cateUrl);
                    Elements liElements = mod.select("div.li_li");
                    for(Element item : liElements){
                        Element aE = item.select("a").first();
                        String url1 = aE.attr("href");
                        Element imgE = aE.select("img").first();
                        String imgUrl = imgE.attr("src");
                        String title = item.select(".name").first().text();
                        String tags = item.select(".actor").first().text();
                       // Log.d(TAG,"url = "+domain+url);
                       // Log.d(TAG,"title = "+title);
                       // Log.d(TAG,"imgUrl = "+imgUrl);
                      //  Log.d(TAG,"tags = "+tags);
                      //  Log.d(TAG,"---------------- ");
                        VideoInfoBean bean = new VideoInfoBean();
                        bean.setTitle(title);
                        bean.setCoverImage(imgUrl);
                        bean.setLink(domain+ url1);
                        bean.setDescription(tags);
                        tjTemp.add(bean);
                    }
                    IndexVideoBean info = new IndexVideoBean();
                    info.setTitle(cateTitle);
                    if(!StringUtils.isEmpty(cateUrl)){
                        info.setUrl(domain+ cateUrl);
                    }
                    info.setVideoList(tjTemp);
                    dataListTemp.add(info);
                }

                if(null != dataListTemp && dataListTemp.size() > 0){
                    getActivity().runOnUiThread(()->{
                        dataList = dataListTemp;
                        indexVideoAdapter.updateData(dataListTemp);
                    });
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
