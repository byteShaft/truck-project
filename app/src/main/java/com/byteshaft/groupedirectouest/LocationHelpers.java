package com.byteshaft.groupedirectouest;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;

public class LocationHelpers extends ContextWrapper {

    private LocationRequest mLocationRequest;
    private Handler mHandler;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private final int UNIQUE_ID_FOR_ALARM = 21;

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

    public void setLocationAlarm(int time) {
        mAlarmManager = getAlarmManager();
        Intent intent = getIntent();
        mPendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), UNIQUE_ID_FOR_ALARM, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, mPendingIntent);
    }

    private Intent getIntent() {
        return new Intent("com.byteshaft.LOCATION_ALARM");
    }

    public void cancelAlarmIfSet() {
        boolean isAlarmSet = isAlarmSet();
        if (isAlarmSet) {
            mAlarmManager = getAlarmManager();
            mAlarmManager.cancel(mPendingIntent);
            Log.i("LocationLogger", "Alarm Removed");
        }
    }

    private boolean isAlarmSet() {
        Intent intent = getIntent();
        return (PendingIntent.getBroadcast(getApplicationContext(), UNIQUE_ID_FOR_ALARM,
                intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) getSystemService(ALARM_SERVICE);
    }
}
