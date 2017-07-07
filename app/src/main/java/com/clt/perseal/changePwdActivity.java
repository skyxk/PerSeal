package com.clt.perseal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.perseal.Dao.UnitDao;
import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.Util.DBManager;
import com.clt.perseal.Util.SimpleDesede;

public class changePwdActivity extends AppCompatActivity {
    private UnitDao unitDao;

    private EditText mPwdText;
    private EditText mRePwdText;
    private TextView errorText;
    private Button changePwdBtn;
    private String phone;
    private String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        unitDao = new UnitDao(changePwdActivity.this);

        initView();
        initDate();

    }

    private void initView() {

        mPwdText = (EditText)findViewById(R.id.changePwdText);
        mRePwdText = (EditText)findViewById(R.id.changeRePwdText);
        errorText = (TextView)findViewById(R.id.errortext1) ;
        errorText.setVisibility(View.GONE);

        changePwdBtn = (Button) findViewById(R.id.changePwdBtn);


        //重复密码框焦点事件处理
        mRePwdText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    if((mPwdText.getText().toString()).equals(mRePwdText.getText().toString())){
                        errorText.setVisibility(View.GONE);
                    }else{
                        errorText.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        //修改按钮点击事件处理
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pwd=mPwdText.getText().toString();

                UnitDto unit = unitDao.queryUnit(phone);
                if(!"".equals(mRePwdText.getText().toString()) && !"".equals(mPwdText.getText().toString())) {
                    if(pwd.equals(mRePwdText.getText().toString())){
                        unit.setPassword(pwd);
                        //修改密码
                        unitDao.updateUnit(unit);

                        //修改成功提示
                        new AlertDialog.Builder(changePwdActivity.this).setTitle("密码修改成功")//设置对话框标题
                                .setMessage("是否重新登录")//设置显示的内容
                                .setPositiveButton("登录",new DialogInterface.OnClickListener() {//添加确定按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                        //转到登录页面
                                        Intent intent = new Intent(changePwdActivity.this, LoginActivity.class);
                                        intent.putExtra("phone",phone);
                                        startActivity(intent);
                                        finish();

                                    }
                                }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//响应事件
                                onDestroy();
                            }
                        }).show();//在按键响应事件中显示此对话框

                    }else{
                        Toast.makeText(changePwdActivity.this, "两次输入密码不一致",Toast.LENGTH_SHORT).show();//显示信息;
                    }
                }else{
                    Toast.makeText(changePwdActivity.this, "请填写填写全部内容",Toast.LENGTH_SHORT).show();//显示信息;
                }

            }
        });
    }

    private void initDate() {
        Intent in = getIntent();
        phone = in.getStringExtra("phone");
    }
}
