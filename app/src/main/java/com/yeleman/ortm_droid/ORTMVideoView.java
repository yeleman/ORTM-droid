package com.yeleman.ortm_droid;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;


/**
 * Created by fad on 09/11/15.
 */
public class ORTMVideoView extends Activity{

    private static final String TAG = Constants.getLogTag("GetJsonAndParse");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        new GetJsonAndParse(ORTMVideoView.this).execute("http://livestream.com/api/accounts/ortm/events/ortm/viewing_info");
    }

    public void setupUI(String url) {
        if (url.equals("")){
            finish();
            Tools.toast(this, R.string.offlive);
        }
        VideoView vidView = (VideoView)findViewById(R.id.myVideo);
        Uri vidUri = Uri.parse(url);
        vidView.setVideoURI(vidUri);
        vidView.start();
    }
}

