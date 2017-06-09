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
    private static final String DATABASE_NAME = "stu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user";
    private static final String[] COLUMNS = {"_id","name","phone"};
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    //数据库被创建时调用的方法
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        //输出更新数据库的日志信息
        Log.i(Constants.TAG, "Create Database------------->");




    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

        //输出更新数据库的日志信息
        Log.i(Constants.TAG, "pdate Database------------->");
    }

}
