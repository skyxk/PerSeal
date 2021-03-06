package com.clt.perseal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.perseal.Dao.UnitDao;
import com.clt.perseal.Dto.UnitDto;
import com.clt.perseal.Util.StatusUtil;

public class RegisterActivity extends AppCompatActivity {
    private EditText phText ;
    private EditText pwText ;
    private EditText rePwText ;
    private EditText editanswer;
    private TextView errPwd ;
    private Button regBtn ;

    private UnitDao unitDao;
    //声明spinner对象
    private Spinner spinner;
    //显示的数组
    private String arr[]=new String[]{
            "您的身份证号",
            "您父母的名字",
            "您就读于哪一所中学",
    };

    private String question = "您的身份证号";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        unitDao = new UnitDao(RegisterActivity.this);

        initView();

        initDate();

        new StatusUtil(RegisterActivity.this).Update();

    }
    /**
     * 初始化view
     */
    private void initView() {

        phText = (EditText)findViewById(R.id.editphone);

        pwText = (EditText)findViewById(R.id.editpwd);

        rePwText = (EditText)findViewById(R.id.editrepwd);

        editanswer = (EditText)findViewById(R.id.editanswer);

        errPwd = (TextView)findViewById(R.id.texterror);

        regBtn = (Button)findViewById(R.id.regBtn) ;

        //输入框焦点时间设置
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


        //选择框部分
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //注册按钮点击事件
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //注册按钮点击事件
                if(!"".equals(phText.getText().toString())){
                    if(!"".equals(editanswer.getText().toString())){
                        if((pwText.getText().toString()).equals(rePwText.getText().toString())&& !"".equals(pwText.getText().toString())){
                            if(unitDao.queryUnit(phText.getText().toString()).getPhone()==null){

                                //设置提示信息不可见
                                errPwd.setVisibility(View.GONE);

                                //创建实例
                                UnitDto unitdto = new UnitDto();
                                unitdto.setPhone(phText.getText().toString());
                                unitdto.setPassword(pwText.getText().toString());
                                unitdto.setQuestion(question);
                                unitdto.setAnswer(editanswer.getText().toString());
                                //数据库中加入记录
                                unitDao.addUnit(unitdto);

                                new AlertDialog.Builder(RegisterActivity.this).setTitle("恭喜您注册成功")//设置对话框标题
                                        .setMessage("请您登录")//设置显示的内容
                                        .setPositiveButton("登录",new DialogInterface.OnClickListener() {//添加确定按钮
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                //转到登录页面
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                            intent.putExtra("phone",(phText.getText()).toString());
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
                                Toast.makeText(getApplicationContext(), "该手机号已存在，请重新填写！", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            //设置提示信息可见
                            errPwd.setVisibility(View.VISIBLE);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "密保答案不能为空", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * 初始化date
     */
    private void initDate() {

        errPwd.setVisibility(View.GONE);

    }

}
