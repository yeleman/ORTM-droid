package com.yeleman.ortm_droid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by fad on 08/11/14.
 */
public class About extends Activity {

    private Button versionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("À propos");
        setContentView(R.layout.about);
        setupUI();
    }

    protected void setupUI() {
        TextView about_description = (TextView) findViewById(R.id.appLongDescriptionLabel);
        about_description.setText(Html.fromHtml(getString(R.string.about_long_description)));
        versionButton = (Button) findViewById(R.id.updateVersionBtt);
        versionButton.setText(String.format(
            getString(R.string.version_button_label),
            BuildConfig.VERSION_NAME));
        versionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String market_uri = Constants.app_market_url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(market_uri));
                startActivity(intent);
            }
        });
    }

}
