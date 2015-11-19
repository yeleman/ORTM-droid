package com.yeleman.ortm_droid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;


public class Home extends Activity implements View.OnClickListener {

    private static final String TAG = Constants.getLogTag("Home");

    private LinearLayout tvButton;
    private LinearLayout siteButton;
    private ImageView playButtonCh1, playButtonCh2, pauseButtonCh1, pauseButtonCh2;
    public String urlCh2 = "http://ortmmali1.primcast.com:8364/shoutcast.com";
    public String urlCh1 = "http://ortmmali.primcast.com:9464/shoutcast.com";
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
        playButtonCh2 = (ImageView) findViewById(R.id.playButtonCh2);
        playButtonCh2.setOnClickListener(this);
        pauseButtonCh1 = (ImageView) findViewById(R.id.pauseButtonCh1);
        pauseButtonCh1.setOnClickListener(this);
        pauseButtonCh2 = (ImageView) findViewById(R.id.pauseButtonCh2);
        pauseButtonCh2.setOnClickListener(this);
        siteButton = (LinearLayout) findViewById(R.id.gotoSite);
        siteButton.setOnClickListener(this);
        displayMedaPlayerBtn(false, 0);
    }
    public void onClick(View v) {

        if (v == playButtonCh1) {
            initializeMediaPlayer(true, 1);
        }
        if (v == playButtonCh2) {
            initializeMediaPlayer(true, 2);
        }
        if (v == pauseButtonCh1) {
            initializeMediaPlayer(false, 1);
        }
        if (v == pauseButtonCh2) {
            initializeMediaPlayer(false, 2);
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
        Log.d(TAG, "displayMedaPlayerBtn play: " + play + "  ch: " + ch);
        if (ch == 1) {
            Log.d(TAG, "Ch1 " + ch);
            playOrPause(play, pauseButtonCh1, playButtonCh1);
        } else if (ch == 2) {
            Log.d(TAG, "Ch2 " + ch);
            playOrPause(play, pauseButtonCh2, playButtonCh2);
        } else {
            Log.d(TAG, "Ch! " + ch);
            goneOrVisible(playButtonCh1 , pauseButtonCh1);
            goneOrVisible(playButtonCh2 , pauseButtonCh2);
        }
    }
    public void playOrPause (boolean v, ImageView pauseButtonCh, ImageView playButtonCh){
        if (v) {
            goneOrVisible(pauseButtonCh, playButtonCh);
        } else {
            goneOrVisible(playButtonCh, pauseButtonCh);
        }
    }
    public void goneOrVisible(ImageView vbutton, ImageView gbutton) {
        vbutton.setVisibility(View.VISIBLE);
        gbutton.setVisibility(View.GONE);
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
    private void stopPlaying() {
        Log.d(TAG, "stopPlaying");
        try {
            if (player.isPlaying()) {
                player.stop();
                player.release();
            }
        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
    }
    private void initializeMediaPlayer(boolean play, int ch) {
        Log.d(TAG, "initializeMediaPlayer");
        stopPlaying();
        int unCh;
        if (ch == 1){
            urlCh = urlCh1;
            unCh = 2;
        } else {
            urlCh = urlCh2;
            unCh = 1;
        }
        if (play){
            player = new MediaPlayer();
            displayMedaPlayerBtn(true, ch);
            try {
                player.setDataSource(urlCh);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, "IllegalArgumentException" + e.toString());
            } catch (IllegalStateException e) {
                Log.d(TAG, "IllegalStateException" + e.toString());
            } catch (IOException e) {
                Log.d(TAG, "IOException" + e.toString());
            }
            startPlaying();
        }else {
            displayMedaPlayerBtn(false, ch);
        }
        displayMedaPlayerBtn(false, unCh);
        Log.d(TAG, "fin initializeMediaPlayer");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Intent a = new Intent(Home.this, About.class);
            startActivity(a);
        }if (id == R.id.action_chare) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = Constants.share;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, R.string.app_name);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Partager ..."));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK :
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.exit))
                        .setMessage(getString(R.string.exit_confirmed))
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.exit(0);
                                    }})
                        .setNegativeButton(android.R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {}
                                }).create().show();
                return true;
        }
        return false;
    }
}