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
public class MyWebView extends Activity{

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

            Bundle extras = getIntent().getExtras();
            page = extras.getString("page");
            if (page.equals("tv")){
                url = "http://cdn.livestream.com/embed/maliactu?layout=4&height=340&width=560&autoplay=true";
            }if (page.equals("ch1")){
                url = "http://stream.rfi.fr/rfiafrique/all/rfiafrique-64k.mp3";
            }if (page.equals("ch2")){
                url = "http://stream.rfi.fr/rfiafrique/all/rfiafrique-64k.mp3";
            }

            final ProgressDialog pd = ProgressDialog.show(MyWebView.this, "", "Chargement en cours ...", true);

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
