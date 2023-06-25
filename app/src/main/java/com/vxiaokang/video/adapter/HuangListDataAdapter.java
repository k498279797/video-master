package com.vxiaokang.video.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.vxiaokang.video.R;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;

import java.util.List;


/**
 * 列表
 */
public class HuangListDataAdapter extends RecyclerView.Adapter<HuangListDataAdapter.RecyclerViewHolder> {

    private List<VideoInfoBean> mList;//数据源
    private Activity mActivity;
    private int currentPosition = -1;
    private OnItemClickListener onItemClickListener;//声明一下这个接口

    public HuangListDataAdapter(Activity activity, List<VideoInfoBean> list) {
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
        //将我们自定义的item布局R.layout.item_one转换为View
        View view = LayoutInflater.from(mActivity).inflate(R.layout.huang_item, null);
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
            String adType  = mList.get(position).getAdType();
            if(StringUtils.equals(adType,"1")){
                holder.class_item_top_container.setVisibility(View.VISIBLE);
                holder.class_item_main_container.setVisibility(View.GONE);
            }else{
                holder.class_item_top_container.setVisibility(View.GONE);
                holder.class_item_main_container.setVisibility(View.VISIBLE);

                holder.titleView.setText(mList.get(position).getTitle());
                String times  = mList.get(position).getAuthor();
                String views  = mList.get(position).getUpVote();
                String coverImage  = mList.get(position).getCoverImage();
                holder.timesView.setText("时长："+times);
                holder.viewsView.setText("播放："+views);
                if(position == currentPosition){
                    if(null != mActivity){
                        Glide.with(mActivity).load(R.mipmap.mv_play_on).into(holder.imageView);
                    }
                }else{
                    if(null != mActivity) {
                        Glide.with(mActivity).load(R.mipmap.mv_play).into(holder.imageView);
                    }
                }
                if(!StringUtils.isEmpty(coverImage)){
                    if(null != mActivity) {
                       /* Glide.with(mActivity).load(coverImage).placeholder(R.mipmap.error)
                                .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                                .error(R.mipmap.error).into(holder.coverImageView);*/

                        GlideNetWorkImageUtils.showNetworkImage(mActivity,coverImage,holder.coverImageView);
                    }
                }
            }
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
        private TextView titleView;
        private ImageView imageView;
        private TextView viewsView;
        private TextView timesView;
        private ImageView coverImageView;
        private LinearLayout itemContainer;
        private FrameLayout class_item_top_container;
        private LinearLayout class_item_main_container;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.class_item_title);
            viewsView = itemView.findViewById(R.id.class_item_views);
            timesView = itemView.findViewById(R.id.class_item_times);
            imageView = itemView.findViewById(R.id.class_item_img);
            coverImageView = itemView.findViewById(R.id.class_item_coverImage);
            itemContainer = itemView.findViewById(R.id.class_item_container);
            class_item_top_container = itemView.findViewById(R.id.class_item_top_container);
            class_item_main_container = itemView.findViewById(R.id.class_item_main_container);
        }
    }
}
