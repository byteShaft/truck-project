package com.byteshaft.groupedirectouest;

import android.app.Application;
import android.content.Context;


public class AppGlobals extends Application {

    private static String latitude;
    private static String longitude;
    public static final String URL = "http://groupedirectouest.com/mobile/towme.php";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static void setLatitude(String latti) {
        latitude = latti;
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLongitude(String longi) {
        longitude = longi;
    }

    public static String getLongitude() {
        return longitude;
    }


}
