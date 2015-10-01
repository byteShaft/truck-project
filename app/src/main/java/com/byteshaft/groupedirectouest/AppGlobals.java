package com.byteshaft.groupedirectouest;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;


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

    public boolean isAnyLocationServiceAvailable() {
        LocationManager locationManager = getLocationManager();
        return isGpsEnabled(locationManager) || isNetworkBasedGpsEnabled(locationManager);
    }

    private LocationManager getLocationManager() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean isGpsEnabled(LocationManager locationManager) {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkBasedGpsEnabled(LocationManager locationManager) {
        return locationManager.isProviderEnabled((LocationManager.NETWORK_PROVIDER));
    }

}
