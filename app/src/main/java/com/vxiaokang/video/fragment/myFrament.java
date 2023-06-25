package com.vxiaokang.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.util.SPUtils;
import com.vxiaokang.video.BuildConfig;
import com.vxiaokang.video.R;
import com.vxiaokang.video.activity.FeedBackActivity;
import com.vxiaokang.video.activity.HistoryListActivity;
import com.vxiaokang.video.activity.ToolsActivity;
import com.vxiaokang.video.activity.WebViewActivity;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.config.InitAdConfig;
import com.vxiaokang.video.constants.ConfigKey;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 个人中心
 */
public class myFrament extends Fragment implements View.OnClickListener{
    private String TAG = "myFrament";
    private LinearLayout myLineClear;  //清除缓存
    private LinearLayout myLinePolicy;  //隐私政策
    private LinearLayout myLineAgreement;  //用户协议
    private LinearLayout myLineFeedback;  //意见反馈
    private LinearLayout myLineContact;  //联系我们
    private LinearLayout myLineUpdate;  //版本更新
    private LinearLayout myLineQQ;  //版本更新
    private LinearLayout myLineHistory;  //历史记录
    private LinearLayout my_line_tools;  //历史记录
    private PromptDialog promptDialog;
    private TextView txt_app_version;
    private SwitchCompat switcher_debug;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
       // StatusBarUtil.transparencyBar(getActivity());


        initView(view);
        return view;
    }
    private void initView(View view) {
        promptDialog = new PromptDialog(getActivity());
        promptDialog.setViewAnimDuration(800);

        View mybar = view.findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,getContext());

        myLineClear = view.findViewById(R.id.my_line_clear);
        myLinePolicy = view.findViewById(R.id.my_line_policy);
        myLineAgreement = view.findViewById(R.id.my_line_agreement);
        myLineFeedback = view.findViewById(R.id.my_line_feedback);
        myLineContact = view.findViewById(R.id.my_line_contact);
        myLineUpdate = view.findViewById(R.id.my_line_update);
        myLineQQ = view.findViewById(R.id.my_line_qq);
        myLineHistory = view.findViewById(R.id.my_line_history);
        my_line_tools = view.findViewById(R.id.my_line_tools);

        myLineClear.setOnClickListener(this);
        myLinePolicy.setOnClickListener(this);
        myLineAgreement.setOnClickListener(this);
        myLineFeedback.setOnClickListener(this);
        myLineContact.setOnClickListener(this);
        myLineUpdate.setOnClickListener(this);
        myLineQQ.setOnClickListener(this);
        myLineHistory.setOnClickListener(this);
        my_line_tools.setOnClickListener(this);

        switcher_debug = view.findViewById(R.id.switcher_debug);
        if(InitAdConfig.isDebug()){
            switcher_debug.setChecked(true);
        }else{
            switcher_debug.setChecked(false);
        }
        switcher_debug.setOnClickListener(v -> {
            if(switcher_debug.isChecked()){
                SPUtils.getInstance().put(ConfigKey.APP_DEBUG,"1");
            }else{
                SPUtils.getInstance().put(ConfigKey.APP_DEBUG,"0");
            }
        });


        txt_app_version = view.findViewById(R.id.txt_app_version);
        txt_app_version.setText("当前版本 ："+ BuildConfig.VERSION_NAME+"-"+ SPUtils.getInstance().getString(ConfigKey.APP_CHANNEL_NO));
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.my_line_clear :
                promptDialog.showLoading("清理中...");
                new Handler().postDelayed(() -> {
                    //  Toasty.success(getContext(),"清理成功").show();
                    promptDialog.showSuccess("清理成功");
                }, 2 * 1000);
                break;
            case R.id.my_line_policy :
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("configKey", ConfigKey.APP_PRIVACY_URL);
                intent.putExtra("title", "隐私政策");
                startActivity(intent);
                break;
            case R.id.my_line_agreement :
                intent = new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("configKey", ConfigKey.APP_PROTOCOL_URL);
                intent.putExtra("title", "用户协议");
                startActivity(intent);
                break;
            case R.id.my_line_feedback :
                intent = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.my_line_update :
                checkUpdate();
                break;
            case R.id.my_line_contact :
                break;
            case R.id.my_line_history :
                intent = new Intent(getActivity(), HistoryListActivity.class);
                startActivity(intent);
                break;
            case R.id.my_line_qq :
              //  prefetchDownload();
                break;
            case R.id.my_line_tools :
                if(InitAdConfig.isHuaWeiFlag()) {
                    intent = new Intent(getActivity(), ToolsActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void checkUpdate(){
        promptDialog.setViewAnimDuration(1000);
        promptDialog.showLoading("检测中...");
        new Handler().postDelayed(() -> {
            //  Toasty.success(context,"已是最新版本").show();
            promptDialog.showInfo("已是最新版本");
        }, 500);
    }



}
