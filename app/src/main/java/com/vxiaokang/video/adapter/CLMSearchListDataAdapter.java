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
import com.vxiaokang.video.R;
import com.vxiaokang.video.bean.VideoInfoBean;
import com.vxiaokang.video.component.JustifyTextView;

import java.util.List;


/**
 * 磁力猫
 */
public class CLMSearchListDataAdapter extends RecyclerView.Adapter<CLMSearchListDataAdapter.RecyclerViewHolder> {

    private List<VideoInfoBean> mList;//数据源
    private Activity mActivity;
    private int currentPosition = 0;
    private OnItemClickListener onItemClickListener;//声明一下这个接口

    public CLMSearchListDataAdapter(Activity activity, List<VideoInfoBean> list) {
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
        View view = LayoutInflater.from(mActivity).inflate(R.layout.cml_search_list_item, null);
        //将view传递给我们自定义的ViewHolder
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        //返回这个MyHolder实体
       try{
           holder.copyContainer.setOnClickListener(v -> {
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
            holder.titleView.setText(mList.get(position).getTitle()+"");
            holder.descView.setText(mList.get(position).getDescription()+"");
            holder.contentView.setText(mList.get(position).getKeywords()+"");
            holder.linkView.setText(mList.get(position).getLink()+"");
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
        private JustifyTextView titleView;
        private JustifyTextView descView;
        private JustifyTextView contentView;
        private JustifyTextView linkView;
        private LinearLayout itemContainer;
        private LinearLayout copyContainer;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.item_title_txt);
            descView = itemView.findViewById(R.id.item_desc_txt);
            contentView = itemView.findViewById(R.id.item_content_txt);
            linkView = itemView.findViewById(R.id.item_link_txt);
            itemContainer = itemView.findViewById(R.id.class_item_container);
            copyContainer = itemView.findViewById(R.id.item_copy_layout);
        }
    }
}
