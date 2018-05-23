package com.gelostech.jkuat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.gelostech.jkuat.R;

public class MastersActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masters);

        final String title = getIntent().getStringExtra("title");
        final String url = getIntent().getStringExtra("url");

        initViews(title);
        loadWeb(url);
    }

    private void initViews(String toolbarTitle){
        webView = findViewById(R.id.masters_webview);
        progressBar = findViewById(R.id.masters_loading);
        toolbar = findViewById(R.id.masters_activity_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(toolbarTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadWeb(String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(progressBar.isShown())
                    progressBar.setVisibility(View.GONE);

                if(!view.isShown())
                    view.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_signin, R.anim.exit_main);
        finish();
    }

}
