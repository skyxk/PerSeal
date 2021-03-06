package com.clt.perseal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.Dao.VerCodeDao;
import com.clt.perseal.Dto.VerCodeDto;
import com.clt.perseal.Util.StatusUtil;

public class ChildWebViewActivity extends AppCompatActivity {
    public WebView webView = null;
    private SharedPreferences preferences;
    public String url  =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_web_view);

        Intent intent = getIntent();
        url = intent.getExtras().getString("url");

        if(!"activateApp/login.jsp".equals(url)){
            new StatusUtil(ChildWebViewActivity.this).isActivate();
        }


        preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
        initWebView(preferences.getString("ip", null)+url);
        //提供js调用
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }

    private void initWebView(String url) {
        webView = (WebView) findViewById(R.id.webView_child);
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
                preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
                webView.loadUrl("javascript:getPhoneAndroid("+"'"+preferences.getString("phone", null)+"'"+")");
            }
        });
    }
    class JSInterface {
        @JavascriptInterface

        //供H5页面调用
        public void insertVer(String ver){
            //获取当前手机号
            preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            String phone = preferences.getString("phone", null);
            VerCodeDao verDao = new VerCodeDao(ChildWebViewActivity.this);
            VerCodeDto verDto = new VerCodeDto();
            verDto.setPhone(phone);
            verDto.setVercode(ver);
            //添加验证码
            verDao.addVerCode(verDto);
        }
        @JavascriptInterface
        public String getPhone(){
            preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);
        }
    }
}
