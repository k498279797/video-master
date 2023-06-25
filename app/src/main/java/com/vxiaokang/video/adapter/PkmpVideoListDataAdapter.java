package com.vxiaokang.video.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.vxiaokang.video.R;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;

import java.util.List;


/**
 * 列表
 */
public class PkmpVideoListDataAdapter extends RecyclerView.Adapter<PkmpVideoListDataAdapter.RecyclerViewHolder> {

    private List<VideoInfoBean> mList;//数据源
    private Activity mActivity;
    private int currentPosition = 0;
    private OnItemClickListener onItemClickListener;//声明一下这个接口

    public PkmpVideoListDataAdapter(Activity activity, List<VideoInfoBean> list) {
     //   Log.d("TAG--",list.size()+"");
        mActivity = activity;
        mList = list;
    }
    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Log.d("TAG--","onCreateViewHolder");
        //将我们自定义的item布局R.layout.item_one转换为View
        //View view = LayoutInflater.from(mActivity).inflate(R.layout.index_class_item, parent, null);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pkmp_video_item, null);
        //将view传递给我们自定义的ViewHolder
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        //返回这个MyHolder实体
       try{
           holder.itemContainer.setOnClickListener(v -> {
               currentPosition = holder.getAdapterPosition();
               if (onItemClickListener != null) {
                   onItemClickListener.onItemClick(currentPosition);
               }
               notifyDataSetChanged();
           });
       }catch (Exception e){
           e.printStackTrace();
       }
        return holder;
    }
    //通过方法提供的ViewHolder，将数据绑定到ViewHolder中
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        try{
            if(TextUtils.isEmpty(mList.get(position).getCoverImage())){
                if(null != mActivity) {
                    Glide.with(mActivity).load(R.mipmap.default_img).into(holder.imageView);
                }
            }else{
                if(null != mActivity) {
                   /* Glide.with(mActivity).load(mList.get(position).getCoverImage()).placeholder(R.mipmap.error)
                            .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                            .error(R.mipmap.error).into(holder.imageView);*/

                    GlideNetWorkImageUtils.showNetworkImage(mActivity,mList.get(position).getCoverImage(),holder.imageView);
                }
            }
            holder.titleView.setText(mList.get(position).getTitle());
            holder.class_item_actor.setText(mList.get(position).getAuthor());
            holder.class_item_times.setText("评分："+mList.get(position).getUpVote());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<VideoInfoBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<VideoInfoBean> list) {
       // mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    /**
     * 自定义的ViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;
        private TextView viewsView;
        private TextView class_item_times;
        private TextView class_item_actor;
        private LinearLayout itemContainer;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.class_item_img);
            titleView = itemView.findViewById(R.id.class_item_title);
            viewsView = itemView.findViewById(R.id.class_item_views);
            class_item_times = itemView.findViewById(R.id.class_item_times);
            class_item_actor = itemView.findViewById(R.id.class_item_actor);
            itemContainer = itemView.findViewById(R.id.class_item_container);
        }
    }
}
