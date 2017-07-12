package com.clt.perseal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.Util.DBManager;
import com.clt.perseal.WebSerivce.WsControler;

public class TemporaryActivity extends AppCompatActivity {
    public WebView webView = null;
    public SharedPreferences preferences;
    private String returnvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);

        returnvalue = WsControler.isActivateByPhone(preferences.getString("phone", null));

        if("ESSRET:0".equals(returnvalue)){


        }else if("ESSRET:1".equals(returnvalue)){

            new AlertDialog.Builder(TemporaryActivity.this).setTitle("您的手机号尚未激活")//设置对话框标题
                    .setMessage("是否前往激活")//设置显示的内容
                    .setPositiveButton("激活",new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                            //手机号未激活，跳转激活页。
                            Intent intent_1 = new Intent(TemporaryActivity.this, ActivateActivity.class);
                            startActivity(intent_1);
                            finish();
                        }
                    }).setNegativeButton("退出",new DialogInterface.OnClickListener() {//添加返回按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {//响应事件
                    finish();
                }
            }).show();//在按键响应事件中显示此对话框
        }

        //初始化webview
        initWebView(Constants.webUrl+"activateApp/temporaryAuth.jsp");
        //提供js调用
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }
    private void initWebView(String url) {
        webView = (WebView) findViewById(R.id.webView7);
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

            }
        });
    }
    class JSInterface {
        @JavascriptInterface

        //供H5页面调用
        public String getPhone(){
            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);
        }
    }
}
