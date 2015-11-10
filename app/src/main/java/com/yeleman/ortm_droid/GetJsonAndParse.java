package com.yeleman.ortm_droid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fad on 09/11/15.
 */
public class GetJsonAndParse extends AsyncTask<String, Void, Void> {

    private static final String TAG = Constants.getLogTag("GetJsonAndParse");
    private final ORTMVideoView ctxt;
    String data = null;
    JSONObject jObject;
    JSONParser jParser = new JSONParser();
    private boolean isOnline;
    private String rtsp_url = "";
    private ProgressDialog progressDialog;

    public GetJsonAndParse(ORTMVideoView myVideoView) {
        ctxt = myVideoView;
    }

    @Override
    protected void onPreExecute() {
        // Loading
       isOnline = Tools.isOnline(ctxt);
        if (!isOnline) {
            ctxt.finish();
            Tools.toast(ctxt, R.string.required_connexion_body);
            return;
        } else {
            Tools.lockScreenOrientation(ctxt);
            progressDialog = Tools.getStandardProgressDialog(ctxt,"", ctxt.getString(R.string.loading), false);
            progressDialog.show();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Log.d(TAG, params[0]);
            data = Tools.getFromUrl(params[0]);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground Exception" + e + "\nLe lien (url) vers la liste des articles est mort.");
            return null;
        }
        try {
            jObject = new JSONObject(data);
        } catch (JSONException e) {
            Log.d(TAG, "JSONObject-JSONException " + e.toString());
        }
        //Object rtsp = null;
        Object rtsp = jParser.parse(jObject);
        if (rtsp != null){
            rtsp_url = rtsp.toString();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (isOnline) {
            if (ctxt != null) {
                ctxt.setupUI(rtsp_url);
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                Tools.unlockScreenOrientation(ctxt);
            }
            // register to notifications if not already
        }
    }
}