package com.clt.perseal.Constants;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.clt.perseal.WelcomeActivity;

/**
 * Created by clt_abc on 2017/4/20.
 */

public class Constants {
    public static final String TAG = "SQLite";
    public static final String SYS = "System";
    // webview 访问地址前缀
//    public static final String webUrl = "http://10.88.4.102:9080/EssApp/";
    public static final String webUrl = "http://192.168.1.108:8083/EssApp/";

//    public void  Alret(Context context){
//
//        new AlertDialog.Builder(context).setTitle("新版本提示")//设置对话框标题
//                .setMessage("本应用已有新版本，请及时升级！否则无法进入应用")//设置显示的内容
//                .setPositiveButton("确认",new DialogInterface.OnClickListener() {//添加确定按钮
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        Uri content_url = Uri.parse(webUrl+"activateApp/downLoadTerimalApp.jsp");
//                        intent.setData(content_url);
////                        startActivity(intent);
//
//                    }
//                }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
//            @Override
//            public void onClick(DialogInterface dialog, int which) {//响应事件
//
////                        WelcomeActivity.this.finish();
////                goToNext();
//
//            }
//        }).show();//在按键响应事件中显示此对话框
//
//
//    }



}
