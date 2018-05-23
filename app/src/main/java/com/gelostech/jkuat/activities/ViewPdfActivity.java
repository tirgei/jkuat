package com.gelostech.jkuat.activities;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gelostech.jkuat.R;
import com.gelostech.jkuat.commoners.FileDownloader;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;


public class ViewPdfActivity extends AppCompatActivity implements View.OnClickListener, DownloadFile.Listener{
    private android.support.v7.widget.Toolbar toolbar;
    private AVLoadingIndicatorView progressBar;
    private WebView webView;
    private  RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter pdfPagerAdapter;
    private RelativeLayout root;
    private String toolbarTitle, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        url = getIntent().getStringExtra("url");
        toolbarTitle = getIntent().getStringExtra("title");

        initViews(toolbarTitle);
        if(Build.VERSION.SDK_INT >= 21)
            checkPermission(false, url);
        else
            loadWebView(url);


    }

    private void initViews(String toolbarTitle){
        toolbar = findViewById(R.id.main_toolbar);
        progressBar = findViewById(R.id.loading_pdf);
        webView = findViewById(R.id.webview);
        root = findViewById(R.id.activity_viewpdf_root);

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

        progressBar.smoothToShow();
    }

    private void loadWebView(String url){
        final String pdfURl = "https://docs.google.com/viewer?url=" + url;

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.smoothToHide();

                if(!view.isShown())
                    view.setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(pdfURl);
    }

    private void loadPdf(String url){
       remotePDFViewPager = new RemotePDFViewPager(this, url, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_pdf_activity_toolbar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.download_pdf:
                checkPermission(true, url);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_signin, R.anim.exit_main);
        finish();
    }

    private void checkPermission(final boolean isDownload, final String url){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        if(isDownload){
                           new DownloadFile().execute(url, toolbarTitle + ".pdf");

                        } else {
                            loadPdf(url);
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        pdfPagerAdapter = new PDFPagerAdapter(this, destinationPath);
        remotePDFViewPager.setAdapter(pdfPagerAdapter);
        updateUI();

        progressBar.smoothToHide();

    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {
    }

    private void updateUI(){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, toolbar.getId());
        root.addView(remotePDFViewPager, params);
    }

    public class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ViewPdfActivity.this, "Downloading file...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "jkuat");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ViewPdfActivity.this, "File downloaded successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
