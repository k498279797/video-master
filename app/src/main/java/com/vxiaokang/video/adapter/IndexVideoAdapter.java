package com.vxiaokang.video.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.vxiaokang.video.R;
import com.vxiaokang.video.bean.AdMaterialBean;
import com.vxiaokang.video.bean.IndexVideoBean;
import com.vxiaokang.video.util.EmptyUtils;

import java.util.List;

/**
 * 首页
 */
public class IndexVideoAdapter extends RecyclerView.Adapter<IndexVideoAdapter.RecyclerViewHolder> {

    private List<IndexVideoBean> mList;//数据源
    private Activity mActivity;
    private OnItemClickListener onItemClickListener;//声明一下这个接口
    private int currentPosition = 0;
    private IndexVideoChildAdapter mItemRecyclerViewAdapter;


    public IndexVideoAdapter(Activity activity, List<IndexVideoBean> list) {
       // Log.d("TAG--",list.size()+"");
        mActivity = activity;
        mList = list;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将我们自定义的item布局R.layout.item_one转换为View
        //View view = LayoutInflater.from(mActivity).inflate(R.layout.index_class_item, parent, null);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.index_video_item, null);
        //将view传递给我们自定义的ViewHolder
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        //返回这个MyHolder实体
          try{
             holder.more_layout.setOnClickListener(v -> {
                  currentPosition = holder.getAdapterPosition();
                  if (onItemClickListener != null) {
                      onItemClickListener.onItemClick(currentPosition);
                  }
                  notifyDataSetChanged();
              });
          }catch (Exception e){}

        return  holder;
    }
    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        try{
            holder.titleView.setText(mList.get(position).getTitle());
            holder.index_video_rec_view.setHasFixedSize(true);
            holder.index_video_rec_view.setLayoutManager(new GridLayoutManager(mActivity, 3,
                    GridLayoutManager.VERTICAL, false));
            mItemRecyclerViewAdapter = new IndexVideoChildAdapter(mActivity,mList.get(position).getVideoList(),position);
            holder.index_video_rec_view.setAdapter(mItemRecyclerViewAdapter);
            if(StringUtils.isEmpty(mList.get(position).getUrl())){
                holder.imageView.setVisibility(View.GONE);
            }else{
                holder.imageView.setVisibility(View.VISIBLE);
            }
            drawRecyclerView();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    /**
     * RecyclerView 内层点击事件方法
     */
    private void drawRecyclerView() {
        //RecyclerView点击事件
        mItemRecyclerViewAdapter.setOnItemClickListener((parentPosition, childPostion) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(parentPosition,childPostion);
            }
        });
    }

    public void updateData(List<IndexVideoBean> list) {
       try{
           mList.clear();
           mList.addAll(list);
           notifyDataSetChanged();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    //获取数据源总的条数
    @Override
    public int getItemCount() {

        return mList.size();
    }

    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position, int childPosition);
        void onItemClick(int position);
    }

    /**
     * 自定义的ViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;
        private RecyclerView index_video_rec_view;
        private LinearLayout itemContainer;
        private FrameLayout more_layout;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.class_item_title);
            imageView = itemView.findViewById(R.id.img_more_hot);
            index_video_rec_view = itemView.findViewById(R.id.index_video_rec_view);
            itemContainer = itemView.findViewById(R.id.class_item_container);
            more_layout = itemView.findViewById(R.id.more_layout);
        }
    }
}
