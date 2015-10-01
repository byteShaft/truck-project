package com.byteshaft.groupedirectouest.location;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.byteshaft.groupedirectouest.AppGlobals;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

        private GoogleApiClient mGoogleApiClient;
        private Location mLocation;
        private LocationRequest mLocationRequest;
        LocationHelpers mLocationHelpers;
        private IntentFilter alarmIntent = new IntentFilter("com.byteshaft.LOCATION_ALARM");
        private int mLocationRecursionCounter;
        private int mLocationChangedCounter;
        private static LocationService sInstance;

        private BroadcastReceiver mLocationRequestAlarmReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                        startLocationUpdate();
                }
        };
        private Runnable mLocationRunnable = new Runnable() {
                @Override
                public void run() {
                        String LOG_TAG = "LocationLogger";
                        if (mLocation == null && mLocationRecursionCounter > 24) {
                                mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                                if (mLocation != null) {
                                        AppGlobals.setLatitude(LocationHelpers.getLatitudeAsString(mLocation));
                                        AppGlobals.setLongitude(LocationHelpers.getLongitudeAsString(mLocation));

                                        Log.w(LOG_TAG, "Failed to get location current location, saving last known location");
                                        stopLocationUpdate();
                                } else {
                                        Log.e(LOG_TAG, "Failed to get location");
                                        stopLocationUpdate();
                                }
                        } else if (mLocation == null) {
                                acquireLocation();
                                mLocationRecursionCounter++;
                                Log.i(LOG_TAG, "Tracker Thread Running: " + mLocationRecursionCounter);
                        } else {
                                AppGlobals.setLatitude(LocationHelpers.getLatitudeAsString(mLocation));
                                AppGlobals.setLongitude(LocationHelpers.getLongitudeAsString(mLocation));
                                stopLocationUpdate();
                        }
                }
        };

        @Override
        public void onDestroy() {
                super.onDestroy();
                mLocationHelpers.getHandler().removeCallbacks(mLocationRunnable);
                reset();
                unregisterReceiver(mLocationRequestAlarmReceiver);
                sInstance = null;
        }

        private void connectGoogleApiClient() {
                createLocationRequest();
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                mGoogleApiClient.connect();
        }

        protected void createLocationRequest() {
                long INTERVAL = 0;
                long FASTEST_INTERVAL = 0;
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(INTERVAL);
                mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        public void startLocationUpdates() {
                LocationRequest locationRequest = mLocationHelpers.getLocationRequest();
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, locationRequest, this);
        }

        public void stopLocationUpdate() {
                reset();
        }

        private void acquireLocation() {
                Handler handler = mLocationHelpers.getHandler();
                handler.postDelayed(mLocationRunnable, 5000);
        }

        private void reset() {
                mLocationChangedCounter = 0;
                mLocationRecursionCounter = 0;
                if (mGoogleApiClient.isConnected()) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                        mGoogleApiClient.disconnect();
                }
                mLocation = null;
        }

        @Override
        public void onConnected(Bundle bundle) {
                startLocationUpdates();
        }

        private void startLocationUpdate() {
                connectGoogleApiClient();
                acquireLocation();
        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onLocationChanged(Location location) {
                mLocationChangedCounter++;
                if (mLocationChangedCounter == 5) {
                        mLocation = location;
                }
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
                sInstance = this;
                mLocationHelpers = new LocationHelpers(getApplicationContext());
                registerReceiver(mLocationRequestAlarmReceiver, alarmIntent);
                startLocationUpdate();
                return START_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
                        return null;
        }
}
