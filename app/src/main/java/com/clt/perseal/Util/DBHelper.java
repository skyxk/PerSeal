package com.clt.perseal.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.clt.perseal.Constants.Constants;

import java.io.File;


/**
 * Created by clt_abc on 2017/4/20.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "per.db";
    private static final int DATABASE_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
//    private static String getMyDatabaseName(Context context){
//        String databasename = DATABASE_NAME;
//        boolean isSdcardEnable =false;
//        String state =Environment.getExternalStorageState();
//        if(Environment.MEDIA_MOUNTED.equals(state)){//SDCard是否插入
//            isSdcardEnable = true;
//        }
//        String dbPath = null;
//        if(isSdcardEnable){
//            dbPath =Environment.getExternalStorageDirectory().getPath() +"/database/perseal/";
//        }else{//未插入SDCard，建在内存中
//            dbPath =context.getFilesDir().getPath() + "/database/perseal/";
//        }
//        File dbp = new File(dbPath);
//        if(!dbp.exists()){
//            dbp.mkdirs();
//        }
//        databasename = dbPath +DATABASE_NAME;
//
//        return databasename;
//    }

    //数据库被创建时调用的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //输出更新数据库的日志信息

        //建unit表
        db.execSQL("CREATE TABLE IF NOT EXISTS unit (_id integer primary key autoincrement," +
                " name varchar(36)," +
                " phone varchar(20)," +
                " password varchar(36), " +
                " question varchar(36), " +
                " answer varchar(36) )");
        db.execSQL("CREATE TABLE IF NOT EXISTS vercodes (_id integer primary key autoincrement," +
                //验证码
                " phone varchar(20), vercode varchar(20))");
        Log.i(Constants.TAG, "Create Database stu.db ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        if (oldVersion==1 && newVersion==2) {//升级判断,如果再升级就要再加两个判断,从1到3,从2到3
//            db.execSQL("ALTER TABLE restaurants ADD phone TEXT;");
//        }
    }
}
