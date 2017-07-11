package com.clt.perseal.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.Util.DBHelper;
import com.clt.perseal.Util.SimpleDesede;

/**
 * Created by clt_abc on 2017/7/5.
 */

public class UnitDao {
    private DBHelper helper;
    private SQLiteDatabase db;

    public UnitDao(Context context){
        //实例化数据库帮助类
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    /**
     * 增加Unit
     * @param unit
     */
    public void addUnit(UnitDto unit){

        db.beginTransaction();  //开始事务
        try {
            //实例化常量值
            ContentValues cValue = new ContentValues();
            //添加用户名
            if(unit.getName()!=null){
                cValue.put("name",unit.getName());
            }
            //添加手机号
            if(unit.getPhone()!=null){
                cValue.put("phone",unit.getPhone());
            }
            //添加密码
            if(unit.getPassword()!=null){
                cValue.put("password",SimpleDesede.encryptToDB(unit.getPassword()));
            }
            //添加密保问题
            if(unit.getQuestion()!=null){
                cValue.put("question",unit.getQuestion());
            }
            //添加密保答案
            if(unit.getAnswer()!=null){
                cValue.put("answer",SimpleDesede.encryptToDB(unit.getAnswer()));
            }
            //调用insert()方法插入数据
            db.insert("unit",null,cValue);

            db.setTransactionSuccessful();//设置事务成功完成

        } finally {
            db.endTransaction();//结束事务
        }
    }
    /**
     * 查询unit
     * @param phone
     * @return UnitDto
     */
    public UnitDto queryUnit(String phone){

        UnitDto unit = new UnitDto();
        Cursor c = db.query("unit",
                            null,"phone = ?",
                            new String[] {phone},
                            null,null,null);
        while (c.moveToNext()) {
            unit.setName(c.getString(c.getColumnIndex("name")));
            unit.setPhone(c.getString(c.getColumnIndex("phone")));
            unit.setPassword(SimpleDesede.decryptFromDB(c.getString(c.getColumnIndex("password"))));
            unit.setQuestion(c.getString(c.getColumnIndex("question")));
            unit.setAnswer(SimpleDesede.decryptFromDB(c.getString(c.getColumnIndex("answer"))));
        }
        return unit;
    }
    /**
     * 查询unit
     * @return UnitDto
     */
    public UnitDto queryOnlyUnit(){

        UnitDto unit = new UnitDto();
        Cursor c = db.query("unit",
                null,null, null,
                null,null,null);
        while (c.moveToNext()) {
            unit.setName(c.getString(c.getColumnIndex("name")));
            unit.setPhone(c.getString(c.getColumnIndex("phone")));
            unit.setPassword(SimpleDesede.decryptFromDB(c.getString(c.getColumnIndex("password"))));
            unit.setQuestion(c.getString(c.getColumnIndex("question")));
            unit.setAnswer(SimpleDesede.decryptFromDB(c.getString(c.getColumnIndex("answer"))));
        }
        return unit;
    }


    /**
     * 修改unit
     * @param unit
     */
    public void updateUnit(UnitDto unit) {
        ContentValues cValue = new ContentValues();
        //添加用户名
        if(unit.getName()!=null){
            cValue.put("name",unit.getName());
        }
        //添加手机号
        if(unit.getPhone()!=null){
            cValue.put("phone",unit.getPhone());
        }
        //添加密码
        if(unit.getPassword()!=null){
            cValue.put("password",SimpleDesede.encryptToDB(unit.getPassword()));
        }
        //添加密保问题
        if(unit.getQuestion()!=null){
            cValue.put("question",unit.getQuestion());
        }
        //添加密保答案
        if(unit.getAnswer()!=null){
            cValue.put("answer",SimpleDesede.encryptToDB(unit.getAnswer()));
        }
        db.update("unit", cValue, "phone = ?", new String[]{unit.getPhone()});
    }

    /**
     * 删除指定unit
     * @return
     */
    public void  deleteUnit(String phone){
        db.execSQL("DELETE FROM unit");
        db.beginTransaction();//开启事务
        db.delete("unit", "phone = ?", new String[]{phone});
        db.setTransactionSuccessful();//设置事务成功完成
    }


    /**
     * 关闭数据库
     */
    public void closeDB() {
        db.close();
    }

    /**
     * 清空
     * @return
     */
    public void  deleteAll(){
        db.execSQL("DELETE FROM unit");
    }
}
