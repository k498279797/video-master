package com.vxiaokang.video.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.vxiaokang.video.R;
import com.vxiaokang.video.entity.PlayRecord;
import com.vxiaokang.video.util.DateUtils;
import com.vxiaokang.video.util.commons.GlideNetWorkImageUtils;

import java.util.List;


/**
 * 历史记录
 */
public class HistroyListDataAdapter extends RecyclerView.Adapter<HistroyListDataAdapter.RecyclerViewHolder> {
    private String TAG = "HistroyListDataAdapter";
    private List<PlayRecord> mList;//数据源
    private Activity mActivity;
    private int currentPosition = 0;
    private OnItemClickListener onItemClickListener;//声明一下这个接口

    public HistroyListDataAdapter(Activity activity, List<PlayRecord> list) {
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
        View view = LayoutInflater.from(mActivity).inflate(R.layout.history_video_item, null);
        //将view传递给我们自定义的ViewHolder
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        //返回这个MyHolder实体
        try{
            holder.class_item_del.setOnClickListener(v -> {
                int holderPosition = holder.getAdapterPosition();
                AttachPopupView attachPopupView = new XPopup.Builder(mActivity)
                        .hasShadowBg(false)
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{ "查看", "删除记录"},null, (position, text) -> {
                            if(position == 0){
                                onItemClickListener.onItemClick(holderPosition,0);
                            }else if(position == 1){
                                onItemClickListener.onItemClick(holderPosition,1);
                            }
                        }, 0, 0);
                attachPopupView.show();
            });
        }catch (Exception e){
            e.printStackTrace();
        }

       try{
           holder.itemContainer.setOnClickListener(v -> {
               currentPosition = holder.getAdapterPosition();
               if (onItemClickListener != null) {
                   onItemClickListener.onItemClick(currentPosition,0);
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
      //  Log.d("TAG---class", String.valueOf(position));
        try{
            if(TextUtils.isEmpty(mList.get(position).getCoverImage())){
                if(null != mActivity) {
                    Glide.with(mActivity).load(R.mipmap.default_img).into(holder.imageView);
                }
            }else{
                if(null != mActivity) {
                  /*  Glide.with(mActivity).load(mList.get(position).getCoverImage()).placeholder(R.mipmap.error)
                            .priority(Priority.IMMEDIATE)//指定加载的优先级，优先级越高越优先加载
                            .error(R.mipmap.error).into(holder.imageView);*/

                    GlideNetWorkImageUtils.showNetworkImage(mActivity,mList.get(position).getCoverImage(),holder.imageView);
                }
            }
            holder.titleView.setText(mList.get(position).getTitle());
            holder.tagsView.setText(mList.get(position).getKeywords());
            holder.class_item_time.setText(DateUtils.dataToString(mList.get(position).getUpdate(),"yyyy-MM-dd HH:mm"));
           // String views  = mList.get(position).getUpVote();
           /* if(EmptyUtils.isEmpty(views)){
                views = "0";
            }
            holder.viewsView.setText(views+"次观看");*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<PlayRecord> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<PlayRecord> list) {
       // mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    /**
     * 定义RecyclerView选项单击事件的回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position, int type);
    }
    /**
     * 自定义的ViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView class_item_del;
        private TextView titleView;
        private TextView class_item_time;
        private TextView tagsView;
        private LinearLayout itemContainer;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.class_item_img);
            class_item_del = itemView.findViewById(R.id.class_item_del);
            titleView = itemView.findViewById(R.id.class_item_title);
            class_item_time = itemView.findViewById(R.id.class_item_time);
            tagsView = itemView.findViewById(R.id.class_item_tags);
            itemContainer = itemView.findViewById(R.id.class_item_container);
        }
    }
}
