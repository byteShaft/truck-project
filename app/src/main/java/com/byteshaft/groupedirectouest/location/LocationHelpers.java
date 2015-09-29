package com.byteshaft.groupedirectouest.location;


import android.content.Context;
import android.content.ContextWrapper;
import android.location.Location;
import android.os.Handler;

import com.google.android.gms.location.LocationRequest;

public class LocationHelpers extends ContextWrapper {

    private LocationRequest mLocationRequest;
    private Handler mHandler;

    public LocationHelpers(Context base) {
        super(base);
    }

    public static String getLongitudeAsString(Location location) {
        return String.valueOf(location.getLongitude());
    }

    public static String getLatitudeAsString(Location location) {
        return String.valueOf(location.getLatitude());
    }

    public LocationRequest getLocationRequest() {
        long INTERVAL = 0;
        long FASTEST_INTERVAL = 0;
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        return mLocationRequest;
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }
}
