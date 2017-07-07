package com.clt.perseal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.clt.perseal.Dao.UnitDao;
import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.Util.DBManager;


/**
 * Created by clt_abc on 2017/4/26.
 */

public class WelcomeActivity extends Activity {
//    private UnitDao unitdao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置显示的布局
        setContentView(R.layout.welcome_main);
//        unitdao = new UnitDao(WelcomeActivity.this);
        //计时器，一秒后跳转
        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 1000);
    }
}
