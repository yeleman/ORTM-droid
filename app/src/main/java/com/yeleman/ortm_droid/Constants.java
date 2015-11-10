package com.yeleman.ortm_droid;

/**
 * Created by fad on 09/11/15.
 */
public class Constants {
    private static final String TAG = Constants.getLogTag("Constants");

    public static final String getLogTag(String activity) {
        return String.format("ORTMLog-%s", activity);
    }
}
