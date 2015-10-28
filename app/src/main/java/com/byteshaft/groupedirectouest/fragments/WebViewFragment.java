package com.byteshaft.groupedirectouest.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.byteshaft.groupedirectouest.R;


public class WebViewFragment extends Fragment {

    private View mBaseView;
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        mBaseView = inflater.inflate(R.layout.webviewfragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mWebView = (WebView) mBaseView.findViewById(R.id.web_view);
        String uri = "http://groupedirectouest.com/";
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(uri);
        return mBaseView;
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // Stay within this webview and load url
                return true;
        }
    }

}
