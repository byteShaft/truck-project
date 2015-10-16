package com.byteshaft.groupedirectouest.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.byteshaft.groupedirectouest.R;
import com.byteshaft.groupedirectouest.location.LocationHelpers;
import com.byteshaft.groupedirectouest.location.LocationService;


public class MainTab extends Fragment implements View.OnClickListener {
    
    private View mBaseView;
    public static Button mButton;
    private LocationHelpers locationHelpers;
    private LocationService locationService;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBaseView= inflater.inflate(R.layout.mainview, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        locationHelpers = new LocationHelpers(getActivity());
        mButton = (Button) mBaseView.findViewById(R.id.button);
        mButton.setText(R.string.tow_me_now);
        mButton.setOnClickListener(this);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
        if (!locationHelpers.playServicesAvailable(getActivity())) {
            locationHelpers.showGooglePlayServicesError(getActivity());
        }
        return mBaseView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.tow_me);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (locationHelpers.playServicesAvailable(getActivity()) && !locationHelpers.isAnyLocationServiceAvailable()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(R.string.location_disabled);
                    alertDialog.setMessage(R.string.want_to_enable);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });
                    alertDialog.setNegativeButton(R.string.skip, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mButton.setVisibility(View.INVISIBLE);
                            Fragment newFragment = new FormFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(getView().getId(), newFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                            locationService = new LocationService(getActivity());
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
                    locationService = new LocationService(getActivity());
                }
                break;
        }
    }
}
