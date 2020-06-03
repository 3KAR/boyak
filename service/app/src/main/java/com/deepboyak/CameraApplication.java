package com.deepboyak;

import android.app.Application;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;

public class CameraApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        RxPaparazzo.register(this);
    }
}