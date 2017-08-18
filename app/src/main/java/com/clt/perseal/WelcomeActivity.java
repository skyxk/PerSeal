package com.clt.perseal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.Toast;

import com.clt.perseal.Dao.UnitDao;
import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.Util.UpdateUtil;
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

        goToNext();
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
