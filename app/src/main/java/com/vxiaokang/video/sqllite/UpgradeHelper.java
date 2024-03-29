package com.vxiaokang.video.sqllite;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;


import com.vxiaokang.video.App;
import com.vxiaokang.video.entity.dao.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpgradeHelper {
    private static String dbName = "mydatabase.db";
    private final String TAG = "UpgradeHelper";
    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
    private static MigrationHelper instance;

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper(App.getContext(), dbName);
        }
        return instance;
    }

    public void migrate(final Database db, final Class<? extends AbstractDao<?, ?>>... daoClasses) {
        Log.i(TAG, "1111");
        generateTempTables(db, daoClasses);
        Log.i(TAG, "2222");
        DaoMaster.dropAllTables(db, true);
        Log.i(TAG, "3333");
        DaoMaster.createAllTables(db, false);
        Log.i(TAG, "4444");
        restoreData(db, daoClasses);
    }

    private void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            try {
                DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
                String divider = "";
                String tableName = daoConfig.tablename;
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                ArrayList<String> properties = new ArrayList<>();
                StringBuilder createTableStringBuilder = new StringBuilder();
                createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");

                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;

                    if (getColumns(db, tableName).contains(columnName)) {
                        properties.add(columnName);
                        String type = null;
                        try {
                            type = getTypeByClass(daoConfig.properties[j].type);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);
                        if (daoConfig.properties[j].primaryKey) {
                            createTableStringBuilder.append(" PRIMARY KEY");
                        }
                        divider = ",";
                    }
                }
                createTableStringBuilder.append(");");
                db.execSQL(createTableStringBuilder.toString());
                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(" FROM ").append(tableName).append(";");
                db.execSQL(insertTableStringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                continue;
            }
        }
    }

    private void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            try {
                DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
                String tableName = daoConfig.tablename;
                String tempTableName = daoConfig.tablename.concat("_TEMP");
                Log.i(TAG, "tableName=" + tableName);
                ArrayList<String> properties = new ArrayList();

                for (int j = 0; j < daoConfig.properties.length; j++) {
                    String columnName = daoConfig.properties[j].columnName;
                    if (getColumns(db, tempTableName).contains(columnName)) {
                        properties.add(columnName);
                    }
                }
                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(TextUtils.join(",", properties));
                insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                StringBuilder dropTableStringBuilder = new StringBuilder();
                dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
                db.execSQL(insertTableStringBuilder.toString());
                db.execSQL(dropTableStringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                continue;
            }
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class)  || type.equals(long.class)||
                type.equals(Integer.class) || type.equals(int.class)||
                type.equals(byte.class)||type.equals(Byte.class)||
                type.equals(short.class)||type.equals(Short.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)||type.equals(boolean.class)) {
            return "BOOLEAN";
        }
        if (type.equals(Double.class)||type.equals(double.class) ||type.equals(float.class)||type.equals(Float.class)){
            return "REAL";
        }
        if(type.equals(byte[].class)){
            return "BLOB";
        }
        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        exception.printStackTrace();
        throw exception;
    }
    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;

    }
}
