package com.afchine.beijing.billboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;
import com.afchine.beijing.billboard.NetWorkStateReceiver;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    private Handler handler = new Handler() ;
    private WebView myWebView;
    private ImageView myimageView;
    private Runnable task = new Runnable()
    {
        public void run()
        {
            //TODO Auto-generated method stub
            handler.postDelayed(this, 600*1000);

            myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            myWebView.loadUrl("http://192.168.1.8/vscreen-jt/");
            System.out.println("OMG!!It's reloaded!");


        }
    };
    NetWorkStateReceiver netWorkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myimageView = (ImageView)findViewById(R.id.nonetwork_img2);

        myWebView = (WebView) findViewById(R.id.myWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.loadUrl("http://192.168.1.8/vscreen-jt/");

        System.out.println("OMG!! It's ON!!" );


        myWebView.setWebViewClient(new WebViewClient() {
                                       @Override
                                       public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                           view.loadUrl(url);

                                           return true;
                                       }
                                        @Override
                                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                            super.onReceivedError(view, request, error);

                                        }
                                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                                            Log.e("onReceivedError", "onReceivedError = " + errorCode);


                                            myWebView.setVisibility(View.GONE);
                                            myimageView.setVisibility(View.VISIBLE);

                                        }

                                       @Override
                                       public void onPageFinished(WebView view, String url) {
                                           super.onPageFinished(view, url);
                                           System.out.println("OMG!!!!!!!!!!!!!!!" );


                                       }
                                   }

        );

        myWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                System.out.println("title:"+title);
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("not available")){
                    System.out.println("error");
                    myWebView.setVisibility(View.GONE);
                    myimageView.setVisibility(View.VISIBLE);
                }
                else{
                    System.out.println("normal");
                    myWebView.setVisibility(View.VISIBLE);
                    myimageView.setVisibility(View.GONE);
                    //myWebView.loadUrl("about:blank");
                }
            }
        });

        System.out.println("OMG!!It's Jumping!" );
        //myWebView.reload();

        handler.post(task);

    }

    @Override
    protected void onResume() {
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        System.out.println("注册");
        super.onResume();
    }

    //onPause()方法注销
    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateReceiver);
        System.out.println("注销");
        super.onPause();
    }



}

