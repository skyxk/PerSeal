package com.clt.perseal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.Toast;

import com.clt.perseal.Dao.UnitDao;
import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.WebSerivce.WsControler;

import java.util.StringTokenizer;

import static com.clt.perseal.Constants.Constants.webUrl;


/**
 * Created by clt_abc on 2017/4/26.
 */

public class WelcomeActivity extends Activity {
    private UnitDao unitdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置显示的布局
        setContentView(R.layout.welcome_main);
        unitdao = new UnitDao(WelcomeActivity.this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        try {

            String Version = "a" + getVersionName();
            String result = WsControler.getAppUpdate("PerSeal","a"+Version);
            if(result==null){

                goToNext();

            }else{

                if(!"ESSERROR".equals(result) ){


                    StringTokenizer st = new StringTokenizer(result,"@");

                    String status = st.nextToken();
                    String lastVersion = st.nextToken();

                    //STATUS:0   不需要更新  STATUS:1    有最新版可进行更新    STATUS:2    强制更新否则不能进入应用
                    if("STATUS:0".equals(status)){

                        goToNext();


                    } else if("STATUS:1".equals(status)){

                        if(Version.equals(lastVersion)){

                            goToNext();

                        }else{

                            new AlertDialog.Builder(WelcomeActivity.this).setTitle("新版本提示")//设置对话框标题
                                    .setMessage("本应用已有新版本，请及时升级！")//设置显示的内容
                                    .setPositiveButton("确认",new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            Uri content_url = Uri.parse(webUrl+"activateApp/downLoadPersonApp.jsp");
                                            intent.setData(content_url);
                                            startActivity(intent);
                                        }
                                    }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件

                                    UnitDto unit = unitdao.queryOnlyUnit();
                                    if(unit.getPhone()!=null){
                                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                        intent.putExtra("phone",unit.getPhone());
                                        startActivity(intent);
                                    }else{
                                        Intent intent_1 = new Intent(WelcomeActivity.this, RegisterActivity.class);
                                        startActivity(intent_1);
                                    }

                                    WelcomeActivity.this.finish();


                                }
                            }).show();//在按键响应事件中显示此对话框
                        }

                    }else if("STATUS:2".equals(status)){
                        if(Version.equals(lastVersion)){

                            goToNext();

                        }else{

                            new AlertDialog.Builder(WelcomeActivity.this).setTitle("新版本提示")//设置对话框标题
                                    .setMessage("本应用已有新版本，请及时升级！否则无法进入应用")//设置显示的内容
                                    .setPositiveButton("确认",new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                            Intent intent = new Intent();
                                            intent.setAction("android.intent.action.VIEW");
                                            Uri content_url = Uri.parse(webUrl+"activateApp/downLoadPersonApp.jsp");
                                            intent.setData(content_url);
                                            startActivity(intent);

                                        }
                                    }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件

                                    WelcomeActivity.this.finish();

                                }
                            }).show();//在按键响应事件中显示此对话框
                        }

                    }
                }else{
                    Toast.makeText(WelcomeActivity.this,"解析数据错误",Toast.LENGTH_LONG).show();
                }


            }



        }catch (Exception e){
            Toast.makeText(WelcomeActivity.this,"获取版本号错误",Toast.LENGTH_LONG).show();
        }
    }

    //获取应用版本号
    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        int versionCode =packInfo.versionCode;
        return version;
    }

    private void goToNext(){


        //计时器，一秒后跳转
        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UnitDto unit = unitdao.queryOnlyUnit();
                if(unit.getPhone()!=null){
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    intent.putExtra("phone",unit.getPhone());
                    startActivity(intent);
                }else{
                    Intent intent_1 = new Intent(WelcomeActivity.this, RegisterActivity.class);
                    startActivity(intent_1);
                }

                WelcomeActivity.this.finish();
            }
        }, 1000);



    }

}
