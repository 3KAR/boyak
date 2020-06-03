package com.deepboyak

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.deepboyak.ui.SplashActivity
import com.example.kotlinbase.JsonModel
import com.example.kotlinbase.Net
import kotlinx.android.synthetic.main.detail_customcell_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 화면전환
        startActivity( Intent(this, SplashActivity::class.java))

    }
}