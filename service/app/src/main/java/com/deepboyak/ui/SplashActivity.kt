package com.deepboyak.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.deepboyak.R

class SplashActivity : AppCompatActivity() {
    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 1000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, SearchHomeActivity::class.java))
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }



    override fun onBackPressed() {
//        super.onBackPressed()
        Log.i("TT", "백키")
    }
}
