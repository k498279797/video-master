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
public class HuangIndexVideoChildAdapter extends RecyclerView.Adapter<HuangIndexVideoChildAdapter.RecyclerViewHolder> {

    private List<VideoInfoBean> mList;//数据源
    private Activity mActivity;
    private int currentPosition = 0;
    private OnItemClickListener onItemClickListener;//声明一下这个接口
    private int mParentPositon = 0;

    public HuangIndexVideoChildAdapter(Activity activity, List<VideoInfoBean> list, int parentPositon) {
        //Log.d("TAG--",list.size()+"");
        mActivity = activity;
        mList = list;
        mParentPositon = parentPositon;
    }
    //提供setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //创建ViewHolder并返回，后续item布局里控件都是从ViewHolder中取出
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.d("TAG--","onCreateViewHolder");
        //将我们自定义的item布局R.layout.item_one转换为View
        //View view = LayoutInflater.from(mActivity).inflate(R.layout.index_class_item, parent, null);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.huang_child_video_item, null);
        //将view传递给我们自定义的ViewHolder
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        //返回这个MyHolder实体
       try{
           holder.itemContainer.setOnClickListener(v -> {
               currentPosition = holder.getAdapterPosition();
               if (onItemClickListener != null) {
                   onItemClickListener.onItemClick(mParentPositon,currentPosition);
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
      //  holder.setIsRecyclable(false);
        try{
            if(TextUtils.isEmpty(mList.get(position).getCoverImage())){
                if(null != mActivity) {
                    Glide.with(mActivity).load(R.mipmap.default_img).into(holder.imageView);
                }
            }else{
                //Glide.with(mActivity).load(R.mipmap.default_img).into(holder.imageView);
                if(null != mActivity) {
                 /*   Glide.with(mActivity).load(mList.get(position).getCoverImage()).placeholder(R.mipmap.error)
                            .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                            .error(R.mipmap.error).into(holder.imageView);*/
                    GlideNetWorkImageUtils.showNetworkImage(mActivity,mList.get(position).getCoverImage(),holder.imageView);

                }
            }
            holder.titleView.setText(mList.get(position).getTitle());
            if(!StringUtils.isEmpty(mList.get(position).getAuthor())){
                holder.updateView.setText(mList.get(position).getAuthor());
            }else{
                holder.updateView.setVisibility(View.GONE);
            }
            if(!StringUtils.isEmpty(mList.get(position).getUpVote())){
                holder.rateView.setText(mList.get(position).getUpVote());
            }else{
                holder.rateView.setVisibility(View.GONE);
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
        void onItemClick(int position, int childPosition);
    }
    /**
     * 自定义的ViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;
        private TextView rateView;
        private TextView updateView;
        private LinearLayout itemContainer;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.video_item_img);
            titleView = itemView.findViewById(R.id.video_item_title);
            rateView = itemView.findViewById(R.id.video_item_rate);
            updateView = itemView.findViewById(R.id.video_item_update_text);
            itemContainer = itemView.findViewById(R.id.video_item_container);
        }
    }
}
