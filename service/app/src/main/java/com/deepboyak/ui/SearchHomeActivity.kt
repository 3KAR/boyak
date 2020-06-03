package com.deepboyak.ui

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.deepboyak.R
import com.example.kotlinbase.JsonModel
import com.example.kotlinbase.Net
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchHomeActivity : AppCompatActivity() {
    private lateinit var item_name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


    }
    fun onTextSearch(view: View) {
        // 1. 검색어 획득
        val item_name = textInput.text.toString()
        if (TextUtils.isEmpty(item_name)) {
            textInput.error = "검색어를 입력 후 검색하세요."
            return
        }
        this.item_name = item_name // 검색어 저장
        onSearchProc(item_name)
        println(item_name)
    }

    fun onSearchProc(item_name: String) {
        progressBar.visibility = View.VISIBLE
        // 2. 텍스트검색 통신 진행
        val res = Net.kakaoImp.getTextSearch(item_name = item_name)
        // 통신결과 대기
        Log.i("TT", "통신대기")
        res.enqueue(object : Callback<JsonModel.ResResult> {
            override fun onResponse(
                call: Call<JsonModel.ResResult>,
                response: Response<JsonModel.ResResult>
            ) {
                if (response.isSuccessful()) {
                    val result = response.body()
                    // !! => null이 아님을 내가 보장하겠다. => null이 올 수도 있다.
                    // ? => 만약 null이면 아무것도 하지마

                    progressBar.visibility = View.GONE
                    val intent = Intent(this@SearchHomeActivity, TextResultActivity::class.java)
                    intent.putExtra("res", result!!)
                    println(result)
                    // 화면 전환
                    startActivity( intent )


                    // 응답 데이터중 실리스트 데이터를 담는다
//                    docs.addAll(result!!.res)
//                    // 리스트뷰에 공급하는 데이터가 변경되었다
//                    myAdapter.notifyDataSetChanged()
//                    Log.i("TT", "총카운트" + result?.res?.size)
                } else {
                    Log.i("TT", "" + response.errorBody()!!.toString())
                }
            }

            override fun onFailure(call: Call<JsonModel.ResResult>, t: Throwable) {
                // 2-2. 실패
                // 2-2-1. 실패 내용 화면에 표시
                val ad = AlertDialog.Builder(this@SearchHomeActivity)
                    .setTitle("알림")
                    .setMessage((t.message))
                    .create()
                    Log.i("TT","안돼")
                ad.show()
            }
        })
    }

    fun onCameraSearch(view:View){
        // 카메라 서치
        // 카메라 서치 액티비티로 이동
        startActivity( Intent(this@SearchHomeActivity, CameraSearchActivity::class.java)   )
    }


}



//    fun loadingProc(flag:Boolean) {
//        if (flag) {
//            loading.visibility = View.VISIBLE
//        } else {
//            loading.visibility = View.GONE
//        }
//    }
//    internal inner class MyAdapter: BaseAdapter(){
//        internal lateinit var viewHolder : RecyclerView.ViewHolder
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            var view:View = convertView ?: layoutInflater.inflate(R.layout.activity_text_search, parent, false)
//
//            if (convertView == null) {
//                viewHolder = RecyclerView.ViewHolder
//            }
//        }
//
//
//}




