package com.clt.perseal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.Util.DBManager;

public class ActivateActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        initWebView(Constants.webUrl+"activateApp/login.jsp");
        webView.addJavascriptInterface(new JSInterface (),"Android");
    }
    private void initWebView(String url){
        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    class JSInterface {
        @JavascriptInterface
        public void insertPhone(String name,String phone, String pwd){
            //创建DBManager对象
            DBManager DBManager = new DBManager(ActivateActivity.this);
//            DBManager.insert(name,phone,pwd);
//            dbHelper.insert("chen123","13261583005");
        }
        @JavascriptInterface
        public String getPhone(){
            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);

        }
    }
}
