package com.vxiaokang.video.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.vxiaokang.video.entity.dao.DaoMaster;
import com.vxiaokang.video.entity.dao.PlayRecordDao;

import org.greenrobot.greendao.database.Database;

public class MigrationHelper extends DaoMaster.OpenHelper{
    private final String TAG="MigrationHelper";

    public MigrationHelper(Context context, String name) {
        super(context, name);
    }

    public MigrationHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
        /**
         * 这个方法
         * 1、在第一次打开数据库的时候才会走
         * 2、在清除数据之后再次运行-->打开数据库，这个方法会走
         * 3、没有清除数据，不会走这个方法
         * 4、数据库升级的时候这个方法不会走
         */
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d(TAG,"onUpgrade = oldVersion = "+oldVersion+"newVersion="+newVersion);
        super.onUpgrade(db, oldVersion, newVersion);
        if (newVersion > oldVersion) {
            new UpgradeHelper().migrate(db, PlayRecordDao.class);
        }
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        /**
         * 执行数据库的降级操作
         * 1、只有新版本比旧版本低的时候才会执行
         * 2、如果不执行降级操作，会抛出异常
         */
    }
}
