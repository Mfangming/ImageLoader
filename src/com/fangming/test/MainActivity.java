package com.fangming.test;

import com.example.gemgem.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        WebView webview = (WebView )findViewById(R.id.webView1);   
        webview.getSettings().setJavaScriptEnabled(true);   
        webview.setVerticalScrollbarOverlay(true);   
        webview.loadUrl("file:///android_asset/www/index.html");
	}
}
