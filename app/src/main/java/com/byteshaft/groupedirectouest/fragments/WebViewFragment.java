package com.byteshaft.groupedirectouest.fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import com.byteshaft.groupedirectouest.R;

import im.delight.android.webview.AdvancedWebView;


public class WebViewFragment extends Fragment implements AdvancedWebView.Listener {

    private View mBaseView;
    public static AdvancedWebView mWebView;
    public static boolean sWebViewOpen = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        mBaseView = inflater.inflate(R.layout.webviewfragment, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mWebView = (AdvancedWebView) mBaseView.findViewById(R.id.web_view);
        String uri = "http://groupedirectouest.com/";
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(uri);
        mWebView.setListener(getActivity(), this);
        sWebViewOpen = true;
        return mBaseView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebView.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onPageStarted(String s, Bitmap bitmap) {

    }

    @Override
    public void onPageFinished(String s) {

    }

    @Override
    public void onPageError(int i, String s, String s1) {

    }

    @Override
    public void onDownloadRequested(String s, String s1, String s2, String s3, long l) {

    }

    @Override
    public void onExternalPageRequest(String s) {

    }

    class MyWebViewClient extends WebChromeClient {

    }

}
