package com.vxiaokang.video.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 图片全图
 */
public class FullScreenImageView extends AppCompatImageView {

    public static final String TAG = FullScreenImageView.class.getSimpleName();

    private Bitmap mBitmap;
    private boolean mLock;

    public FullScreenImageView(Context context) {
        this(context, null);
    }

    public FullScreenImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullScreenImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mBitmap != null && mLock) {
            mLock = false;
            float viewW = getWidth();
            float viewH = getHeight();
            float bw = mBitmap.getWidth();
            float bh = mBitmap.getHeight();
            float radioW = viewW / bh;
            float radioH = viewH / bw;

            Matrix matrixFollScreen = new Matrix();
            //matrixFollScreen.postRotate(90);
            matrixFollScreen.postScale(radioW, radioH);
            Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrixFollScreen, true);
//            Log.e(TAG, "zwg----[onLayout]:  " + viewW + "*" + viewH);
//            Log.e(TAG, "zwg----[onLayout]:  " + bw + "*" + bh);
//            Log.e(TAG, "zwg----[onLayout]:  " + radioW + "-" + radioH);
            super.setImageBitmap(bitmap);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmap = bm;
        mLock = true;
    }
}

