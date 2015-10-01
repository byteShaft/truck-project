package com.byteshaft.groupedirectouest.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.byteshaft.groupedirectouest.location.LocationHelpers;
import com.byteshaft.groupedirectouest.location.LocationService;
import com.byteshaft.groupedirectouest.R;


public class MainTab extends Fragment implements View.OnClickListener {
    
    private View mBaseView;
    public static Button mButton;
    LocationHelpers locationHelpers;
    LocationService locationService;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBaseView= inflater.inflate(R.layout.mainview, container, false);
        locationHelpers = new LocationHelpers(getContext());
        mButton = (Button) mBaseView.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
        if (!locationHelpers.playServicesAvailable(getContext())) {
            locationHelpers.showGooglePlayServicesError(getContext());
        }
        return mBaseView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (locationHelpers.playServicesAvailable(getContext()) && !locationHelpers.isAnyLocationServiceAvailable()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Location Service disabled");
                    alertDialog.setMessage("Want to enable?");
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    alertDialog.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mButton.setVisibility(View.INVISIBLE);
                            Fragment newFragment = new FormFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(getView().getId(), newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            locationService = new LocationService(getContext());
                            locationService.locationTimer().start();
                        }
                    });
                    alertDialog.show();
                } else {
                    mButton.setVisibility(View.INVISIBLE);
                    Fragment newFragment = new FormFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(getView().getId(), newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    locationService = new LocationService(getContext());
                }
                break;
        }
    }
}
