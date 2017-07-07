package com.clt.perseal.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.Dto.VerCodeDto;
import com.clt.perseal.Util.DBHelper;
import com.clt.perseal.Util.SimpleDesede;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clt_abc on 2017/7/5.
 */

public class VerCodeDao {
    private DBHelper helper;
    private SQLiteDatabase db;

    public VerCodeDao(Context context){
        //实例化数据库帮助类
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    /**
     * 增加VerCode
     * @param vercode
     */
    public void addVerCode(VerCodeDto vercode){

        db.beginTransaction();  //开始事务
        try {
            //实例化常量值
            ContentValues cValue = new ContentValues();
            //添加手机号
            if(vercode.getPhone()!=null){
                cValue.put("phone",vercode.getPhone());
            }
            //添加验证码
            if(vercode.getVercode()!=null){
                cValue.put("vercode",SimpleDesede.encryptToDB(vercode.getVercode()));
            }
            //调用insert()方法插入数据
            db.insert("vercodes",null,cValue);

            db.setTransactionSuccessful();//设置事务成功完成

        } finally {
            db.endTransaction();//结束事务
        }
    }
    /**
     * 根据手机号查询所有VerCode
     * @param phone
     * @return UnitDto
     */
    public List<VerCodeDto> queryVerCodes(String phone){
        List<VerCodeDto> vercodelist = new ArrayList<VerCodeDto>();
        VerCodeDto vercode = new VerCodeDto();

        Cursor c = db.query("vercodes",
                null,"phone = ?",
                new String[] {phone},
                null,null,null);
        while (c.moveToNext()) {
            vercode.setPhone(c.getString(c.getColumnIndex("phone")));
            vercode.setVercode(SimpleDesede.decryptFromDB(c.getString(c.getColumnIndex("vercode"))));
            vercodelist.add(vercode);
        }
        return vercodelist;
    }

    /**
     * 修改VerCode
     * @param vercode
     */
    public void updateVerCode(VerCodeDto vercode) {
//        ContentValues cValue = new ContentValues();
//        //添加用户名
//        if(unit.getName()!=null){
//            cValue.put("name",unit.getName());
//        }
//        //添加手机号
//        if(unit.getPhone()!=null){
//            cValue.put("phone",unit.getPhone());
//        }
//        db.update("unit", cValue, "phone = ?", new String[]{unit.getPhone()});
    }

    /**
     * 删除指定VerCode
     * @return
     */
    public void  deleteVerCode(String phone){
//        db.execSQL("DELETE FROM unit");
//        db.beginTransaction();//开启事务
//        db.delete("unit", "phone = ?", new String[]{phone});
//        db.setTransactionSuccessful();//设置事务成功完成
//
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
        db.execSQL("DELETE FROM vercodes");
    }
}
