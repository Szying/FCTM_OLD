package com.fctm.actionbartabdrawer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class LabTrain extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.labvideohtml5, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        WebView mWebView = (WebView) getActivity().findViewById(R.id.trainwebvw2);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings=mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String video_URL = "file:///android_asset/www/video.html";
        mWebView.loadUrl(video_URL);

    }

}
