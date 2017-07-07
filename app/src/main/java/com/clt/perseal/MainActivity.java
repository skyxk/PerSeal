package com.clt.perseal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.clt.perseal.Constants.Constants;
import com.clt.perseal.Util.DBHelper;
import com.clt.perseal.Util.DBManager;

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
        webView = (WebView) findViewById(R.id.webView1);
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
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
            //页面加载结束后可执行
//
//            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //跳转激活
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            //跳转激活
            Intent intent = new Intent(MainActivity.this, ActivateActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            //跳转申请印章
            Intent intent_2 = new Intent(MainActivity.this, ApplicationActivity.class);
            Bundle bundle_2 = new Bundle();
            intent_2.putExtras(bundle_2);
            startActivity(intent_2);

        } else if (id == R.id.nav_manage) {
            //跳转验证码查看
            Intent intent_3 = new Intent(MainActivity.this, LookVerActivity.class);
            Bundle bundle_3 = new Bundle();
            intent_3.putExtras(bundle_3);
            startActivity(intent_3);

        } else if (id == R.id.nav_temporary) {
            //跳转验证码查看
            Intent intent_4 = new Intent(MainActivity.this, TemporaryActivity.class);
            Bundle bundle_4 = new Bundle();
            intent_4.putExtras(intent_4);
            startActivity(intent_4);

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    class JSInterface1 {
        @JavascriptInterface

        //供H5页面调用
        public String getPhone(){
            SharedPreferences preferences = getSharedPreferences("perseal", Context.MODE_PRIVATE);
            return preferences.getString("phone", null);
        }
    }
}
