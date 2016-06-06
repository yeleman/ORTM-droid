package ml.ortm.ortm_droid;

/**
 * Created by fad on 09/11/15.
 */

public class Constants {

    static String app_market_url = "market://details?id=com.yeleman.ortmdroid";
    public static final String share = "https://play.google.com/store/apps/details?id=com.yeleman.ortm_droid";
    public static final String getLogTag(String activity) {
        return String.format("ORTMLog-%s", activity);
    }
}
