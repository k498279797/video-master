package com.vxiaokang.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.mbridge.msdk.thrid.okhttp.OkHttpClient;
import com.mbridge.msdk.thrid.okhttp.Request;
import com.mbridge.msdk.thrid.okhttp.Response;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.HistoryListActivity;
import com.vxiaokang.video.activity.video.shipin.ShipinVideoDetailActivity;
import com.vxiaokang.video.activity.video.shipin.ShipinVideoListActivity;
import com.vxiaokang.video.activity.video.shipin.bean.ShipinConstant;
import com.vxiaokang.video.adapter.IndexVideoAdapter;
import com.vxiaokang.video.bean.IndexVideoBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

/**
 * 首页
 */
public class HomeFrament extends Fragment implements View.OnClickListener{
    private String TAG = "HomeFrament";


    private List<IndexVideoBean>  dataList = new ArrayList<>();
    private IndexVideoAdapter indexVideoAdapter;
    private RecyclerView indexRecView;

    private Banner myBanner;
    private List<Object> imageUrlData = new ArrayList<Object>();
    private List<VideoInfoBean> bannerList = new ArrayList<>();

    private LinearLayout searchBtn;
    private ImageView history_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        initView(view);
        return view;
    }
    private void initView(View view) {
        searchBtn = view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);

        history_button = view.findViewById(R.id.history_button);
        history_button.setOnClickListener(this);
        //轮播
        initBanner(view);
        indexRecView = view.findViewById(R.id.index_item_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        indexVideoAdapter = new IndexVideoAdapter(getActivity(),dataList);
        indexRecView.setLayoutManager(gridLayoutManager);
        indexRecView.setAdapter(indexVideoAdapter);
        indexRecView.setFocusable(false);
        indexRecView.setNestedScrollingEnabled(false);
        indexVideoAdapter.setOnItemClickListener(new IndexVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int childPosition) {

                VideoInfoBean clickBean = dataList.get(position).getVideoList().get(childPosition);
                Intent intent = new Intent(getActivity(), ShipinVideoDetailActivity.class);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra("url", clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra("categoryId", "");
                startActivity(intent);
            }
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ShipinVideoListActivity.class);
                if(!StringUtils.isEmpty(dataList.get(position).getUrl())){
                    //Log.d(TAG,"link "+dataList.get(position).getUrl());
                    Log.d(TAG,"link "+dataList.get(position).getUrl());
                    if(dataList.get(position).getUrl().contains("dianying/index")){
                        intent.putExtra("categoryId", "1");
                        intent.putExtra("tabName", "电影");
                    }else if(dataList.get(position).getUrl().contains("dianshiju/index")){
                        intent.putExtra("categoryId", "2");
                        intent.putExtra("tabName", "电视剧");
                    }else  if(dataList.get(position).getUrl().contains("zongyi/index")){
                        intent.putExtra("categoryId", "3");
                        intent.putExtra("tabName", "综艺");
                    }else  if(dataList.get(position).getUrl().contains("dongman/index")){
                        intent.putExtra("categoryId", "4");
                        intent.putExtra("tabName", "动漫");
                    }else{
                        intent.putExtra("categoryId", "0");
                        intent.putExtra("tabName", "全部");
                    }
                    intent.putExtra("url", dataList.get(position).getUrl());
                    //intent.putExtra("url", "https://v.jsjinfu.com:8443/?v=https://v.qq.com/x/cover/mzc00200mp8vo9b/n0041aa087e.html");
                    intent.putExtra("title", dataList.get(position).getTitle());

                    startActivity(intent);
                }else{
                    intent.putExtra("categoryId", "0");
                    intent.putExtra("tabName", "全部");
                    startActivity(intent);
                }

            }
        });
        setDataList(ShipinConstant.getReqDomain());
        setDataList("https://www.88dyw.com");
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.searchBtn:
                intent = new Intent(getActivity(), ShipinVideoListActivity.class);
                intent.putExtra("categoryId", "0");
                intent.putExtra("tabName", "全部");
                startActivity(intent);
                break;
            case R.id.history_button:
                intent = new Intent(getActivity(), HistoryListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    //private String requestUrl = "https://www.pkmp4.com"; //https://www.88kan.tv
   // private String domain = "https://www.pkmp4.com";

   /* private SSLSocketFactory getSSLSocketFactory(){
        try {
            CertificateFactory cf;
            cf = CertificateFactory.getInstance("X.509");

            Certificate ca;
            InputStream cert = getContext().getResources().openRawResource(R.raw.cert);
            ca = cf.generateCertificate(cert);
            cert.close();

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            return sslContext.getSocketFactory();

        }
        catch (Exception e){
            return null;
        }
    }*/


    private void setDataList(String url) {
        try{
            new Thread(() -> {
                Document doc = null;
                Request request;
                Response response = null;
                try{
                   // Log.e(TAG,"setDataList = "+url);
                  //  doc = Jsoup.parse(new URL(url),6000);
                    doc = Jsoup.connect(url).timeout(60000).validateTLSCertificates(false).get();
                    Log.e(TAG,"setDataList  doc= "+doc);
                   /* OkHttpClient client = new OkHttpClient();
                    //建立一个Request对象，利用这个对象来发起http请求
                    request = new Request.Builder().url(url).build();
                    //newCall方法来创建一个call对象，调用他的execute（）方法来发送请求并且获取服务器的返回的数据
                    response = client.newCall(request).execute();
                    //得到具体的数据
                    String responseData = response.body().string();
                    Log.e(TAG,"setDataList responseData= "+responseData);*/
                }catch (Exception e){
                    Log.e(TAG,"setDataList = "+e.getMessage());
                    e.printStackTrace();
                    return;
                }finally {
                    //已经获取到了数据，我们需要关闭连接。
                    // close()是用来释放连接所占用的资源即释放BluetootheGatt的所有资源，只能使用BluetoothDevice.connectGatt()方法进行重新连接
                    if (response != null) {
                        try {
                            response.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(null == doc){
                    return;
                }
                List<IndexVideoBean>  dataListTemp = new ArrayList<>();

                //推荐
                List<VideoInfoBean> bannerListTemp = new ArrayList<>();
                Log.e(TAG,"setDataList = "+doc);
                Elements  bxList =  doc.select(".recommend_scroll_lst_bx li a");
                if(null != bxList) {
                    List<VideoInfoBean> tjTemp = new ArrayList<>();
                    for(Element bxitem : bxList) {
                        Element item = bxitem.select("img").first();
                        if(null != item) {
                            String url1 = bxitem.attr("href");
                            String title = bxitem.attr("title");
                            String imgUrl = item.attr("src");
                            VideoInfoBean bean = new VideoInfoBean();
                            bean.setTitle(title);
                            bean.setCoverImage(imgUrl);
                            bean.setLink(ShipinConstant.getReqDomain()+ url1);
                            bean.setDescription("");
                            tjTemp.add(bean);
                        }
                    }
                    if(null != tjTemp && tjTemp.size() > 1) {
                        bannerListTemp.addAll(tjTemp);
                    }
                }

                Elements recomendList =  doc.select("ul.recommend_lst li a");
                // System.out.println(recomendList);
                if(null != recomendList) {
                    List<VideoInfoBean> tjTemp = new ArrayList<>();
                    for(Element bxitem : recomendList) {
                        Element item = bxitem.select("img").first();
                        if(null != item) {
                            String url1 = bxitem.attr("href");
                            String title = bxitem.attr("title");
                            String tags = bxitem.text();
                            String imgUrl = item.attr("src");
                         //   System.out.println(url1);
                         //   System.out.println(title);
                         //   System.out.println(imgUrl);
                         //   System.out.println(tags);
                            VideoInfoBean bean = new VideoInfoBean();
                            bean.setTitle(title);
                            bean.setCoverImage(imgUrl);
                            bean.setLink(ShipinConstant.getReqDomain()+ url1);
                            bean.setDescription(tags);
                            tjTemp.add(bean);
                        }
                    }
                    if(null != tjTemp && tjTemp.size() > 0) {
                        IndexVideoBean info = new IndexVideoBean();
                        info.setTitle("推荐视频");
                       // info.setUrl(ShipinConstant.getReqDomain()+"/index.html");
                        info.setUrl("https://www.88dyw.com"+"/index.html");
                       /* if(tjTemp.size()%2 != 0){
                            info.setVideoList(tjTemp.subList(0,tjTemp.size()-1));
                        }else{*/
                            info.setVideoList(tjTemp);
                        //}
                        dataListTemp.add(info);
                    }
                }

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
                        bean.setLink(ShipinConstant.getReqDomain()+ url1);
                        bean.setDescription(tags);
                        tjTemp.add(bean);
                    }
                    IndexVideoBean info = new IndexVideoBean();
                    info.setTitle(cateTitle);
                    if(!StringUtils.isEmpty(cateUrl)){
                        info.setUrl(ShipinConstant.getReqDomain()+ cateUrl);
                    }
                    if(tjTemp.size()%2 != 0){
                        info.setVideoList(tjTemp.subList(0,tjTemp.size()-1));
                    }else{
                        info.setVideoList(tjTemp);
                    }
                    dataListTemp.add(info);
                }

                if(null != dataListTemp && dataListTemp.size() > 0){
                    getActivity().runOnUiThread(()->{
                        dataList = dataListTemp;
                        indexVideoAdapter.updateData(dataListTemp);
                    });
                }

                if(null != bannerListTemp && bannerListTemp.size() > 0){
                    getActivity().runOnUiThread(()->{
                        bannerList = bannerListTemp;
                        imageUrlData.clear();
                        List<String> imgList = new ArrayList<>();
                        for(VideoInfoBean info : bannerList){
                            Log.d(TAG,"----"+info.getCoverImage());
                            imgList.add(info.getCoverImage());
                            myBanner.update(imgList);
                        }
                    });
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
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
            if(null != bannerList){
                VideoInfoBean clickBean = bannerList.get(position);
                Intent intent = new Intent(getActivity(), ShipinVideoDetailActivity.class);
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra("url", clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra("categoryId", "");
                startActivity(intent);
            }
        });
    }

}
