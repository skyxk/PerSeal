package com.clt.perseal;

import android.content.Intent;
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

public class SecretVerifyActivity extends AppCompatActivity {

    private UnitDao unitDao;
    private String answer;
    private EditText answerText;
    private EditText phone_Text;
    private TextView quesText;
    private TextView phone_tip_Text;
    private Button nextBtn;
    private Button nextBtn_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_verify);

        initView();
        initDate();

        unitDao = new UnitDao(SecretVerifyActivity.this);

    }

    private void initView() {
        phone_tip_Text = (TextView) findViewById(R.id.verify_phone_tip);
        phone_Text = (EditText) findViewById(R.id.verify_phone);
        answerText = (EditText)findViewById(R.id.verify_answer);
        quesText = (TextView)findViewById(R.id.verify_question);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn_1 = (Button)findViewById(R.id.nextBtn_1);

        //
        nextBtn.setVisibility(View.VISIBLE);
        phone_tip_Text.setVisibility(View.VISIBLE);
        phone_Text.setVisibility(View.VISIBLE);
        quesText.setVisibility(View.GONE);
        answerText.setVisibility(View.GONE);
        nextBtn_1.setVisibility(View.GONE);

        //输入框焦点事件设置
        phone_Text.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                } else {
                    //失去焦点的处理内容
//                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断输入的手机号是否注册
                if(!"".equals(phone_Text.getText().toString())){
                    UnitDto unit = unitDao.queryUnit(phone_Text.getText().toString());
                    if(unit.getPhone()!=null){
                        quesText.setVisibility(View.VISIBLE);
                        answerText.setVisibility(View.VISIBLE);
                        nextBtn_1.setVisibility(View.VISIBLE);
                        phone_tip_Text.setVisibility(View.GONE);
                        phone_Text.setVisibility(View.GONE);
                        nextBtn.setVisibility(View.GONE);
                        //问题设值
                        quesText.setText(unit.getQuestion());
                    }else{
                        Toast.makeText(SecretVerifyActivity.this,"手机号不存在，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SecretVerifyActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });

        nextBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断密保问题答案是否正确
                answer = answerText.getText().toString();
                UnitDto unit_1 = unitDao.queryUnit(phone_Text.getText().toString());
                if(answer.equals(unit_1.getAnswer())){
                    //跳转身份验证
                    Intent intent = new Intent(SecretVerifyActivity.this, changePwdActivity.class);
                    intent.putExtra("phone",phone_Text.getText().toString());
                    startActivity(intent);
                    finish();
                }else{
                    //提示输入错误
                    Toast.makeText(SecretVerifyActivity.this, "密保答案输入错误",Toast.LENGTH_SHORT).show();//显示信息;
                }
            }
        });

    }

    private void initDate() {

    }

}
