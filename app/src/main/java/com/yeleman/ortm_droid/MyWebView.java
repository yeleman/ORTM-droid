package com.yeleman.ortm_droid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.novoda.merlin.Merlin;

/**
 * Created by fad on 21/08/15.
 */
public class MyWebView extends Activity{

    private WebView webView;
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
        if (!isOnline()) {
            //Dialog

            alertDialog = new AlertDialog.Builder(MyWebView.this).create();
            alertDialog.setTitle("Problème de connexion");
            alertDialog.setIcon(R.mipmap.ic_launcher);
            alertDialog.setMessage("Une connexion Internet est requise.\nVeuillez l'activer et réessayer.");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.setButton("Reessayer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    setupUI();
                }
            });
            alertDialog.show();
        } else {

            //final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Chargement en cours ...", true);

            webView = (WebView) findViewById(R.id.webView);
            //webView.getSettings().setLoadWithOverviewMode(true);
            //webView.getSettings().setUseWideViewPort(true);
            //webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    MyWebView.this.setProgress(progress * 1000);
                }
            });

            Bundle extras = getIntent().getExtras();
            page = extras.getString("page");
            if (page.equals("tv")){
                url = "http://www.ortm.info/ortm-en-live/";
            }if (page.equals("ch1")){
                url = "http://www.ortm.info/radio-national-en-live/";
            }if (page.equals("ch2")){
                url = "http://www.ortm.info/chaine-ii-en-live/";
            }
            webView.loadUrl(url);
            webView.reload();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
