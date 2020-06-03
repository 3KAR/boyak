package com.deepboyak.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.deepboyak.R
import com.example.kotlinbase.JsonModel
import com.example.kotlinbase.Net
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_camera_result.*
import kotlinx.android.synthetic.main.activity_camera_search.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class CameraSearchActivity : AppCompatActivity() {

    private var fileDataList: ArrayList<FileData>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_search)

        RxPaparazzo.single(this).crop()
            .usingCamera()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                // See response.resultCode() doc
                if (response.resultCode() !== Activity.RESULT_OK) {
                    // 촬영 실패시
//                    response.targetUI().showUserCanceled()
                    Log.i("TT","촬영실패")
                    return@subscribe
                    
                }
                Log.i("TT","촬영성공")
                val path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RxPaparazzo";
                val f = File(path)
                val arr = f.listFiles()
//                val intent = Intent(this@CameraSearchActivity, CameraResultActivity::class.java)
//                startActivity( intent )

//                response.targetUI().loadImage(response.data())
                makeImage( response.data(), path+"/"+arr[arr.size-1].getName() )
                Log.i("TT","저장")


            }
    }

    fun makeImage(fileData: FileData, name:String)
    {
        val fName = "file:///data/user/0/com.deepboyak/files/RxPaparazzo/" + name
//        val fName = "C:/Users/admin/Desktop/final/files/" + name
        Log.i("TT", "저장된 파일명 => "+fName)

        val file = File(name)
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        Log.i("TT","파일 전송")
        val body =
            MultipartBody.Part.createFormData("image", file.getName(), requestFile)

        println(file.name)
// add another part within the multipart request
        val fullName =
            RequestBody.create(MediaType.parse("multipart/form-data"), "Your Name")
        progressBar2.visibility = View.VISIBLE

        val res = Net.kakaoImp.updateProfile(body)
        res.enqueue(object : Callback<JsonModel.ResUpload> {

            override fun onResponse(
                call: Call<JsonModel.ResUpload>,
                response: Response<JsonModel.ResUpload>
            ) {
                if (response.isSuccessful()) {
                    progressBar2.visibility = View.GONE
                    val result = response.body()
                    val intent = Intent(this@CameraSearchActivity, CameraResultActivity::class.java)
                    intent.putExtra("fileName", file.name!!)
                    intent.putExtra("res", result!!)
                    println(result)
                    startActivity( intent )
                    Log.i("TT","성공" + result!!.res.size)

                } else {
                    Log.i("TT", "안됨" + response.errorBody()!!.toString())
                }
            }

            override fun onFailure(call: Call<JsonModel.ResUpload>, t: Throwable) {
                Log.i("TT","실패" + t.localizedMessage)
                Log.ERROR
            }
        })
    }
}

