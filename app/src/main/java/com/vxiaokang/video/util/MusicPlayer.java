package com.vxiaokang.video.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;


import com.vxiaokang.video.R;

import java.util.Map;
import java.util.TreeMap;

public class MusicPlayer { 
    private Context mContext ;
    private static MusicPlayer sInstance ;
    static class Type{
        public final static int MUSIC_CLICK = 1 ;
        public final static int MUSIC_FOCUSED = 2 ;
    }
    private SoundPool mSp ;
    private Map<Integer ,Integer> sSpMap ;
    private MusicPlayer(Context context){
        mContext = context ;

        sSpMap = new TreeMap<Integer ,Integer>() ;
        mSp = new SoundPool(10 , AudioManager.STREAM_MUSIC ,100) ;
        sSpMap.put(Type.MUSIC_CLICK, mSp.load(mContext, R.raw.bg0, 1)) ;
        sSpMap.put(Type.MUSIC_FOCUSED, mSp.load(mContext, R.raw.bg12, 1)) ;
    }

    public static MusicPlayer getInstance(Context context){
        if(sInstance == null)
            sInstance = new MusicPlayer(context) ;
        return sInstance ;
    }

    /**
     *
     * @param type 音频
     * @param loop loop mode (0 = no loop, -1 = loop forever)
     * @param rate playback rate (1.0 = normal playback, range 0.5 to 2.0)
     */
    public void play(int type,int loop,float rate){
        if(sSpMap.get(type) == null) return ;
        mSp.autoPause();
        mSp.play(sSpMap.get(type), 1, 1, 0, loop, rate) ;
        mSp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

            }
        });
    }
}