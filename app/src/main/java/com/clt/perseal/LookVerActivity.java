package com.clt.perseal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clt.perseal.Util.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LookVerActivity extends AppCompatActivity {
    private DBManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_ver);
        db  = new DBManager(LookVerActivity.this);
        //绑定XML中的ListView，作为Item的容器
        ListView list = (ListView) findViewById(R.id.verList);
        List<String> verlist = db.queryVer();
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<verlist.size();i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();

//            map.put("textView2", "验证码"+(i+1));

            map.put("ItemText", verlist.get(i));
            mylist.add(map);
            if(i==5){
                break;
            }
        }
        //生成适配器，数组===》ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
                mylist,//数据来源
                R.layout.vercode_list,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"ItemText"},

                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.ItemText});

        //添加并且显示
        list.setAdapter(mSchedule);
    }

}
