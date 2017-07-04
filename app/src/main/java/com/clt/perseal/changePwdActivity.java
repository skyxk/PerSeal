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

import com.clt.perseal.Util.DBManager;
import com.clt.perseal.Util.SimpleDesede;

public class changePwdActivity extends AppCompatActivity {
    private DBManager db;
    private EditText changePwdText;
    private EditText changeRePwdText;
    private TextView errorText;
    private Button changePwdBtn;
    private String phone;
    private String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        db = new DBManager(changePwdActivity.this);
        Intent in = getIntent();
        phone = in.getStringExtra("phone");

        changePwdText = (EditText)findViewById(R.id.changePwdText);
        changeRePwdText = (EditText)findViewById(R.id.changeRePwdText);
        errorText = (TextView)findViewById(R.id.errortext1) ;
        errorText.setVisibility(View.GONE);



        changeRePwdText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    if((changePwdText.getText().toString()).equals(changeRePwdText.getText().toString())){
                        errorText.setVisibility(View.GONE);
                    }else{
                        errorText.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
        //
        changePwdBtn = (Button) findViewById(R.id.changePwdBtn);
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd=changePwdText.getText().toString();

                if(pwd.equals(changeRePwdText.getText().toString()) && !"".equals(changeRePwdText.getText().toString())){
//                    errPwd.setVisibility(View.GONE);
                    //成功，更新密码。
                    db.updatePwd(phone, SimpleDesede.encryptToDB(pwd));
//                    Toast.makeText(changePwdActivity.this, "修改密码成功",Toast.LENGTH_SHORT).show();//显示信息;
                    //跳转登录
                    new AlertDialog.Builder(changePwdActivity.this).setTitle("密码修改成功")//设置对话框标题
                            .setMessage("是否重新登录")//设置显示的内容
                            .setPositiveButton("登录",new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                    //转到登录页面
                                    Intent intent = new Intent(changePwdActivity.this, LoginActivity.class);
                                    intent.putExtra("phone",phone);
                                    startActivity(intent);
                                    finish();

                                }
                            }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            // TODO Auto-generated method stub
                            onDestroy();
                        }
                    }).show();//在按键响应事件中显示此对话框
                }else{
                    //提示失败
                    errorText.setVisibility(View.VISIBLE);
                   Toast.makeText(changePwdActivity.this, "修改密码失败",Toast.LENGTH_SHORT).show();//显示信息;
                }
            }
        });
    }
}
