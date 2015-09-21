package com.byteshaft.groupedirectouest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.byteshaft.groupedirectouest.R;

/**
 * Created by s9iper1 on 9/21/15.
 */
public class FormFragment extends Fragment {

    private View mBaseView;
    private RelativeLayout mRelativeLayout;
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView= inflater.inflate(R.layout.mainview, container, false);
        return mBaseView;
    }
}
