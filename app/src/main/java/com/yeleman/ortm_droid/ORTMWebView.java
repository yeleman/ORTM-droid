package com.yeleman.ortm_droid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
/**
 * Created by fad on 21/08/15.
 */
public class ORTMWebView extends Activity{

    private WebView mWebView;
    private String page;
    private String url;

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
                url = "http://ortmmali.primcast.com:9464/shoutcast.com";
            }if (page.equals("ch2")){
                url = "http://ortmmali.primcast.com:9464/shoutcast.com";
            }

            final ProgressDialog pd = ProgressDialog.show(ORTMWebView.this, "", "Chargement en cours ...", true);

            mWebView = (WebView) findViewById(R.id.webView);
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    //Also you can show the progress percentage using integer value 'progress'
                    if (!pd.isShowing()) {
                        pd.show();
                    }
                    if (progress == 100 && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });

            String streaming_audio = "<html><audio src='http://ortmmali.primcast.com:9464/shoutcast.com' autoplay='true' /></html>>";
            mWebView.loadDataWithBaseURL(null, streaming_audio, "text/html", "UTF-8", null);
            mWebView.reload();

        }
    }
}
