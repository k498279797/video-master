package com.vxiaokang.video.activity.tool.luck;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vxiaokang.video.R;

import java.util.Random;

public class LuckCircle extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    //用于绘制的线程
    private Thread mThread;
    //线程开关的控制
    private boolean isRunning;
    private String[] mStr = new String[]{"优惠券","十元话费","恭喜发财","恭喜发财","英雄皮肤","50M流量"};

    //物品的图片
    private int[] mImgs = new int[]{R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};

    private int mItemCount = 6;

    //盘快的颜色
    private int[] mColor = new int[]{0xffffc300,0xFFD9B114,0xFFDC0B2E,0xFF5510A4,0xFF447C42,0xFFEC3636};

    //与图片对应的bitmap数组
    private Bitmap[] mImgBitmap;

    //整个盘块的范围
    private RectF mRange = new RectF();

    //整个盘块的直径
    private int mRadius;

    //绘制盘块的画笔
    private Paint mArcPaint;


    //绘制文本的画笔
    private Paint mTextPaint;

    //滚动速度
    private double mSpeed = 10;


    //绘制的角度
    private volatile int mStartAngle = 0;


    //判断是否点击了停止按钮
    private boolean isShouldEnd;


    //转盘的中心位置
    private int mCenter;

    //padding取四个padding中的最小值
    private int mPadding;

    //背景图
    //private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics());
    public LuckCircle(Context context) {
        this(context,null);
    }

    public LuckCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        // 可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        setKeepScreenOn(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(),getMeasuredHeight());

        mPadding = getPaddingLeft();
        mRadius = width - mPadding *2;
        mCenter = width / 2;
        setMeasuredDimension(width,width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 初始化绘制盘块的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        // 初始化绘制盘块的画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(0XFF0B25CF);
        mTextPaint.setTextSize(mTextSize);

        // 初始化盘块绘制的范围
        mRange = new RectF(mPadding,mPadding,mPadding+mRadius,mPadding+mRadius);

        // 初始化图片
        mImgBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mImgBitmap.length; i++) {
            mImgBitmap[i] = BitmapFactory.decodeResource(getResources(),mImgs[i]);
        }
        draw();
       // isRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while(true){
           if(isRunning){
               if(isShouldEnd){
                   int stayIndex = new Random().nextInt(10)+1;
                   for(int i = 0 ; i < stayIndex ; i++){
                       realDraw();
                   }
                   isShouldEnd = false;
               }else{
                   realDraw();
               }
           }else{
               try{
                   Thread.sleep(500);
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        }
    }
    private void realDraw(){
        long start = System.currentTimeMillis();
        draw();
        long end = System.currentTimeMillis();
        if (end - start < 50) {
            try {
                Thread.sleep(50 - (end - start));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw(){
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                // 绘制背景
                drawBackground();
                // 绘制盘块
                float tmpAngle = mStartAngle;
                float sweepAngle = 360 /mItemCount;
                for (int i = 0; i < mItemCount; i++) {
                    mArcPaint.setColor(mColor[i]);
                    // 绘制盘块
                    mCanvas.drawArc(mRange,tmpAngle,sweepAngle,true,mArcPaint);
                    // 绘制文本
                    drawText(tmpAngle,sweepAngle,mStr[i]);
                    // 绘制Icon
                    drawIcon(tmpAngle,mImgBitmap[i]);
                    tmpAngle += sweepAngle;
                }
                mStartAngle = mStartAngle+20;
            }
        }catch (Exception e){

        }finally {
            if (mCanvas != null) {
                // 释放Canvas
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }


    //点击启动旋转
    public void luckyStart(){
        mSpeed = 50;
        isRunning = true;
        isShouldEnd = false;
    }
    public void luckEnd(){
        isShouldEnd = true;
        isRunning = false;
    }
    public boolean isStart(){
        return mSpeed != 0;
    }
    public boolean isShouldEnd(){
        return isShouldEnd;
    }

    //绘制Icon

    private void drawIcon(float tmpAngle, Bitmap bitmap) {
        // 设置图片的宽度为直径的1/8；
        int imgWidth = mRadius / 8;

        float angle = (float) ((tmpAngle + 360 / mItemCount / 2)* Math.PI/180);

        int x = (int) (mCenter + mRadius/2/2 * Math.cos(angle));
        int y = (int) (mCenter + mRadius/2/2 * Math.sin(angle));
        // 确定图片的位置
        Rect rect = new Rect(x - imgWidth/2, y - imgWidth/2, x + imgWidth/2, y + imgWidth/2);
        mCanvas.drawBitmap(bitmap,null,rect,null);

    }


    //绘制每个盘块的文本

    private void drawText(float tmpAngle, float sweepAngle, String s) {
        Path path = new Path();
        path.addArc(mRange,tmpAngle,sweepAngle);
        // 利用水平偏移量让文字居中
        float measureText = mTextPaint.measureText(s);
        int hOffset = (int) (mRadius * Math.PI/mItemCount/2 - measureText/2);
        int vOffset = mRadius /2/6;
        mCanvas.drawTextOnPath(s,path,hOffset,vOffset,mTextPaint);
    }

    private void drawBackground() {
        int drawColor = Color.parseColor("#F7F7F7");
       // mCanvas.drawColor(0xFF696565);
        mCanvas.drawColor(drawColor);
        Paint paint = new Paint();
        //paint.setColor(0xFFF94905);
        paint.setColor(drawColor);
        mCanvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,paint);
    }
}