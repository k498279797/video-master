package com.vxiaokang.video;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.ImageUtils;
import com.kongzue.dialog.v2.DialogSettings;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.vxiaokang.video.entity.dao.DaoSession;
import com.vxiaokang.video.sqllite.DBRepository;
import com.vxiaokang.video.util.ImageLoaderUtil;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_IOS;
import static com.kongzue.dialog.v2.DialogSettings.THEME_DARK;
import static com.kongzue.dialog.v2.DialogSettings.THEME_LIGHT;


/**
 * 应用
 * application
 */
public class App extends Application {
    private String TAG = "app";
    public static Context context;
    public static int H, W;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();//try-catch主线程的所有异常；Looper.loop()内部是一个死循环，出现异常时才会退出，所以这里使用while(true)。
                    } catch (Throwable e) {
                        Log.d(TAG, "Looper.loop(): " + e.getMessage());
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {//try-catch子线程的所有异常。
                Log.d(TAG, "UncaughtExceptionHandler: " + e.getMessage());
            }
        });

    }



    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
        context = this;
        getScreen(this);
       try{
         //  Logger.addLogAdapter(new AndroidLogAdapter());
           DialogSettings.use_blur = true;
           DialogSettings.style = STYLE_IOS;
           DialogSettings.tip_theme = THEME_DARK;
           DialogSettings.dialog_theme = THEME_LIGHT;
           initDataBase();
           //initImageLoader();
         /*  runOnUiThread(() -> {
              // Toast.makeText(App.this,"App： 启动成功!",Toast.LENGTH_LONG).show();
           });*/
           VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                   //使用使用IjkPlayer解码
                   .setPlayerFactory(IjkPlayerFactory.create())
                   //使用ExoPlayer解码
                 //  .setPlayerFactory(ExoMediaPlayerFactory.create())
                   //使用MediaPlayer解码
                   .setPlayerFactory(AndroidMediaPlayerFactory.create())
                   .build());

        /*   String licenceURL = "https://license.vod2.myqcloud.com/license/v2/1301796830_1/v_cube.license"; // 获取到的 licence url
           String licenceKey = "8adf4ba1f1fe6f8d4f23060db90d6412"; // 获取到的 licence key
           TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
           TXLiveBase.setListener(new TXLiveBaseListener() {
               @Override
               public void onLicenceLoaded(int result, String reason) {
                   Log.i(TAG, "onLicenceLoaded: result:" + result + ", reason:" + reason);
               }
           });*/
            //https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20230226015251
          // CrashReport.initCrashReport(getApplicationContext(), "dcf739d181", false);
           // APP全局字体
         /* CalligraphyConfig.initDefault(
                   new CalligraphyConfig.Builder()
                           .setDefaultFontPath("fonts/ZCOOL_KuHei_Regular.ttf")
                           .setFontAttrId(R.attr.fontPath)
                           .build());*/
           Bitmap src = null;
           ImageUtils.save2Album(src, Bitmap.CompressFormat.JPEG,false);
           //ZipUtils.u

       }catch (Exception e){
           e.printStackTrace();
           /*runOnUiThread(() -> {
              // Toast.makeText(App.this,"出错："+e.getMessage(),Toast.LENGTH_LONG).show();
           });*/
       }

       try{
           //handleSSLHandshake();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public   void initDataBase() {
        DBRepository.initDatabase(getContext());
    }

    public static DaoSession getDaoSession() {
        return DBRepository.getDaoSession();
    }
    public static Context getContext() {
        return context;
    }
    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

    public void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(100 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoaderUtil.getInstance().init(config);
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
