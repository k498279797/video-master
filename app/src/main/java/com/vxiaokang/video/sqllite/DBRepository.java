package com.vxiaokang.video.sqllite;

import android.content.Context;


import com.vxiaokang.video.entity.dao.DaoMaster;
import com.vxiaokang.video.entity.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DBRepository {
    public static final String DB_NAME = "video-db";//数据库名称
    private static DaoSession mDaoSession;
    private static MigrationHelper mySqlLiteOpenHelper;
    /**
     * 初始化greenDao，这个操作建议在Application初始化的时候添加；
     */
    public static void initDatabase(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        mySqlLiteOpenHelper = new MigrationHelper(context.getApplicationContext(),DB_NAME,null);
        Database db = mySqlLiteOpenHelper.getWritableDb();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

}
