package com.vxiaokang.video.activity.tool;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vxiaokang.video.R;
import com.vxiaokang.video.base.system.StatusBarUtil;
import com.vxiaokang.video.view.DragScaleView;
import com.vxiaokang.video.view.NewDragScaleView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/****
 * 可拉伸矩形框
 */
public class DragScaleActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "DragScaleActivity";
    private ImageView ivBack;

    private Button showResult;
    private Button showResult2;
    private Button setAuto;
    private DragScaleView dragScaleView;
    private NewDragScaleView my_newdragscale;
    @BindView(R.id.audioView)
    AudioView audioView;
    private static final String[] STYLE_DATA = new String[]{"STYLE_ALL", "STYLE_NOTHING", "STYLE_WAVE", "STYLE_HOLLOW_LUMP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_scale);
        ButterKnife.bind(this);
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
        dragScaleView = findViewById(R.id.my_dragscale);
        my_newdragscale = findViewById(R.id.my_newdragscale);

    }
    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        View mybar = findViewById(R.id.my_topbar);
        StatusBarUtil.setMyBarHeight(mybar,this);
        String string  = "ffd223dpoew,.zxkdas;dsamfoidf,amdas;2,23,.vf";
        byte[] data = string.getBytes();
        audioView.setWaveData(data);
    }

    @Override
    public void onClick(View v) {
        int position = 0;
        switch (v.getId()){
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.showResult:
                Log.d(TAG,"cutHeight ="+my_newdragscale.getCutHeight());
                Log.d(TAG,"cutWidth ="+my_newdragscale.getCutWidth());
                Log.d(TAG,"left ="+my_newdragscale.getLeft());
                Log.d(TAG,"right ="+my_newdragscale.getRight());
                Log.d(TAG,"x ="+my_newdragscale.getX());
                Log.d(TAG,"y ="+my_newdragscale.getY());
              //  Log.d(TAG,"getLeftTopLocation  x = y="+my_newdragscale.getLeftTopLocation()[0]+"  "+my_newdragscale.getLeftTopLocation()[1]);
              //  Log.d(TAG,"getLeftBottomLocation  x y="+my_newdragscale.getLeftBottomLocation()[0]+"  "+my_newdragscale.getLeftBottomLocation()[1]);
              //  Log.d(TAG,"getRightTopLocation  x y="+my_newdragscale.getRightTopLocation()[0]+"  "+my_newdragscale.getRightTopLocation()[1]);
              //  Log.d(TAG,"getRightBottomLocation  x y="+my_newdragscale.getRightBottomLocation()[0]+"  "+my_newdragscale.getRightBottomLocation()[1]);
                break;
            case R.id.showResult2:
                position = (int)Math.round(Math.random()*STYLE_DATA.length-1);
                audioView.setStyle(AudioView.ShowStyle.getStyle(STYLE_DATA[position]), audioView.getDownStyle());
                break;
            case R.id.setAuto:
                position = (int)Math.round(Math.random()*STYLE_DATA.length-1);
                audioView.setStyle(audioView.getUpStyle(), AudioView.ShowStyle.getStyle(STYLE_DATA[position]));
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

}
