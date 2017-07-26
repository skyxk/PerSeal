package com.clt.perseal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.perseal.Dao.UnitDao;
import com.clt.perseal.Dao.VerCodeDao;
import com.clt.perseal.Dto.UnitDto;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private Button mLgbtn;
    private Button mChgebtn;
    private Button mRgrbtn;
    private Button removbtn;

    private String phone;
    private String password;
    private EditText mPhoneView;
    private EditText mPasswordView;
    private TextView errorText;
    private UnitDao unitDao;
    private VerCodeDao verDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unitDao = new UnitDao(LoginActivity.this);
        verDao = new VerCodeDao(LoginActivity.this);
        initView();

        initDate();

    }

    /**
     * 初始化view
     */
    public void initView(){

        mPhoneView = (EditText) findViewById(R.id.phone);

        mPasswordView = (EditText) findViewById(R.id.password);

        errorText = (TextView)findViewById(R.id.errortext);

        //登录
        mLgbtn = (Button)findViewById(R.id.login_btn);

        //修改密码
        mChgebtn = (Button)findViewById(R.id.change_pwd_btn);

        //注册
        mRgrbtn = (Button)findViewById(R.id.register_btn);
        //注销
        removbtn = (Button) findViewById(R.id.removed_btn);

        mLgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone = mPhoneView.getText().toString();

                password = mPasswordView.getText().toString();


                UnitDto unit = unitDao.queryUnit(phone);
                if(!"".equals(phone)){
                    if(password.equals(unit.getPassword())){
                        //保存登录信息
                        savePhone(phone);
                        //跳转进入程序
                        Intent intent_1 = new Intent(LoginActivity.this, MainActivity.class);
                        intent_1.putExtra("phone",phone);
                        startActivity(intent_1);
                        finish();
                    }else {
                        errorText.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"请填写手机号",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mChgebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转密保问题页面
                Intent intent = new Intent(LoginActivity.this, SecretVerifyActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        });
        mRgrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        removbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = mPhoneView.getText().toString();

                password = mPasswordView.getText().toString();


                UnitDto unit = unitDao.queryUnit(phone);
                if(!"".equals(phone)){
                    if(password.equals(unit.getPassword())){
                        //清除信息
//                        savePhone(phone);
                        unitDao.deleteAll();
                        verDao.deleteAll();
                        //跳转注册
                        Intent intent_1 = new Intent(LoginActivity.this, RegisterActivity.class);
//                        intent_1.putExtra("phone",phone);
                        startActivity(intent_1);
                        finish();
                    }else {
                        errorText.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"请填写手机号",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 初始化date
     */
    public void initDate(){

        UnitDto unit = unitDao.queryOnlyUnit();
        if(unit.getPhone()!=null){
            mPhoneView.setText(unit.getPhone());
            mPhoneView.setEnabled(false);
            mRgrbtn.setVisibility(View.GONE);
        }else{

        }

        errorText.setVisibility(View.GONE);
//        mPhoneView.setText(phone);
    }

    public void savePhone(String phone){

        //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
        SharedPreferences preferences = this.getSharedPreferences("perseal", Context.MODE_PRIVATE);
        //取到编辑器
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("phone", phone);
        //把数据提交给文件中
        editor.commit();
    }
}

