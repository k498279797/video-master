package com.vxiaokang.video.activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;

import me.leefeng.promptlibrary.PromptDialog;

/****
 * 常用工具
 */
public class OpenVideoActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "OpenWebSiteActivity";
    private ImageView ivBack;
    private PromptDialog promptDialog;

    private TextView submit_clear_txt;
    private TextView submit_paste_txt;
    private TextView submit_open_txt;
    private EditText website_url_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_video);
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

        website_url_txt = findViewById(R.id.website_url_txt);
        submit_clear_txt = findViewById(R.id.submit_clear_txt);
        submit_paste_txt = findViewById(R.id.submit_paste_txt);
        submit_open_txt = findViewById(R.id.submit_open_txt);
        submit_clear_txt.setOnClickListener(this);
        submit_paste_txt.setOnClickListener(this);
        submit_open_txt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.submit_open_txt:
                if(TextUtils.isEmpty(website_url_txt.getText())){
                    promptDialog.showError("请填写地址");
                }else{
                    Intent intent = new Intent(this, VideoPlayActivity.class);
                    intent.putExtra("url", website_url_txt.getText().toString());
                    intent.putExtra("title", "");
                    startActivity(intent);
                }
                break;
            case R.id.submit_clear_txt:
                website_url_txt.setText("");
                break;
            case R.id.submit_paste_txt:
                getCopy(this);
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
    private void openSelfWebsite(){
        Intent intent = new Intent(this, VideoPlayActivity.class);
        intent.putExtra("url", website_url_txt.getText().toString());
        intent.putExtra("title", "");
        startActivity(intent);
    }
    private void openNewWebsite(){
        Uri uri = Uri.parse(website_url_txt.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getCopy(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        //无数据时直接返回
        if (clipboard == null || !clipboard.hasPrimaryClip()) {
            return;
        }
        //如果是文本信息
        if (clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cdText = clipboard.getPrimaryClip();
            ClipData.Item item = cdText.getItemAt(0);
            //此处是TEXT文本信息
            if (item.getText() != null) {
                //item为剪贴板的内容，你可以取到这个字符串，然后再根据规则去进行剪贴拼接
                String content = item.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    website_url_txt.setText(content);
                }
            }
        }
    }

    /**
     * 清空剪贴板内容
     */
    public static void clearClipboard() {
        ClipboardManager manager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            try {
                manager.setPrimaryClip(manager.getPrimaryClip());
                manager.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
