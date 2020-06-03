package com.deepboyak//package com.deepboyak
//
//import okhttp3.MultipartBody
//import retrofit2.Call
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//
//interface retrofit_interface {
//    // api 를 관리해 주는 인터페이스
//    // 프로필 이미지 보내기
//    @Multipart
//    @POST("upload")
//    fun post_Porfile_Request(
//        @Part("userId") userId: String,
//        @Part imageFile : MultipartBody.Part): Call<String>
//
//}