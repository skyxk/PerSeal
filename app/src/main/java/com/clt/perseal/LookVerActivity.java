package com.clt.perseal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.Dao.VerCodeDao;
import com.clt.perseal.Dto.VerCodeDto;
import com.clt.perseal.Util.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LookVerActivity extends AppCompatActivity {
    private WebView webView;
    private VerCodeDao verDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_ver);
        verDao = new VerCodeDao(LookVerActivity.this);



        //绑定XML中的ListView，作为Item的容器
        ListView list = (ListView) findViewById(R.id.verList);

        SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);

        List<VerCodeDto> verlist = verDao.queryVerCodes(preferences.getString("phone", null));
        //生成动态数组，并且转载数据
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

        for(int i=0;i<verlist.size();i++)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemText_vercode", verlist.get(verlist.size()-1-i).getVercode());
            map.put("ItemText_phone", verlist.get(verlist.size()-1-i).getPhone());
            mylist.add(map);
//            if(mylist.size()>4){
//                break;
//            }
        }
        //生成适配器，数组===》ListItem
        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
                mylist,//数据来源
                R.layout.vercode_list,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"ItemText_vercode","ItemText_phone"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.ItemText_vercode,R.id.ItemText_phone});

        //添加并且显示
        list.setAdapter(mSchedule);
    }

}
