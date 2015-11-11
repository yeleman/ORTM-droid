package com.yeleman.ortm_droid;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;


public class Home extends Activity implements View.OnClickListener {

    private static final String TAG = Constants.getLogTag("Home");

    private LinearLayout tvButton;
    private LinearLayout siteButton;
    private ImageView playButtonCh1, playButtonCh2, pauseButtonCh1, pauseButtonCh2;
    public String urlCh1 = "http://ortmmali.primcast.com:9464/shoutcast.com";
    public String urlCh2 = "http://usa8-vn.mixstream.net:8138";
    private MediaPlayer player;
    private String urlCh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setupUI();
    }

    public void setupUI() {
        tvButton = (LinearLayout) findViewById(R.id.tvButton);
        tvButton.setOnClickListener(this);
        playButtonCh1 = (ImageView) findViewById(R.id.playButtonCh1);
        playButtonCh1.setOnClickListener(this);
        pauseButtonCh1 = (ImageView) findViewById(R.id.pauseButtonCh1);
        pauseButtonCh1.setOnClickListener(this);
        playButtonCh2 = (ImageView) findViewById(R.id.playButtonCh2);
        playButtonCh2.setOnClickListener(this);
        pauseButtonCh2 = (ImageView) findViewById(R.id.pauseButtonCh1);
        pauseButtonCh2.setOnClickListener(this);
        siteButton = (LinearLayout) findViewById(R.id.gotoSite);
        siteButton.setOnClickListener(this);
        displayMedaPlayerBtn(false, 0);
    }

    public void onClick(View v) {
        if (v == playButtonCh1) {
            player = new MediaPlayer();
            initializeMediaPlayer(1);
        }
        if (v == playButtonCh2) {
            player = new MediaPlayer();
            initializeMediaPlayer(2);
        }
        if (v == pauseButtonCh1) {
            stopPlaying(1);
        }
        if (v == pauseButtonCh2) {
            stopPlaying(2);
        }
        if (v == siteButton) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ortm.ml"));
            startActivity(browserIntent);
        }
        if (v == tvButton) {
            Intent a = new Intent(Home.this, ORTMVideoView.class);
            startActivity(a);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void displayMedaPlayerBtn(boolean play, int ch) {
        Log.d(TAG, "displayMedaPlayerBtn play: "+ play + "  ch: " + ch);
        if (ch == 1) {
            pauseButtonCh1.setVisibility(View.VISIBLE);
            playButtonCh1.setVisibility(View.GONE);
            if (play) {
                playButtonCh1.setVisibility(View.VISIBLE);
            }
        } else if (ch == 2){
            playButtonCh2.setVisibility(View.VISIBLE);
            pauseButtonCh2.setVisibility(View.GONE);
            if (play) {
                pauseButtonCh2.setVisibility(View.VISIBLE);
            }
        } else {
            pauseButtonCh2.setVisibility(View.GONE);
            playButtonCh2.setVisibility(View.VISIBLE);
            pauseButtonCh1.setVisibility(View.GONE);
            playButtonCh1.setVisibility(View.VISIBLE);
        }
    }

    private void startPlaying() {
        Log.d(TAG, "startPlaying");
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "Player");
                player.start();
            }
        });
    }
    private void stopPlaying(int ch) {
        Log.d(TAG, "stopPlaying");
        try {
            if (player.isPlaying()) {
                player.stop();
                player.release();
            }
        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
        displayMedaPlayerBtn(true, ch);
    }
    private void initializeMediaPlayer(int ch) {
        Log.d(TAG, "initializeMediaPlayer");
        displayMedaPlayerBtn(true, ch);
        int unCh;
        if (ch == 1){
            urlCh = urlCh1;
            unCh = 2;
        } else {
            urlCh = urlCh2;
            unCh = 1;
        }
        stopPlaying(unCh);
        try {
            player.setDataSource(urlCh);
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "IllegalArgumentException" + e.toString());
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException" + e.toString());
        } catch (IOException e) {
            Log.d(TAG, "IOException" + e.toString());
        }
        Log.d(TAG, "fin");
        startPlaying();
    }
}
