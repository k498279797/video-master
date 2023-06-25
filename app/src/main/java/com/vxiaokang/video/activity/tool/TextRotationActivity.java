package com.vxiaokang.video.activity.tool;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vxiaokang.video.App;
import com.vxiaokang.video.R;
import com.vxiaokang.video.adapter.KeyBoardDataAdapter;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.bean.KeyBoardBean;
import com.vxiaokang.video.bean.KeyBoardUtils;
import com.vxiaokang.video.util.FilesUtils;
import com.vxiaokang.video.util.MediaUtility;
import com.vxiaokang.video.view.AutoScrollView;
import com.wx.goodview.GoodView;

import java.util.ArrayList;
import java.util.List;

/****
 * 倒置文字
 */
public class TextRotationActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "TextRotationActivity";
    private ImageView ivBack;

    private Button showResult;
    private Button showResult2;
    private Button setAuto;
    private Button setGoodView;
    private EditText txtNumber;
    private TextView txtNumber2;
    private AutoScrollView autoScrollView;
    private int autoType = 0;  // 0 不滚动 1 自动滚动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_txt_rotation);
        StatusBarUtil.setNavbarColor(this);
        StatusBarUtil.setStatusBar(this,R.color.color_00000000);
        initView();
        txtRotation();
    }

    private void txtRotation(){
        showResult = findViewById(R.id.showResult);
        showResult.setOnClickListener(this);
        showResult2 = findViewById(R.id.showResult2);
        showResult2.setOnClickListener(this);

        setAuto = findViewById(R.id.setAuto);
        setAuto.setOnClickListener(this);
        txtNumber = findViewById(R.id.txtNumber);
        txtNumber2 = findViewById(R.id.txtNumber2);
       // Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/2222.ttf");
        //txtNumber2.setTypeface(typeface);

        autoScrollView = findViewById(R.id.autoScrollView);
        //autoScrollView.setScrolled(true);
       // autoScrollView.setSpeed(20);

        setGoodView = findViewById(R.id.setGoodView);

       // final GoodView goodView2 = new GoodView(this);
        setGoodView.setOnClickListener(v -> {
            GoodView goodView = new GoodView(this);
            goodView.setText("功德+1");
            goodView.setTranslateY(20,100);
            goodView.show(v);

          //  goodView2.setText("祝福+1");
          //  goodView2.show(v);
        });
    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.showResult:
                String txtValue = txtNumber.getText().toString();
                txtNumber2.setText(txtValue);
               /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/pain");//设置类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);*/

                break;
            case R.id.showResult2:
                String txtValue2 = getResources().getString(R.string.txt_content);
                txtNumber2.setText(txtValue2);
                break;
            case R.id.setAuto:
                if(autoType == 0){
                    autoType = 1;
                    setAuto.setText("停止滚动");
                    autoScrollView.setScrolled(true);
                    autoScrollView.setSpeed(10);
                }else{
                    autoType = 0;
                    setAuto.setText("自动滚动");
                    autoScrollView.setScrolled(false);
                    autoScrollView.setSpeed(10);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            switch (requestCode) {
                case 1:
                    // ResultActivity的返回数据
                   /* Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程

                   String url = FilesUtils.getPath(App.getContext(),uri);
                    Log.d(TAG,"url == "+url);
                    Log.d(TAG,"uri == "+uri.getPath());*/
                case 2:
                    // NewActivity的返回数据
            }
        }
    }


}
