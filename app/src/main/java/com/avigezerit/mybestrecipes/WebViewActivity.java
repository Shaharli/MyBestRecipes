package com.avigezerit.mybestrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //get the url
        String givenUrl = getIntent().getStringExtra("url");

        //xml ref
        WebView recpWV = (WebView) findViewById(R.id.recpWV);
        recpWV.loadUrl(givenUrl);

    }
}
