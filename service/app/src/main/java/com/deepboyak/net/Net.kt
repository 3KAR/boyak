package com.example.kotlinbase

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Net {
    internal var retrofit = Retrofit.Builder()
        // 통신의 대상이 되는 기본 도메인
        .baseUrl("http://10.0.2.2:5000")
//        .baseUrl("http://13.125.244.99:5000")
        // 통신후 결과를 파싱하는 엔진 연결
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    // getter
    internal val kakaoImp: KakaoImp = retrofit.create(KakaoImp::class.java)
}
