package com.vxiaokang.video.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.ConfigBean;
import com.vxiaokang.video.constants.ConfigKey;
import com.vxiaokang.video.request.RequestConfig;
import com.zhy.http.okhttp.callback.StringCallback;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.Call;

/**
 * web view
 */
public class WebViewActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "WebViewActivity";
    private ImageView ivBack;
    private TextView titleView;
    private WebView webCommonView;
    private PromptDialog promptDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);

        titleView = findViewById(R.id.web_view_title);
        webCommonView = findViewById(R.id.web_common_view);//webview 容器
        promptDialog = new PromptDialog(this);

       try{
           String title = getIntent().getStringExtra("title");
           //设置标题
           if(!TextUtils.isEmpty(title)){
               titleView.setText(title);
           }
           String configKey = getIntent().getStringExtra("configKey");
           String url = getIntent().getStringExtra("url");
           if(!StringUtils.isEmpty(url)){
               setData(url);
           }else{
               getWebViewUrl(configKey);
           }
       }catch (Exception ex){
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
                promptDialog.dismiss();
            }
        });
        WebSettings webSettings = webCommonView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 是否开启JS支持
        //webSettings.setPluginsEnabled(true); // 是否开启插件支持
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 是否允许JS打开新窗口
        webSettings.setUseWideViewPort(true); //缩放至屏幕大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕大小
        webSettings.setSupportZoom(true); // 是否支持缩放
        webSettings.setBuiltInZoomControls(true); // 是否支持缩放变焦，前提是支持缩放
        webSettings.setDisplayZoomControls(true); // 是否隐藏缩放控件

        webSettings.setAllowFileAccess(true); // 是否允许访问文件
        webSettings.setDomStorageEnabled(true); // 是否节点缓存
        webSettings.setDatabaseEnabled(true); // 是否数据缓存
        webSettings.setAppCacheEnabled(true); // 是否应用缓存
        webSettings.setAppCachePath(url); // 设置缓存路径

        /*webSettings.setMediaPlaybackRequiresUserGesture(false); // 是否要手势触发媒体
        webSettings.setStandardFontFamily("sans-serif"); // 设置字体库格式
        webSettings.setFixedFontFamily("monospace"); // 设置字体库格式
        webSettings.setSansSerifFontFamily("sans-serif"); // 设置字体库格式
        webSettings.setSerifFontFamily("sans-serif"); // 设置字体库格式
        webSettings.setCursiveFontFamily("cursive"); // 设置字体库格式
        webSettings.setFantasyFontFamily("fantasy"); // 设置字体库格式
        webSettings.setTextZoom(100); // 设置文本缩放的百分比
        webSettings.setMinimumFontSize(8); // 设置文本字体的最小值(1~72)
        webSettings.setDefaultFontSize(16); // 设置文本字体默认的大小

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 按规则重新布局
        webSettings.setLoadsImagesAutomatically(false); // 是否自动加载图片
        webSettings.setDefaultTextEncodingName("UTF-8"); // 设置编码格式
        webSettings.setNeedInitialFocus(true); // 是否需要获取焦点
        webSettings.setGeolocationEnabled(false); // 设置开启定位功能*/
        webSettings.setBlockNetworkLoads(true); // 是否从网络获取资源
        webSettings.setJavaScriptEnabled(true);//true允许使用JavaScript脚本：false则相反
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        //显示网页加载进度；
      /*  webCommonView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });*/
       webCommonView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    private void getWebViewUrl(String configKey){
            if(ConfigKey.APP_PRIVACY_URL.equals(configKey)){
                // file:///android_asset/editor.html
                setData("file:///android_asset/privacy.html");
            }else if(ConfigKey.APP_PROTOCOL_URL.equals(configKey)){
                // file:///android_asset/editor.html
                setData("file:///android_asset/protocol.html");
            }else{
                try{
                 //   promptDialog.showLoading("加载中...");
                    RequestConfig.getData(configKey, new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            promptDialog.dismiss();
                      //      Log.e(TAG,"error:" + e.getMessage());
                        }
                        @Override
                        public void onResponse(String response, int id) {
                            promptDialog.dismiss();
                        //    Log.e(TAG,"resp:"+ response);
                            ConfigBean bean = new Gson().fromJson(response, ConfigBean.class);
                            if(null != bean && bean.getData() != null && bean.getData().size() > 0){
                                setData(bean.getData().get(0).getConfigValue());
                            }
                        }
                    });
                }catch (Exception e){
                    promptDialog.dismiss();
                }
            }
    }

}
