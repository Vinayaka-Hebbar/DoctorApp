package com.example.vinayakahebbar.doctorapp.fragments;


import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.vinayakahebbar.doctorapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    private WebView webViewHelp;
    private AssetManager assetManager;
    private View view;
    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);
        webViewHelp = (WebView)view.findViewById(R.id.web_view_help);
        loadHtmlPage();
        return view;
    }

    private void loadHtmlPage() {
        webViewHelp.getSettings().setJavaScriptEnabled(true);
        webViewHelp.loadUrl("file:///android_asset/html/index.html");
    }

}
