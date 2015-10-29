package com.byteshaft.groupedirectouest.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.byteshaft.groupedirectouest.R;


public class WebViewFragment extends Fragment {

    private View mBaseView;
    public static WebView sWebView;
    public static boolean sWebViewOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        mBaseView = inflater.inflate(R.layout.webviewfragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sWebView = (WebView) mBaseView.findViewById(R.id.web_view);
        String uri = "http://groupedirectouest.com/";
        sWebView.setWebViewClient(new WebViewClient());
        sWebView.setWebChromeClient(new MyWebViewClient());
        sWebView.getSettings().setJavaScriptEnabled(true);
        sWebView.loadUrl(uri);
        sWebViewOpen = true;
        return mBaseView;
    }

    class MyWebViewClient extends WebChromeClient {
        
    }

}
