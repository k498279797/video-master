package com.vxiaokang.video.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.video.bean.CategoryBean;

import java.util.List;


/**
 * 工具
 */
public class LocalCateDataAdapter extends RecyclerView.Adapter<LocalCateDataAdapter.RecyclerViewHolder> {
    private String TAG = "LocalCateDataAdapter";
    private List<CategoryBean> mList;//数据源
    private Activity mActivity;
    private int currentPosition = 0;
    private OnItemClickListener onItemClickListener;//声明一下这个接口

    public LocalCateDataAdapter(Activity activity, List<CategoryBean> list) {
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
        View view = LayoutInflater.from(mActivity).inflate(R.layout.local_cate_item, null);
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
          //  Log.d(TAG,"名称："+(mList.get(position).getCategoryName()));
            holder.titleView.setText(mList.get(position).getCategoryName());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<CategoryBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<CategoryBean> list) {
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
        private LinearLayout itemContainer;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.item_templet_title);
            itemContainer = itemView.findViewById(R.id.templet_item_container);
        }
    }
}
