package com.clt.perseal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.clt.perseal.Util.Base64Utils;
import com.clt.perseal.Util.DBManager;
import com.clt.perseal.Util.SimpleDesede;

public class RegisterActivity extends AppCompatActivity {
    private EditText phText ;
    private EditText pwText ;
    private EditText rePwText ;
    private EditText idCard;
    private TextView errPwd ;
    private Button regBtn ;
    private String idStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化组件
        phText = (EditText)findViewById(R.id.editphone);
        pwText = (EditText)findViewById(R.id.editpwd);
        rePwText = (EditText)findViewById(R.id.editrepwd);
        idCard = (EditText)findViewById(R.id.editidcard);
        errPwd = (TextView)findViewById(R.id.texterror);

        errPwd.setVisibility(View.GONE);
        regBtn = (Button)findViewById(R.id.regBtn) ;
        //        regBtn.setEnabled(false);
        phText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容

                }
            }
        });
        pwText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容

                }
            }
        });
        rePwText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容

                } else {
                    if((pwText.getText().toString()).equals(rePwText.getText().toString())){
                        errPwd.setVisibility(View.GONE);
                    }else{
                        errPwd.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //注册按钮点击事件
                if((pwText.getText().toString()).equals(rePwText.getText().toString())){
                    //设置提示信息不可见
                    errPwd.setVisibility(View.GONE);
                    idStr = idCard.getText().toString();
                    String a  =(pwText.getText()).toString();
                    //注册手机号
                    DBManager db = new DBManager(RegisterActivity.this);
                    db.insert("", (phText.getText()).toString(),
                            SimpleDesede.encryptToDB((pwText.getText()).toString()),idStr);
                    //转到登录页面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("phone",(phText.getText()).toString());
                    startActivity(intent);

                }else{
                    //设置提示信息可见
                    errPwd.setVisibility(View.VISIBLE);
                }

            }
        });

    }
}
