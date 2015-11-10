package com.yeleman.ortm_droid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.novoda.merlin.Merlin;

/**
 * Created by fad on 21/08/15.
 */
public class ORTMWebView extends Activity{

    private WebView mWebView;
    private String page;
    private String url;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        setupUI();
    }

    protected void setupUI() {
        if (!Tools.isOnline(this)) {
            mWebView = (WebView) findViewById(R.id.webView);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl("file:///android_asset/landing-android.html");
        } else {
            Bundle extras = getIntent().getExtras();
            page = extras.getString("page");
            if (page.equals("ch1")){
                url = "http://stream.rfi.fr/rfiafrique/all/rfiafrique-64k.mp3";
            }if (page.equals("ch2")){
                url = "http://stream.rfi.fr/rfiafrique/all/rfiafrique-64k.mp3";
            }

            final ProgressDialog pd = ProgressDialog.show(ORTMWebView.this, "", "Chargement en cours ...", true);

            mWebView = (WebView) findViewById(R.id.webView);
            mWebView.getSettings().setJavaScriptEnabled(true);
            //mWebView.getSettings().setPluginsEnabled(true);

            mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    //Also you can show the progress percentage using integer value 'progress'
                    if(!pd.isShowing()){
                        pd.show();
                    }
                    if (progress == 100&&pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
            mWebView.loadUrl(url);
            mWebView.reload();

        }
    }
}
