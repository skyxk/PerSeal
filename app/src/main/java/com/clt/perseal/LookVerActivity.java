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
//        verDao = new VerCodeDao(LookVerActivity.this);
//
//
//
//        //绑定XML中的ListView，作为Item的容器
//        ListView list = (ListView) findViewById(R.id.verList);
//
//        SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
//
//        List<VerCodeDto> verlist = verDao.queryVerCodes(preferences.getString("phone", null));
//        //生成动态数组，并且转载数据
//        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
//
//        for(int i=0;i<verlist.size();i++)
//        {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ItemText_vercode", verlist.get(verlist.size()-1-i).getVercode());
//            map.put("ItemText_phone", verlist.get(verlist.size()-1-i).getPhone());
//            mylist.add(map);
////            if(mylist.size()>4){
////                break;
////            }
//        }
//        //生成适配器，数组===》ListItem
//        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
//                mylist,//数据来源
//                R.layout.vercode_list,//ListItem的XML实现
//                //动态数组与ListItem对应的子项
//                new String[] {"ItemText_vercode","ItemText_phone"},
//                //ListItem的XML文件里面的两个TextView ID
//                new int[] {R.id.ItemText_vercode,R.id.ItemText_phone});
//
//        //添加并且显示
//        list.setAdapter(mSchedule);




        //初始化webview
        initWebView(Constants.webUrl+"activateApp/showDownVerLogin.jsp");
//        initWebView("http://www.baidu.com");
        //提供js调用
        webView.addJavascriptInterface(new JSInterface(),"Android");

    }




    private void initWebView(String url) {
        webView = (WebView) findViewById(R.id.webView_ver);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
//        webSettings.setBuiltInZoomControls(true);
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                CharSequence pnotfound = "404";
                if (title.contains(pnotfound)) {
                    view.stopLoading();
                    view.loadUrl("file:///android_asset/error.html");
                }
            }

        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // Handle the error
                view.loadUrl("file:///android_asset/error.html");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载结束后可执行
                SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
                webView.loadUrl("javascript:getPhoneAndroid("+"'"+preferences.getString("phone", null)+"'"+")");
            }
        });
    }
    class JSInterface {
        @JavascriptInterface

        //供H5页面调用
        public void insertVer(String ver){
            //获取当前手机号
            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            String phone = preferences.getString("phone", null);
            VerCodeDao verDao = new VerCodeDao(LookVerActivity.this);
            VerCodeDto verDto = new VerCodeDto();
            verDto.setPhone(phone);
            verDto.setVercode(ver);
            //添加验证码
            verDao.addVerCode(verDto);
        }
        @JavascriptInterface
        public String getPhone(){
            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);
        }
    }



}
