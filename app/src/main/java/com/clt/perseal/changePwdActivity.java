package com.clt.perseal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.perseal.R;
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

        //
        changePwdBtn = (Button) findViewById(R.id.changePwdBtn);
        changePwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd=changePwdText.getText().toString();
                if(pwd.equals(changeRePwdText.getText().toString())){
//                    errPwd.setVisibility(View.GONE);
                    //成功，更新密码。
                    db.updatePwd(phone, SimpleDesede.encryptToDB(pwd));
                    Toast.makeText(changePwdActivity.this, "修改密码成功",Toast.LENGTH_SHORT).show();//显示信息;
                    //跳转登录
                    Intent intent = new Intent(changePwdActivity.this, LoginActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else{
                    //提示失败
                    errorText.setVisibility(View.VISIBLE);
                   Toast.makeText(changePwdActivity.this, "修改密码失败",Toast.LENGTH_SHORT).show();//显示信息;
                }
            }
        });
    }
}
