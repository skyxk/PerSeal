package com.clt.perseal.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.R;

public class ShowShareSealsLogActivity extends AppCompatActivity {
    private WebView webView;
    public SharedPreferences preferences;
    private String returnvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_share_seals_log);

        initWebView(Constants.webUrl+"activateApp/showShareSealsLogLogin.jsp");
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }

    private void initWebView(String url) {
        webView = (WebView) findViewById(R.id.webView10);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    class JSInterface {

        @JavascriptInterface
        public String getPhone(){

            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);

        }
        @JavascriptInterface
        public void activiteState(String state){

            //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
            SharedPreferences preferences = ShowShareSealsLogActivity.this.getSharedPreferences("perseal", Context.MODE_PRIVATE);

            //取到编辑器
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("activiteState", state);
            //把数据提交给文件中
            editor.commit();
        }
    }
}
