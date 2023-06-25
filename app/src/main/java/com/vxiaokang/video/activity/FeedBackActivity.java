package com.vxiaokang.video.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.DeviceUuidFactory;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.req.ReqFeedBack;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.request.RequsetFeedBack;
import com.vxiaokang.video.util.EmptyUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;

/****
 * 意见反馈
 */
public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "FeedBackActivity";
    private EditText feedbackContact; //联系人
    private EditText feedbackComment; //意见
    private TextView feedbackSubmit; //提交按钮
    private ImageView ivBack;
    private PromptDialog promptDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        promptDialog = new PromptDialog(this);
        promptDialog.setViewAnimDuration(1000);
        initView();
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        feedbackContact = findViewById(R.id.feedback_contact);
        feedbackComment = findViewById(R.id.feedback_comment);
        feedbackSubmit = findViewById(R.id.feedback_submit);
        feedbackSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.feedback_submit:
                submitFeedBack();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    private void submitFeedBack(){
        Log.d("submitFeedBack -- ","=====");
        if(TextUtils.isEmpty(feedbackContact.getText())){
           // Toasty.error(this, "联系方式不能为空").show();
            promptDialog.showError("联系方式不能为空");
            return;
        }
        if(TextUtils.isEmpty(feedbackComment.getText())){
           // Toasty.error(this, "意见描述不能为空").show();
            promptDialog.showError("意见描述不能为空");
            return;
        }
        Context context = this;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //Toasty.success(context,"反馈成功").show();
                sendMsg();
            }
        }, 500);
    }

    private void sendMsg(){
        try{
            promptDialog.showSuccess("反馈成功");
            /*promptDialog.showLoading("提交中...");
            ReqFeedBack params = new ReqFeedBack();
            params.setApp_id(ConfigKey.MY_APP_ID);
            params.setPhone_num(feedbackContact.getText().toString());
            params.setFeed_back(feedbackComment.getText().toString());
            String  uUID = String.valueOf(DeviceUuidFactory.getInstance(this).getDeviceUuid());
            params.setImei(uUID);
            RequsetFeedBack.addData(params, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    promptDialog.showError("反馈失败");
                    promptDialog.dismiss();
                    Log.e(TAG,"errr:"+ e.getMessage());
                }
                @Override
                public void onResponse(String response, int id) {
                    promptDialog.dismiss();
                    Log.e(TAG,"resp : "+response);
                   if(!EmptyUtils.isEmpty(response)){
                       JsonObject resp = new Gson().fromJson(response,JsonObject.class);
                       if(resp.get("code").getAsInt() == 0){
                           promptDialog.showSuccess("反馈成功");
                       }else{
                           promptDialog.showError("反馈失败");
                       }
                   }else{
                       promptDialog.showError("反馈失败");
                   }
                }
            });*/
        }catch (Exception e){
            promptDialog.showError("反馈失败");
        }
    }
}
