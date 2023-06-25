package com.vxiaokang.video.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.blankj.utilcode.util.SPUtils;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.WebViewActivity;
import com.vxiaokang.video.constants.ConfigKey;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 用户协议 
 */
public class YsDialog extends Dialog {

    public static void showYsDialog(Context context,YsDialogListener listener) {
        String mYs = SPUtils.getInstance().getString("ysxy", "0");
        if (!mYs.equals("1")) {
            YsDialog infoDialog = new Builder(context,listener).create();
            infoDialog.show();
        }else{
            if(null != listener){
                listener.success();
            }
        }
    }

    private YsDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private View mLayout;
        private YsDialogListener mlistener;
        private TextView mClose;
        private TextView mFinish;
        private TextView mDescribe;

        private YsDialog mDialog;
        private Context mContext;
        private PromptDialog promptDialog;

        public Builder(Context context,YsDialogListener listener) {
            mlistener =  listener;
            mContext = context;
            promptDialog = new PromptDialog((Activity) mContext);
            mDialog = new YsDialog(context, R.style.Theme_AppCompat_Dialog);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            mLayout = inflater.inflate(R.layout.dialog_yinsi, null, false);
            //添加布局文件到 Dialog
            mDialog.addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            mClose = mLayout.findViewById(R.id.tv_close);
            mFinish = mLayout.findViewById(R.id.tv_finish);
            mDescribe = mLayout.findViewById(R.id.tv_desc);
        }


        public YsDialog create() {
            mClose.setOnClickListener(view -> {
                mDialog.dismiss();
                SPUtils.getInstance().put("ysxy", "1");
                if(null != mlistener){
                    mlistener.success();
                }
            });
            mFinish.setOnClickListener(view -> {
                mDialog.dismiss();
                if(null != mlistener){
                    mlistener.error();
                }
            });
           // mDescribe.setText("尊敬的用户欢迎使用来电秀！我们非常重视您的个人信息和隐私保护。在您使用本应用之前，请仔细阅读并同意《隐私政策》和《用户协议》，我们将严格按照您同意的各项条款使用您的个人信息，以便为您提供更好的服务；\n\n1.我们会遵循隐私政策收集、使用信息，但不会仅因同意本隐私政策而采用强制捆绑的方式收集信息；\n\n2.在仅浏览时，为保障服务所必需，我们会收集设备信息与日志信息用于视频和信息推送；\n\n3.通讯录、GPS、麦克风、相册权限均不会默认开启，只有经过明示授权才会在为实现功能或服务时使用，不会在功能或服务不需要时收集信息。\n\n详情请点击并仔细查看");
            mDescribe.setText("欢迎使用本产品！\n\n请您先阅读并了解《用户协议》和《隐私政策》我们将严格按照上述协议为您提供服务，保护您的信息安全，点击同意表示您已阅读并同意全部条款，可以继续使用我们的产品和服务。\n\n详情请查看");
            mDialog.setContentView(mLayout);
            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
            SpannableString ysSpan = new SpannableString("《隐私政策》");

            SpannableString xySpan = new SpannableString("《用户协议》");
            ysSpan.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                   // ds.setColor(mContext.getResources().getColor(R.color.text_privacy));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("configKey", ConfigKey.APP_PRIVACY_URL);
                    intent.putExtra("title", "隐私政策");
                    mContext.startActivity(intent);
                }
            }, 0, ysSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            xySpan.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                  //  ds.setColor(mContext.getResources().getColor(R.color.text_protocol));//设置颜色
                    ds.setUnderlineText(false);//去掉下划线
                }

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("configKey", ConfigKey.APP_PROTOCOL_URL);
                    intent.putExtra("title", "用户协议");
                    mContext.startActivity(intent);
                }
            }, 0, ysSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mDescribe.append(ysSpan);
            mDescribe.append("和");
            mDescribe.append(xySpan);
            mDescribe.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
            return mDialog;
        }
    }

    public interface YsDialogListener {
        void success();
        void error();
    }
}
