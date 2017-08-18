package com.clt.perseal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.Util.DBHelper;
import com.clt.perseal.Util.DBManager;
import com.clt.perseal.WebSerivce.WsControler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public WebView webView = null;
    public TextView headPhone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化webview
        initWebView(Constants.webUrl+"activateApp/activateIndex.jsp");
        //提供js调用
        webView.addJavascriptInterface(new JSInterface1(),"Android");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initWebView(String url) {
        webView = (WebView) findViewById(R.id.webView5);
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this).setTitle("确认退出吗？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击“确认”后的操作
//                            MainActivity.this.finish();
                            android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
                            System.exit(0);

                        }
                    })
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 点击“返回”后的操作,这里不设置没有任何操作
                        }
                    }).show();
//            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
        String phone = preferences.getString("phone", null);

        headPhone = (TextView) findViewById(R.id.textView);
        headPhone.setText(phone);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            //跳转激活
//            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
//            Bundle bundle = new Bundle();
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        Intent intent = new Intent(MainActivity.this, ChildWebViewActivity.class);
        Bundle bundle = new Bundle();
        if (id == R.id.nav_activate) {
            //激活
            bundle.putString("url","activateApp/login.jsp");
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (id == R.id.nav_application) {
            //申请印章
            Intent intent1 = new Intent(MainActivity.this, ApplicationActivity.class);
            Bundle bundle1 = new Bundle();
            intent1.putExtras(bundle1);
            startActivity(intent1);
//            Intent intent_8 = new Intent(MainActivity.this, ChildWebViewActivity.class);
//            Bundle bundle_8 = new Bundle();
//            bundle_8.putString("url","activateApp/sealApplyLogin.jsp");
//            intent_8.putExtras(bundle_8);
//            startActivity(intent_8);
        } else if (id == R.id.nav_ver) {
            //跳转验证码
            bundle.putString("url","activateApp/showPinLogin.jsp");
            intent.putExtras(bundle);
            startActivity(intent);

        }
        else if (id == R.id.nav_down_code) {
            //下载码
            Intent intent_7 = new Intent(MainActivity.this, LookVerActivity.class);
            Bundle bundle_7 = new Bundle();
            intent_7.putExtras(bundle_7);
            startActivity(intent_7);
        }
        else if (id == R.id.nav_showShareSeals) {
            //个人授权记录
            bundle.putString("url","activateApp/showShareSealsLogin.jsp");
            intent.putExtras(bundle);
            startActivity(intent);

        }

        else if (id == R.id.nav_addempnum) {
            //绑定专卖系统账号
//            Intent intent_9 = new Intent(MainActivity.this, AddEmpNumActivity.class);
//            Bundle bundle_9 = new Bundle();
//            intent_9.putExtras(bundle_9);
//            startActivity(intent_9);
            bundle.putString("url","activateApp/addEmpNum.jsp");
            intent.putExtras(bundle);
            startActivity(intent);
        }

        else if (id == R.id.nav_showlog) {
            //查看签章日志
            bundle.putString("url","activateApp/showSignatureLogLogin.jsp");
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.nav_temporary) {
            //临时授权
//            Intent intent_4 = new Intent(MainActivity.this, TemporaryActivity.class);
//            Bundle bundle_4 = new Bundle();
//            intent_4.putExtras(bundle_4);
//            startActivity(intent_4);
            bundle.putString("url","activateApp/temporaryAuth.jsp");
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_editPsw) {

            bundle.putString("url","activateApp/editPsw.jsp");
            intent.putExtras(bundle);
            startActivity(intent);

        }
        else if (id == R.id.nav_sharesealsLog) {
            bundle.putString("url","activateApp/showShareSealsLogLogin.jsp");
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (id == R.id.nav_exit) {
            //清楚SharedPreferences的登录信息
            SharedPreferences preferences = this.getSharedPreferences("perseal", Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class JSInterface1 {

        //供H5页面调用手机号
        @JavascriptInterface
        public String getPhone(){
            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);
        }
        //已经激活返回ESSRET:0未激活返回ESSRET:1  手机号未传入返回ESSRET:phoneIsNull
        @JavascriptInterface
        public void activiteState(String state){
            //第一个参数 指定名称 不需要写后缀名 第二个参数文件的操作模式
            SharedPreferences preferences = MainActivity.this.getSharedPreferences("perseal", Context.MODE_PRIVATE);
            //取到编辑器
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("activiteState", state);
            //把数据提交给文件中
            editor.commit();
        }
    }
}
