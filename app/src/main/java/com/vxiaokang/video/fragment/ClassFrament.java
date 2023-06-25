package com.vxiaokang.video.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.tool.AudioView;
import com.vxiaokang.video.adapter.ToolsDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.ToolsBean;
import com.vxiaokang.video.fragment.Utlis.RotateAnimation;
import com.vxiaokang.video.fragment.bean.ToolsBeanUtils;
import com.vxiaokang.video.util.MediaMusicPlayer;
import com.vxiaokang.video.util.MusicPlayer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 工具
 */
public class ClassFrament extends Fragment implements View.OnClickListener {
    private String TAG = "ClassFrament";
    private RecyclerView item_rec_view;
    private List<ToolsBean> dataList = new ArrayList<>();
    private ToolsDataAdapter toolsDataAdapter;
    private MusicPlayer mMusic;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_class, null);
        ButterKnife.bind(getActivity());
        initView(view);
        return view;
    }

    private void initView(View view) {
        View mybar = view.findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,getContext());
        if(null == dataList || dataList.size() == 0){
            dataList = ToolsBeanUtils.converData();
        }



        // 初始化
        mMusic = MusicPlayer.getInstance(getActivity()) ;

        item_rec_view = view.findViewById(R.id.class_rec_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        toolsDataAdapter = new ToolsDataAdapter(getActivity(),dataList);
        item_rec_view.setLayoutManager(gridLayoutManager);
        item_rec_view.setAdapter(toolsDataAdapter);
        item_rec_view.setFocusable(false);
        item_rec_view.setNestedScrollingEnabled(false);
        toolsDataAdapter.setOnItemClickListener(position -> {
            ToolsBean info = dataList.get(position);
            if(null != info.getClazz()){
                Intent intent = new Intent(getActivity(), info.getClazz());
                intent.putExtra("url", dataList.get(position).getUrl());
                intent.putExtra("title", dataList.get(position).getTitle());
                startActivity(intent);
            }else{
                if(StringUtils.equals(info.getType(),"10")){
                    mMusic.play(2,0,1) ;
                }else if(StringUtils.equals(info.getType(),"12")){
                    mMusic.play(2,-1,0.5f) ;
                }else if(StringUtils.equals(info.getType(),"13")){
                    mMusic.play(2,-1,1f) ;
                }else if(StringUtils.equals(info.getType(),"14")){
                    mMusic.play(2,-1,1.5f) ;
                }else if(StringUtils.equals(info.getType(),"15")){
                    mMusic.play(2,-1,2f) ;
                }else if(StringUtils.equals(info.getType(),"16")){
                    MediaMusicPlayer.getInstance(getContext()).play(1,true);
                }else if(StringUtils.equals(info.getType(),"17")){
                    MediaMusicPlayer.getInstance(getContext()).play(2,false);
                }
            }
        });
    }

    /**
     *
     * @param context
     * @param filePath  文件夹路径
     */
    public List<Map<String, Object>> getAssetsImg(Context context,String filePath){
        List<Map<String, Object>> cateList = new ArrayList<>();
        String[] list_image = null;
        try {
            list_image = context.getAssets().list(filePath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for(int i=0 ; i < list_image.length;++i){
            InputStream open = null;
            try {
                String temp = filePath+"/"+list_image[i];
                open = context.getAssets().open(temp);
                Bitmap bitmap = BitmapFactory.decodeStream(open);
                Map<String, Object> map = new HashMap<>();
                map.put("name", list_image[i]);
                Log.d(TAG,"file name"+list_image[i]);
                map.put("iv", bitmap);
                map.put("cate_id",i);
                cateList.add(map);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (open != null) {
                    try {
                        open.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return cateList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
             default:
                 break;
        }
    }

}
