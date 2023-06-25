package com.vxiaokang.video.activity.video.frament;

import android.content.Intent;
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

import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.huang.HuangConstant;
import com.vxiaokang.video.activity.video.huang.HuangVideoDetailActivity;
import com.vxiaokang.video.activity.video.huang.HuangVideoListActivity;
import com.vxiaokang.video.activity.video.bean.ResultBean;
import com.vxiaokang.video.activity.video.bean.ResultItemBean;
import com.vxiaokang.video.activity.video.bean.ResultItemDetailBean;
import com.vxiaokang.video.adapter.HuangIndexVideoAdapter;
import com.vxiaokang.video.bean.IndexVideoBean;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.constants.Constants;
import com.vxiaokang.video.util.codec.Base64;

import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class HuangIndexFrament extends Fragment implements View.OnClickListener{
    private String TAG = "HuangIndexFrament";

    private List<IndexVideoBean>  dataList = new ArrayList<>();
    private HuangIndexVideoAdapter indexVideoAdapter;
    private RecyclerView indexRecView;
    private String mCategoryId = "3";

    public static HuangIndexFrament newInstance(String categoryId) {
        HuangIndexFrament fragment = new HuangIndexFrament();
        Bundle args = new Bundle();
        args.putString("categoryId", String.valueOf(categoryId));
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index_huang, null);
        if (getArguments() != null) {
            mCategoryId = getArguments().getString("categoryId");
        }
        initView(view);
        return view;
    }
    private void initView(View view) {
        indexRecView = view.findViewById(R.id.index_item_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        indexVideoAdapter = new HuangIndexVideoAdapter(getActivity(),dataList);
        indexRecView.setLayoutManager(gridLayoutManager);
        indexRecView.setAdapter(indexVideoAdapter);
        indexRecView.setFocusable(false);
        indexRecView.setNestedScrollingEnabled(false);
        indexVideoAdapter.setOnItemClickListener(new HuangIndexVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int childPosition) {
                //详情
                Intent intent = new Intent(getActivity(), HuangVideoDetailActivity.class);
                IndexVideoBean mianBean = dataList.get(position);
                VideoInfoBean clickBean = mianBean.getVideoList().get(childPosition);
                intent.putExtra("topTitle", mianBean.getTitle());
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceViews, clickBean.getUpVote());
                intent.putExtra(Constants.sourceDuration, clickBean.getAuthor());
                intent.putExtra(Constants.sourceItemId, mianBean.getSourceId());
                intent.putExtra(Constants.sourceId, clickBean.getVideoId());
                startActivity(intent);
            }
            @Override
            public void onItemClick(int position) {
                //更多
                Intent intent = new Intent(getActivity(), HuangVideoListActivity.class);
                intent.putExtra("categoryId",dataList.get(position).getSourceId());
                intent.putExtra("title",dataList.get(position).getTitle());
                startActivity(intent);
            }
        });
        initData();//本地数据
        loadData();//在线数据
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){

        }
    }

    private void loadData() {
        try{
            new Thread(() -> {
                List<IndexVideoBean>  ret_dataListTemp = new ArrayList<>();
                //String apiUrl = "https://app.rk5ck5dzx.com/api/visualization/get?uid=2&tab_id=%s&d_device=app&v_code=252";
                String apiUrl = "https://"+ HuangConstant.getReqDomain() +"/api/visualization/get?uid=2&tab_id=%s&d_device=app&v_code=252";
                apiUrl = String.format(apiUrl,mCategoryId);
                String string = "";
                try{
                    String document = Jsoup.connect(apiUrl).header("Content-Type", "application/json;charset=UTF-8")
                            .ignoreContentType(true)
                            .timeout(120000).get().text();
                    string = new String(Base64.decodeBase64(document));
                    Log.d(TAG,"c="+mCategoryId);
                    Log.d(TAG,"c="+string);
                    Type type  = new TypeToken<ResultBean>(){}.getType();
                    ResultBean result = new Gson().fromJson(string,type);
                    if(null != result && null != result.getData() && result.getData().size() > 0) {
                        List<ResultItemBean> dataList = result.getData();
                        for(ResultItemBean itemBean : dataList) {
                            if(StringUtils.equals(itemBean.getType(), "horizontalChartSmall")
                             || StringUtils.equals(itemBean.getType(),"columnHorizontalSlide")
                             || StringUtils.equals(itemBean.getType(),"columnBarChart61")
                             || StringUtils.equals(itemBean.getType(),"columnVerticalSlide")) {
                                IndexVideoBean indexInfo = new IndexVideoBean();
                                indexInfo.setTitle(itemBean.getName());
                                indexInfo.setDesc(itemBean.getDesc());
                                indexInfo.setSourceId(itemBean.getSourceId());
                                List<VideoInfoBean> ret_infoListTemp = new ArrayList<>();
                                for(ResultItemDetailBean data : itemBean.getData()) {
                                    VideoInfoBean ret_info = new VideoInfoBean();
                                    ret_info.setVideoId(data.getId());
                                    ret_info.setTitle(data.getName());
                                    ret_info.setCoverImage(data.getImage());
                                    ret_info.setLink(data.getMedia());
                                    ret_info.setAuthor(data.getTime_len());
                                    ret_info.setUpVote(data.getPlays());
                                    ret_info.setCategoryId(itemBean.getSourceId());
                                    ret_infoListTemp.add(ret_info);
                                }
                                if(null != ret_infoListTemp && ret_infoListTemp.size() > 0){
                                    indexInfo.setVideoList(ret_infoListTemp);
                                    ret_dataListTemp.add(indexInfo);
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    Log.e(TAG,"JSON-result-- 为空....."+e.getMessage());
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(()->{
                    if(null != ret_dataListTemp && ret_dataListTemp.size() > 0){
                        dataList = ret_dataListTemp;
                        indexVideoAdapter.updateData(ret_dataListTemp);
                    }
                });

            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initData(){
        try{
            List<IndexVideoBean>  ret_dataListTemp = new ArrayList<>();
            String document = ResourceUtils.readAssets2String("data/cate/categoryId_"+mCategoryId+".txt","utf-8");
            if(StringUtils.isEmpty(document)){
                return;
            }
            String string = new String(Base64.decodeBase64(document));
            Type type  = new TypeToken<ResultBean>(){}.getType();
            ResultBean result = new Gson().fromJson(string,type);
            if(null != result && null != result.getData() && result.getData().size() > 0) {
                List<ResultItemBean> dataList = result.getData();
                for(ResultItemBean itemBean : dataList) {
                    if(StringUtils.equals(itemBean.getType(), "horizontalChartSmall")
                            || StringUtils.equals(itemBean.getType(),"columnHorizontalSlide")
                            || StringUtils.equals(itemBean.getType(),"columnBarChart61")
                            || StringUtils.equals(itemBean.getType(),"columnVerticalSlide")) {
                        IndexVideoBean indexInfo = new IndexVideoBean();
                        indexInfo.setTitle(itemBean.getName());
                        indexInfo.setDesc(itemBean.getDesc());
                        indexInfo.setSourceId(itemBean.getSourceId());
                        List<VideoInfoBean> ret_infoListTemp = new ArrayList<>();
                        for(ResultItemDetailBean data : itemBean.getData()) {
                            VideoInfoBean ret_info = new VideoInfoBean();
                            ret_info.setVideoId(data.getId());
                            ret_info.setTitle(data.getName());
                            ret_info.setCoverImage(data.getImage());
                            ret_info.setLink(data.getMedia());
                            ret_info.setAuthor(data.getTime_len());
                            ret_info.setUpVote(data.getPlays());
                            ret_info.setCategoryId(itemBean.getSourceId());
                            ret_infoListTemp.add(ret_info);
                        }
                        if(null != ret_infoListTemp && ret_infoListTemp.size() > 0){
                            indexInfo.setVideoList(ret_infoListTemp);
                            ret_dataListTemp.add(indexInfo);
                        }
                    }
                }
            }
            getActivity().runOnUiThread(()->{
                if(null != ret_dataListTemp && ret_dataListTemp.size() > 0){
                    dataList = ret_dataListTemp;
                    indexVideoAdapter.updateData(ret_dataListTemp);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
