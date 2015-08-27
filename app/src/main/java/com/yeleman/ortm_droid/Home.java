package com.yeleman.ortm_droid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;


public class Home extends Activity {

    private ImageButton tvButton;
    private ImageButton chaine1Button;
    private ImageButton chaine2Button;
    private ImageButton siteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setupUI();
    }

    public void setupUI() {

        tvButton = (ImageButton) findViewById(R.id.tvButton);
        chaine1Button = (ImageButton) findViewById(R.id.chaine1Button);
        chaine2Button = (ImageButton) findViewById(R.id.chaine2Button);
        siteButton = (ImageButton) findViewById(R.id.gotoSite);
        siteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ortm.ml"));
                startActivity(browserIntent);
            }
        });
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, MyWebView.class);
                a.putExtra("page", "tv");
                startActivity(a);
            }
        });
        chaine1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, MyWebView.class);
                a.putExtra("page", "ch1");
                startActivity(a);
            }
        });
        chaine2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Home.this, MyWebView.class);
                a.putExtra("page", "ch2");
                startActivity(a);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
