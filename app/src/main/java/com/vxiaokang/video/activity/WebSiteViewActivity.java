package com.vxiaokang.video.activity;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.ConfigBean;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.request.RequestConfig;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * web view
 */
public class WebSiteViewActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "WebViewActivity";
    private ImageView ivBack;
    private TextView titleView;
    private WebView webCommonView;
    // private PromptDialog promptDialog;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private myWebChromeClient xwebchromeclient;

    private ProgressDialog waitdialog = null;
    private FrameLayout video_fullView;// 全屏时视频加载view
    private View xCustomView;
    private RelativeLayout top_title_line;
    private  View mybar;
    private String cssStyle = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this, R.color.color_00000000);
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        top_title_line = findViewById(R.id.top_title_line);
        ivBack.setOnClickListener(this);

        mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar, this);

        waitdialog = new ProgressDialog(this);
        waitdialog.setTitle("提示");
        waitdialog.setMessage("页面加载中...");
        waitdialog.setIndeterminate(true);
        waitdialog.setCancelable(true);
        //waitdialog.show();

        titleView = findViewById(R.id.web_view_title);
        video_fullView = findViewById(R.id.video_fullView);
        webCommonView = findViewById(R.id.web_common_view);//webview 容器
        //promptDialog = new PromptDialog(this);
        try {
            String title = getIntent().getStringExtra("title");
            //设置标题
            if (!TextUtils.isEmpty(title)) {
                titleView.setText(title);
            }
            String configKey = getIntent().getStringExtra("configKey");
            String url = getIntent().getStringExtra("url");
            if (!StringUtils.isEmpty(url)) {
                setData(url);
            } else {
                getWebViewUrl(configKey);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setData(String url) {
        webCommonView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();// 接受所有网站的证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //  promptDialog.showLoading("加载中...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //promptDialog.dismiss();
                waitdialog.dismiss();
                webCommonView.loadUrl("javascript:(function() { " +
                        "if(null != document.getElementsByClassName('toolbar')){" +
                            "document.getElementsByClassName('toolbar')[0].style.display='none'; " +
                        "}" +
                        "if(null != document.getElementById('toolbarContainer')){" +
                            "document.getElementById('toolbarContainer').style.display='none'; " +
                        "}" +
                        "if(null != document.getElementsByClassName('word_book')){" +
                            "document.getElementsByClassName('word_book')[0].style.display='none'; " +
                        "}" +
                        "if(null != document.getElementsByClassName('recommand_pro')){" +
                            "document.getElementsByClassName('recommand_pro')[0].style.display='none'; " +
                        "}" +
                        "if(null != document.getElementsByClassName('advice')){" +
                            "document.getElementsByClassName('advice')[0].style.display='none'; " +
                        "}" +
                        "})()");

            }
        });
        WebSettings settings = webCommonView.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);//有JavaScript功能的一定要实现
        settings.setBuiltInZoomControls(true);//支持缩放
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);//bushi
        settings.setAllowContentAccess(true);//
        settings.setAppCacheEnabled(true);//是否使用缓存
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 是否允许JS打开新窗口
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);// 新加

        settings.setPluginState(WebSettings.PluginState.ON);
        //settings.setPluginsEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        xwebchromeclient = new myWebChromeClient();
        webCommonView.setWebChromeClient(xwebchromeclient);
       // webCommonView.addJavascriptInterface();
        webCommonView.loadUrl(url);
    }

    public class myWebChromeClient extends WebChromeClient {
        private View xprogressvideo;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            webCommonView.setVisibility(View.INVISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            video_fullView.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            top_title_line.setVisibility(View.GONE);
            mybar.setVisibility(View.GONE);
            if(BarUtils.isNavBarVisible(getWindow())){
                BarUtils.setNavBarVisibility(getWindow(),false);
            }
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            video_fullView.removeView(xCustomView);
            xCustomView = null;
            xCustomViewCallback.onCustomViewHidden();
            webCommonView.setVisibility(View.VISIBLE);
            top_title_line.setVisibility(View.VISIBLE);
            mybar.setVisibility(View.VISIBLE);
            if(!BarUtils.isNavBarVisible(getWindow())){
                BarUtils.setNavBarVisibility(getWindow(),true);
            }
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater
                        .from(WebSiteViewActivity.this);
                xprogressvideo = inflater.inflate(R.layout.loading_progress, null);
            }
            return xprogressvideo;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (webCommonView.canGoBack()) {
            webCommonView.goBack();
            return;
        }
        supportFinishAfterTransition();
    }

    private void getWebViewUrl(String configKey) {
        if (ConfigKey.APP_PRIVACY_URL.equals(configKey)) {
            // file:///android_asset/editor.html
            setData("file:///android_asset/privacy.html");
        } else if (ConfigKey.APP_PROTOCOL_URL.equals(configKey)) {
            // file:///android_asset/editor.html
            setData("file:///android_asset/protocol.html");
        } else {
            try {
                //   promptDialog.showLoading("加载中...");
                RequestConfig.getData(configKey, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //promptDialog.dismiss();
                        //      Log.e(TAG,"error:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //promptDialog.dismiss();
                        //    Log.e(TAG,"resp:"+ response);
                        ConfigBean bean = new Gson().fromJson(response, ConfigBean.class);
                        if (null != bean && bean.getData() != null && bean.getData().size() > 0) {
                            setData(bean.getData().get(0).getConfigValue());
                        }
                    }
                });
            } catch (Exception e) {
                // promptDialog.dismiss();
            }
        }
    }


    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
   /* public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }*/

   /* @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        webCommonView.onResume();
        webCommonView.resumeTimers();
        */

    /**
     * 设置为横屏
     *//*
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webCommonView.onPause();
        webCommonView.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        video_fullView.removeAllViews();
        webCommonView.loadUrl("about:blank");
        webCommonView.stopLoading();
        webCommonView.setWebChromeClient(null);
        webCommonView.setWebViewClient(null);
        webCommonView.destroy();
        webCommonView = null;
    }
   // @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView();
                return true;
            } else {
                //webCommonView.loadUrl("about:blank");
               // WebSiteViewActivity.this.finish();
            }
        }
        return false;
    }*/


    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        webCommonView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webCommonView.onResume();
    }

    @Override
    public void onDestroy() {
        webCommonView.destroy();
        super.onDestroy();
    }
}