package com.vxiaokang.video.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.vxiaokang.video.R;

import java.util.Map;
import java.util.TreeMap;

public class MediaMusicPlayer {
    private Context mContext ;
    private static MediaMusicPlayer sInstance ;
    private static MediaPlayer mediaPlayer ;
    static class Type{
        public final static int MUSIC_CLICK = 1 ;
        public final static int MUSIC_CLICK2 = 2 ; 
    }

    private Map<Integer ,Integer> sSpMap ;
    private MediaMusicPlayer(Context context){
        mContext = context ;
        mediaPlayer = new MediaPlayer();
        sSpMap = new TreeMap<Integer ,Integer>() ;
        sSpMap.put(Type.MUSIC_CLICK,R.raw.fomusic5);
        sSpMap.put(Type.MUSIC_CLICK2,R.raw.fomusic5);
    }

    public static MediaMusicPlayer getInstance(Context context){
        if(sInstance == null)
            sInstance = new MediaMusicPlayer(context) ;
        return sInstance ;
    }

    /**
     * 停止
     */
    public void stop(){
        if(null != mediaPlayer){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     *
     * @param type 音频
     * @param loop loop mode true false
     */
    public void play(int type,boolean loop){
        if(null != mediaPlayer){
            mediaPlayer.release();
        }
        if(sSpMap.get(type) == null) return ;
        mediaPlayer = MediaPlayer.create(mContext,sSpMap.get(type));
        mediaPlayer.setLooping(loop);
        mediaPlayer.start();
    }

    public static void  release(){
        if(null != mediaPlayer){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}