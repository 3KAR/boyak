package com.deepboyak.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.deepboyak.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_customcell_layout.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        home.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val Intent = Intent(this@DetailActivity, SearchHomeActivity::class.java)
                startActivity(Intent)
            }
        })

        back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        val myWebView: WebView = findViewById(R.id.myURL)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

//        myWebView.loadUrl("http://15.164.226.3:5000/detail")
//        myWebView.loadUrl("http://13.125.244.99:5000/test")
        myWebView.loadUrl("http://13.125.244.99:5000/detail")
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true
        myWebView.settings.loadWithOverviewMode = true // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        myWebView.settings.setAppCacheEnabled(true)
        // webview 멀티 터치 가능하게 (줌기능)
        myWebView.settings.builtInZoomControls = true
        myWebView.settings.supportZoom()
        myWebView.settings.displayZoomControls = true
        }
}
