package com.clt.perseal;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.clt.perseal.Util.Base64Utils;
import com.clt.perseal.Util.DBManager;

import java.util.ArrayList;
import java.util.List;



/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private String phone;
    private String password;
    private EditText mPasswordView;
    private  TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取手机号
        Intent itt = getIntent();

        //本地手机号
        phone = itt.getStringExtra("phone");

//      初始化
        errorText = (TextView)findViewById(R.id.errortext);
        errorText.setVisibility(View.GONE);
        TextView phText = (TextView)findViewById(R.id.phonetext);
        phText.setText(phone);
        //登录
        Button lgBtn = (Button)findViewById(R.id.email_sign_in_button);
        //注销
        Button deBtn = (Button)findViewById(R.id.button3);
        deBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager DBManager = new DBManager(LoginActivity.this);
                DBManager.deleteAll();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        lgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPasswordView = (EditText) findViewById(R.id.password);
                password = mPasswordView.getText().toString();
                DBManager DBManager = new DBManager(LoginActivity.this);
                if(password.equals(DBManager.check(phone))){
                    //密码正确，手机号是否激活
                    if(true){
                        //已激活 转至主页面
                        Intent intent_1 = new Intent(LoginActivity.this, MainActivity.class);
//                        intent_1.putExtra("phone",phone);
                        startActivity(intent_1);
                    }else{
                        //未激活 转至激活页面
                        Intent intent_2 = new Intent(LoginActivity.this, ActivateActivity.class);
//                        intent_2.putExtra("phone",phone);
                        startActivity(intent_2);
                    }
                }else{
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}

