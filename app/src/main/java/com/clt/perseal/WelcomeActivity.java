package com.clt.perseal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.clt.perseal.Util.DBManager;


/**
 * Created by clt_abc on 2017/4/26.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置显示的布局
        setContentView(R.layout.welcome_main);


        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DBManager DBManager = new DBManager(WelcomeActivity.this);
                String phone = DBManager.queryPhone();
                if(phone==null){
                    Intent intent_1 = new Intent(WelcomeActivity.this, RegisterActivity.class);
                    startActivity(intent_1);
                }else{
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }
                WelcomeActivity.this.finish();
            }
        }, 1000);
    }





}
