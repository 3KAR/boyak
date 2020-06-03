package com.example.kotlinbase

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface KakaoImp {
    @GET("text")
    fun getTextSearch(@Query("item_name") item_name : String
    ): Call<JsonModel.ResResult>

    @Multipart
    @POST("upload")
    fun updateProfile(
        @Part() image:
        MultipartBody.Part?
    ): Call<JsonModel.ResUpload>
}

//interface WebAPIService {
//    @Multipart
//    @POST("main.php")
//    fun postFile(
//        @PartMap Files: Map<String?, RequestBody?>?,
//        @Part("json") description: String?
//    ): Call<String?>?
//}