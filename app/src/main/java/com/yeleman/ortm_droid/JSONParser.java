package com.yeleman.ortm_droid;

import android.util.Log;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fad on 09/11/15.
 */
public class JSONParser {
    private static final String TAG = Constants.getLogTag("JSONParser");

    // Receives a JSONObject and returns a list
    public Object parse(JSONObject jObject){

        Log.d(TAG, "JSONParser");
        JSONObject jstreamInfo = null;
        try {
            jstreamInfo = (JSONObject) jObject.get("streamInfo");
        } catch (JSONException e) {
            Log.d(TAG, "jObjectgetJSONArray-JSONException " + e.toString());
        } catch (Exception e){
            Log.d(TAG, "Exception " + e.toString());
        }
        String url = null;
        if (jstreamInfo != null){
            try {
                url = jstreamInfo.get("rtsp_url").toString();
            } catch (JSONException e) {
                Log.d(TAG, "Get-rtsp_url-JSONException " + e.toString());
            }
        }
        Log.d(TAG, "return JSONParser");
        return url;
    }
}
