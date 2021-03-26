package com.papasfritas.coronavirus.Activity.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.papasfritas.coronavirus.R;

public class WebFragment extends Fragment {


    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView myWebView = view.findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://e.infogram.com/81277d3a-5813-46f7-a270-79d1768a70b2?parent_url=https%3A%2F%2Fwww.gob.cl%2Fcoronavirus%2Fpasoapaso%2F%23situacioncomunal%2F&src=embed#async_embed");
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl("https://e.infogram.com/81277d3a-5813-46f7-a270-79d1768a70b2?parent_url=https%3A%2F%2Fwww.gob.cl%2Fcoronavirus%2Fpasoapaso%2F%23situacioncomunal%2F&src=embed#async_embed");
            return super.shouldOverrideUrlLoading(view, request);

        }
    }
}