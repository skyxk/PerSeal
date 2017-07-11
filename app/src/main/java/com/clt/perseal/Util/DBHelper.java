package com.clt.perseal.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.clt.perseal.Constants.Constants;


/**
 * Created by clt_abc on 2017/4/20.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "per.db";
    private static final int DATABASE_VERSION = 1;
//    private static final String TABLE_NAME = "user";
//    private static final String[] COLUMNS = {"_id","name","phone"};
//    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
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
