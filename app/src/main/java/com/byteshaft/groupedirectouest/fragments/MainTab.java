package com.byteshaft.groupedirectouest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.byteshaft.groupedirectouest.R;


public class MainTab extends Fragment implements View.OnClickListener {

    public static final String ARG_OBJECT = "Miantab";
    private View mBaseView;
    private RelativeLayout mRelativeLayout;
    private Button mButton;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBaseView= inflater.inflate(R.layout.mainview, container, false);
        mRelativeLayout = (RelativeLayout) mBaseView.findViewById(R.id.tuckRelativeLayout);
        mRelativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.gdo));
        mButton = (Button) mBaseView.findViewById(R.id.button);
        mButton.setOnClickListener(this);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
        return mBaseView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Fragment newFragment = new FormFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(getView().getId(), newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }

    }
}
