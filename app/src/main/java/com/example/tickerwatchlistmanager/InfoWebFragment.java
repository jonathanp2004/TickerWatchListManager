package com.example.tickerwatchlistmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;
public class InfoWebFragment extends Fragment
{


    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.infowebfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        webView = view.findViewById(R.id.webview);

        webView.setWebViewClient(new WebViewClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);


        if (savedInstanceState == null)
        {
            webView.loadUrl("https://seekingalpha.com");
        }

    }

    public void loadTicker(@NonNull String symbol)
    {
        if (webView == null) return;
        String upper = symbol.toUpperCase(Locale.US);
        String url = "https://seekingalpha.com/symbol/" + upper;
        webView.loadUrl(url);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if (webView != null)
        {
            webView.saveState(outState);
        }
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        webView = null;
    }
}