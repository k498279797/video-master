package com.vxiaokang.video.util.commons;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.vxiaokang.video.R;

public class GlideNetWorkImageUtils {
    // 加载并显示网络图片
    public static void showNetworkImage(Context context, String mImageUrl, ImageView view) {
        // 构建一个加载网络图片的建造器
        RequestBuilder<Drawable> builder = Glide.with(context).load(mImageUrl);
        RequestOptions options = new RequestOptions(); // 创建Glide的请求选项
//        options.disallowHardwareConfig(); // 关闭硬件加速，防止过大尺寸的图片加载报错
        options.skipMemoryCache(true); // 是否跳过内存缓存（但不影响硬盘缓存）
       // options.override(300, 200); // 设置图片的宽高
       // if (ck_seize.isChecked()) { // 勾选了占位图
            options.placeholder(R.mipmap.default_img); // 设置加载开始的占位图
        //}
       // if (ck_error.isChecked()) { // 勾选了出错图
            options.error(R.mipmap.error); // 设置发生错误的提示图
      //  }
       // if (ck_original.isChecked()) { // 勾选了原始图
        //    options.override(Target.SIZE_ORIGINAL); // 展示原始图片
       // }
       // if (ck_transition.isChecked()) { // 勾选了渐变动画
            builder.transition(DrawableTransitionOptions.withCrossFade(3000)); // 设置时长3秒的渐变动画
       // }
       // if (mCacheStrategy == 0) { // 自动选择缓存策略
            options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); // 设置指定的缓存策略
       /* } else if (mCacheStrategy == 1) { // 不缓存图片
            options.diskCacheStrategy(DiskCacheStrategy.NONE); // 设置指定的缓存策略
        } else if (mCacheStrategy == 2) { // 只缓存原始图片
            options.diskCacheStrategy(DiskCacheStrategy.DATA); // 设置指定的缓存策略
        } else if (mCacheStrategy == 3) { // 只缓存压缩后的图片
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE); // 设置指定的缓存策略
        } else if (mCacheStrategy == 4) { // 同时缓存原始图片和压缩图片
            options.diskCacheStrategy(DiskCacheStrategy.ALL); // 设置指定的缓存策略
        }*/
        // 在图像视图上展示网络图片。apply方法表示启用指定的请求选项
        builder.apply(options).into(view);
    }


    // 加载并显示网络图片
    public static void showNetworkImage(Activity activity, String mImageUrl, ImageView view) {
        // 构建一个加载网络图片的建造器
        RequestBuilder<Drawable> builder = Glide.with(activity).load(mImageUrl);
        RequestOptions options = new RequestOptions(); // 创建Glide的请求选项
//        options.disallowHardwareConfig(); // 关闭硬件加速，防止过大尺寸的图片加载报错
        options.skipMemoryCache(true); // 是否跳过内存缓存（但不影响硬盘缓存）
      //  options.override(300, 200); // 设置图片的宽高
        // if (ck_seize.isChecked()) { // 勾选了占位图
        options.placeholder(R.mipmap.default_img); // 设置加载开始的占位图
        //}
        // if (ck_error.isChecked()) { // 勾选了出错图
        options.error(R.mipmap.error); // 设置发生错误的提示图
        //  }
        // if (ck_original.isChecked()) { // 勾选了原始图
        //    options.override(Target.SIZE_ORIGINAL); // 展示原始图片
        // }
        // if (ck_transition.isChecked()) { // 勾选了渐变动画
        builder.transition(DrawableTransitionOptions.withCrossFade(3000)); // 设置时长3秒的渐变动画
        // }
        // if (mCacheStrategy == 0) { // 自动选择缓存策略
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); // 设置指定的缓存策略
       /* } else if (mCacheStrategy == 1) { // 不缓存图片
            options.diskCacheStrategy(DiskCacheStrategy.NONE); // 设置指定的缓存策略
        } else if (mCacheStrategy == 2) { // 只缓存原始图片
            options.diskCacheStrategy(DiskCacheStrategy.DATA); // 设置指定的缓存策略
        } else if (mCacheStrategy == 3) { // 只缓存压缩后的图片
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE); // 设置指定的缓存策略
        } else if (mCacheStrategy == 4) { // 同时缓存原始图片和压缩图片
            options.diskCacheStrategy(DiskCacheStrategy.ALL); // 设置指定的缓存策略
        }*/
        // 在图像视图上展示网络图片。apply方法表示启用指定的请求选项
        builder.apply(options).into(view);
    }
}
