package com.example.dimasjose.myapplication.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.dimasjose.myapplication.R;

public class ResultadoActivity extends AppCompatActivity {
    private WebView mWebView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);


        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       // mWebView.loadUrl("http://192.168.0.176/test.php?file=voice8K16bitmono.wav");
        mWebView.loadUrl("http://192.168.2.15/saida.php");

    }
}
