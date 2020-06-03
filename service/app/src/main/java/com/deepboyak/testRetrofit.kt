package com.deepboyak//package com.deepboyak
//
//import android.util.Log
//import android.widget.Toast
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.io.File
//
//fun testRetrofit(path : String){print("hello world!! byebye one ball two strike 미쳤네완전")
//
//    //creating a file
//    val file = File(path)
//    var fileName = userData.user_Id.replace("@","").replace(".","")
//    fileName = fileName+".png"
//
//
//    var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),file)
//    var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file",fileName,requestBody)
//
//    //The gson builder
//    var gson : Gson =  GsonBuilder()
//        .setLenient()
//        .create()
//
//
//    //creating retrofit object
//    var retrofit =
//        Retrofit.Builder()
//            .baseUrl("http://15.164.226.3:5000")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//    //creating our api
//
//    var server = retrofit.create(retrofit_interface::class.java)
//
//    // 파일, 사용자 아이디, 파일이름
//
//    server.post_Porfile_Request(userData.user_Id,body).enqueue(object: Callback<String> {
//        override fun onFailure(call: Call<String>, t: Throwable) {
//            Log.d("레트로핏 결과1",t.message)
//        }
//
//        override fun onResponse(call: Call<String>, response: Response<String>) {
//            if (response?.isSuccessful) {
//                Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
//                Log.d("레트로핏 결과2",""+response?.body().toString())
//            } else {
//                Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
//            }
//        }
//    })
//}