package com.vxiaokang.video.activity.video.frament;

import android.content.Intent;
import android.os.Bundle;
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
import com.vxiaokang.video.activity.video.huang.HuangVideoListActivity;
import com.vxiaokang.video.adapter.HuangCateVideoAdapter;
import com.vxiaokang.video.bean.IndexVideoBean;
import com.vxiaokang.video.bean.VideoInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class HuangCateFrament extends Fragment implements View.OnClickListener{
    private String TAG = "HuangCateFrament";

    private List<IndexVideoBean>  dataList = new ArrayList<>();
    private HuangCateVideoAdapter indexVideoAdapter;
    private RecyclerView indexRecView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index_huang, null);
        initView(view);
        return view;
    }
    private void initView(View view) {
        if(dataList.isEmpty()){
            String json = ResourceUtils.readAssets2String("data/huang_item.json","utf-8");
            if(!StringUtils.isEmpty(json)){
                dataList = new Gson().fromJson(json,new TypeToken<List<IndexVideoBean>>(){}.getType());
            }
        }

        indexRecView = view.findViewById(R.id.index_item_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        indexVideoAdapter = new HuangCateVideoAdapter(getActivity(),dataList);
        indexRecView.setLayoutManager(gridLayoutManager);
        indexRecView.setAdapter(indexVideoAdapter);
        indexRecView.setFocusable(false);
        indexRecView.setNestedScrollingEnabled(false);
        indexVideoAdapter.setOnItemClickListener(new HuangCateVideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, int childPosition) {
                //详情
               // Intent intent = new Intent(getActivity(), HuangVideoDetailActivity.class);
                IndexVideoBean mianBean = dataList.get(position);
               /* VideoInfoBean clickBean = mianBean.getVideoList().get(childPosition);
                intent.putExtra("topTitle", mianBean.getTitle());
                intent.putExtra(Constants.sourceUrl, clickBean.getLink());
                intent.putExtra(Constants.coverImage, clickBean.getCoverImage());
                intent.putExtra(Constants.sourceTitle, clickBean.getTitle());
                intent.putExtra(Constants.sourceViews, clickBean.getUpVote());
                intent.putExtra(Constants.sourceDuration, clickBean.getAuthor());
                intent.putExtra(Constants.sourceItemId, mianBean.getSourceId());
                intent.putExtra(Constants.sourceId, clickBean.getVideoId());
                startActivity(intent);*/
                VideoInfoBean clickBean = mianBean.getVideoList().get(childPosition);
                Intent intent = new Intent(getActivity(), HuangVideoListActivity.class);
                intent.putExtra("categoryId",StringUtils.isEmpty(clickBean.getCategoryId()) ? clickBean.getVideoId() : clickBean.getCategoryId());
                //intent.putExtra("categoryId",clickBean.getVideoId());
                intent.putExtra("title",clickBean.getTitle());
                startActivity(intent);
            }
            @Override
            public void onItemClick(int position) {
                //更多
                /*Intent intent = new Intent(getActivity(), HuangVideoListActivity.class);
                intent.putExtra("categoryId",dataList.get(position).getSourceId());
                intent.putExtra("title",dataList.get(position).getTitle());
                startActivity(intent);*/
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){

        }
    }

}
