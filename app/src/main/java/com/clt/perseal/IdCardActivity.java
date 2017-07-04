package com.clt.perseal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.perseal.Util.DBManager;

public class IdCardActivity extends AppCompatActivity {
    private DBManager db;
    private String phone;
    private String Idcard;
    private EditText IdCardText;
    private TextView quesText;
    private Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_card);
        Intent in = getIntent();
        phone = in.getStringExtra("phone");
        db = new DBManager(IdCardActivity.this);
        //获取输入答案
        IdCardText = (EditText)findViewById(R.id.IdCardText);
        quesText = (TextView)findViewById(R.id.questView);

        quesText.setText(db.queryQuestion(phone)+":");
        //获取按钮
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Idcard = IdCardText.getText().toString();
                if(Idcard.equals(db.queryAnswer(phone))){

                    //跳转身份验证
                    Intent intent = new Intent(IdCardActivity.this, changePwdActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                    finish();
                }else{
                    //提示输入错误
                     Toast.makeText(IdCardActivity.this, "密保答案输入错误",Toast.LENGTH_SHORT).show();//显示信息;
                }
            }
        });

    }
}
