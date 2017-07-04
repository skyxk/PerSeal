package com.clt.perseal;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.perseal.Util.Base64Utils;
import com.clt.perseal.Util.DBManager;
import com.clt.perseal.Util.SimpleDesede;

public class RegisterActivity extends AppCompatActivity {
    private EditText phText ;
    private EditText pwText ;
    private EditText rePwText ;
    private EditText editanswer;
    private TextView errPwd ;
    private Button regBtn ;
    private String idStr;
    //声明spinner对象
    private Spinner spinner;
    //显示的数组
    private String arr[]=new String[]{
            "您的身份证号",
            "您父母的名字",
            "您就读于哪一所中学",
    };
    private String question;
    private String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化组件
        phText = (EditText)findViewById(R.id.editphone);
        pwText = (EditText)findViewById(R.id.editpwd);
        rePwText = (EditText)findViewById(R.id.editrepwd);
        editanswer = (EditText)findViewById(R.id.editanswer);
        errPwd = (TextView)findViewById(R.id.texterror);
        errPwd.setVisibility(View.GONE);
        regBtn = (Button)findViewById(R.id.regBtn) ;


        //根据id获取对象
        spinner=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr){
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
//                    设置spinner展开的Item布局
                    convertView = getLayoutInflater().inflate(R.layout.adapter_mytopactionbar_spinner_item, parent, false);
                }
                TextView spinnerText=(TextView)convertView.findViewById(R.id.spinner_textView);
                spinnerText.setText(getItem(position));
                return convertView;
            }
        };
        //设置显示的数据
        spinner.setAdapter(arrayAdapter);

        //注册事件
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner=(Spinner) parent;
                question = spinner.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(), question, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



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
                answer = editanswer.getText().toString();
                //注册按钮点击事件
                if(!"".equals(phText.getText().toString())){
                    if(!"".equals(answer)){
                        if((pwText.getText().toString()).equals(rePwText.getText().toString())&& !"".equals(pwText.getText().toString())){
                            //设置提示信息不可见
                            errPwd.setVisibility(View.GONE);

                            //注册手机号
                            DBManager db = new DBManager(RegisterActivity.this);
                            db.insert("", (phText.getText()).toString(),
                                    SimpleDesede.encryptToDB((pwText.getText()).toString()),idStr,question,answer);


                            new AlertDialog.Builder(RegisterActivity.this).setTitle("恭喜您注册成功")//设置对话框标题

                                    .setMessage("请您登录")//设置显示的内容
                                    .setPositiveButton("登录",new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            // TODO Auto-generated method stub
                                            //转到登录页面
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            intent.putExtra("phone",(phText.getText()).toString());
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
                            //设置提示信息可见
                            errPwd.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "密保答案不能为空", Toast.LENGTH_LONG).show();
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
