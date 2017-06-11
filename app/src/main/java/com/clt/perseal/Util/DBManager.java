package com.clt.perseal.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clt.perseal.Dto.UnitDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clt_abc on 2017/4/24.
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        //建user表
        db.execSQL("CREATE TABLE IF NOT EXISTS user (_id integer primary key autoincrement," +
                " name varchar(36)," +
                " phone varchar(20)," +
                " password varchar(36)," +
                " idcard varchar(36) )");

        db.execSQL("CREATE TABLE IF NOT EXISTS verlist (_id integer primary key autoincrement," +
                //验证码
                " vercode varchar(20))");// 创建密码表
    }

    /**
     * add persons
     * @param user
     */
    public void add(UnitDto user) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO user VALUES( ?, ?, ?, ?)", new Object[]{user.getId(),
                    user.getName(), user.getPhone(), user.getPassword()});
            db.setTransactionSuccessful();//设置事务成功完成
        } finally {
            db.endTransaction();//结束事务
        }
    }
    /**
     * update user's age
     * @param user
     */
    public void updatePhone(UnitDto user) {
        ContentValues cv = new ContentValues();
        cv.put("age", user.getPhone());
        db.update("user", cv, "name = ?", new String[]{user.getName()});
    }
    /**
     * delete old user
     * @param user
     */
    public void deleteOldPerson(UnitDto user) {
        db.delete("user", "id = ?", new String[]{String.valueOf(user.getId())});
    }
    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

    /**
    * 清空
    * @return
    */
    public void  deleteAll(){
        db.execSQL("DELETE FROM user");
    }
    /**
     * 查询手机号
     * @return
     */
    public String queryPhone(){
        String phone = null;
        //查询获得游标
        Cursor cursor = db.query ("user",null,null,null,null,null,null);
        //判断游标是否为空
        if(cursor.moveToFirst()){
            //遍历游标
            for(int i=0;i<cursor.getCount();i++){
                cursor.move(i);
                phone = cursor.getString(2);
            }
        }else{
            return phone;
        }
        return phone;
    }
    /**
     * 插入数据
     * @param phone 手机号
     * @param name 用户名
     * @return
     */
    public void insert(String name,String phone,String pwd,String idcard){

        db.execSQL("DELETE FROM user");
        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加用户名
        cValue.put("name",name);
        //添加手机号
        cValue.put("phone",phone);
        //添加密码
        cValue.put("password",pwd);
        //添加身份证号
        cValue.put("idcard",idcard);
        //调用insert()方法插入数据
        db.insert("user",null,cValue);
    }

    /**
     * 检查密码
     * @return
     */
    public String check(String phone){
        String phone_1 = null;
        String p1;
        String p2 = "";
        String sql = "select * from user where phone == ? ";
        //查询获得游标
        Cursor cursor = db.rawQuery(sql, new String[]{phone});
//        db.query ("user", null,"phone=?",new String[]{phone},null,null,null);

        //判断游标是否为空
        while(cursor.moveToNext()){
            p2 = SimpleDesede.decryptFromDB(cursor.getString(cursor.getColumnIndex("password")));
            return p2;
        }
        return p2;
    }

    /**
     * 插入验证码
     * @param vercode 用户名
     * @return
     */
    public void insertVer(String vercode){

        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加验证码
        cValue.put("vercode",vercode);
        //调用insert()方法插入数据
        db.insert("verlist",null,cValue);
    }
    /**
     * 查询验证码列表
     * @return
     */
    public List<String> queryVer(){
        List<String> verlist = new ArrayList<String>() ;
        Cursor c = db.rawQuery("SELECT vercode FROM verlist order by _id DESC ",null);
        while (c.moveToNext()) {
            verlist.add(c.getString(c.getColumnIndex("vercode")));
        }
        return verlist;
    }

    /**
     * 查询身份证号
     * @param phone
     * @return
     */
    public String queryIdcard(String phone) {
        String idcard = null;
        Cursor c = db.rawQuery("SELECT idcard FROM user where phone == ? ",new String[]{phone});
        while (c.moveToNext()) {
            idcard = c.getString(c.getColumnIndex("idcard"));
        }
        return idcard;
    }
    /**
     * 修改密码
     * @param phone
     */
    public void updatePwd(String phone,String password) {

        ContentValues cv = new ContentValues();
        cv.put("password", password);
        db.update("user", cv, "phone = ?", new String[]{phone});

    }
}
