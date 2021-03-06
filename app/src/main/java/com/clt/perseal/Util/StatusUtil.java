package com.clt.perseal.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.clt.perseal.ChildWebViewActivity;

import com.clt.perseal.WebSerivce.WsControler;

import java.util.StringTokenizer;

import static com.clt.perseal.Constants.Constants.webUrl;

/**
 * Created by clt_abc on 2017/8/18.
 */

public class StatusUtil {

    private Context ct;
    private String returnvalue;
    public SharedPreferences preferences;
    public StatusUtil(Context context){
        ct = context;
    }

    public void isActivate(){


        preferences = ct.getSharedPreferences("perseal", Context.MODE_PRIVATE);

        returnvalue = preferences.getString("activiteState", null);

        if("ESSRET:0".equals(returnvalue)){


        }else if("ESSRET:1".equals(returnvalue)){

            new AlertDialog.Builder(ct).setTitle("您的手机号尚未激活")//设置对话框标题
                    .setMessage("是否前往激活")//设置显示的内容
                    .setPositiveButton("激活",new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                            //手机号未激活，跳转激活页。
//                            Intent intent_1 = new Intent(ct, ActivateActivity.class);
//                            ct.startActivity(intent_1);
                            Intent intent = new Intent(ct, ChildWebViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url","activateApp/login.jsp");
                            intent.putExtras(bundle);
                            ct.startActivity(intent);
                        }
                    }).show();//在按键响应事件中显示此对话框
        }

    }







    //获取应用版本号
    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = ct.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(ct.getPackageName(),0);
        String version = packInfo.versionName;
        int versionCode =packInfo.versionCode;
        return version;
    }

    public void Update(){
        try {
            String Version = "a" + getVersionName();
            //启动后台异步线程进行连接webService操作，并且根据返回结果在主线程中改变UI
            QueryAddressTask queryAddressTask = new QueryAddressTask();
            //启动后台任务
            queryAddressTask.execute(Version);

        }catch (Exception e){
            Toast.makeText(ct,"获取版本号错误",Toast.LENGTH_LONG).show();
        }
    }

    class QueryAddressTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // 查询手机号码（段）信息*/
//            String result = null;
            String isAlert=null;
            try {
                String result = WsControler.getAppUpdate("PerSeal",params[0]);
//                String result = "STATUS:1@a2";
                if(result==null){
//                    goToNext();
                    isAlert="0";
                }else{
                    if(!"ESSERROR".equals(result) ){

                        StringTokenizer st = new StringTokenizer(result,"@");
                        String status = st.nextToken();
                        String lastVersion = st.nextToken();

                        //STATUS:0   不需要更新  STATUS:1    有最新版可进行更新    STATUS:2    强制更新否则不能进入应用
                        if("STATUS:0".equals(status)){
//                            goToNext();
                            isAlert="0";
                        } else if("STATUS:1".equals(status)){

                            if(params[0].equals(lastVersion)){
//                                goToNext();
                                isAlert="0";
                            }else{
                                isAlert = "1";
                            }
                        }else if("STATUS:2".equals(status)){
                            if(params[0].equals(lastVersion)){
//                                goToNext();
                                isAlert="0";
                            }else{
                                isAlert = "2";
                            }
                        }
                    }else{
                        Toast.makeText(ct,"解析数据错误",Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //将结果返回给onPostExecute方法
            return isAlert;
        }

        @Override
        //此方法可以在主线程改变UI
        protected void onPostExecute(String isAlert) {
            // 将WebService返回的结果显示在TextView中
//            resultView.setText(result);
            if("1".equals(isAlert)){

                new AlertDialog.Builder(ct).setTitle("新版本提示")//设置对话框标题
                        .setMessage("本应用已有新版本，请及时升级！否则无法进入应用")//设置显示的内容
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(webUrl+"activateApp/downLoadTerimalApp.jsp");
                                intent.setData(content_url);
                                ct.startActivity(intent);

                            }
                        }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件


                    }
                }).show();//在按键响应事件中显示此对话框


            }else if("2".equals(isAlert)){

                new AlertDialog.Builder(ct).setTitle("新版本提示")//设置对话框标题
                        .setMessage("本应用已有新版本，请及时升级！否则无法进入应用")//设置显示的内容
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(webUrl+"activateApp/downLoadTerimalApp.jsp");
                                intent.setData(content_url);
                                ct.startActivity(intent);
                            }
                        }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件

                    }
                }).show();//在按键响应事件中显示此对话框
            }else if("0".equals(isAlert)){


            }
        }
    }

}
