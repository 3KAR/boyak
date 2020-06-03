package com.deepboyak.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.deepboyak.R
import com.example.kotlinbase.JsonModel
import kotlinx.android.synthetic.main.activity_camera_result.*
import kotlinx.android.synthetic.main.activity_text_search.*
import kotlinx.android.synthetic.main.activity_text_search.home
import kotlinx.android.synthetic.main.activity_camera_result.back as back1

class CameraResultActivity : AppCompatActivity() {
    private lateinit var myAdapter: CameraResultActivity.MyAdapter
    internal var docs = mutableListOf<JsonModel.Yacs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)
        myAdapter = MyAdapter()
        listView1.adapter = myAdapter
        listView1.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id -> Log.i("TT", "클립") }
        home.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val Intent = Intent(this@CameraResultActivity, SearchHomeActivity::class.java)
                startActivity(Intent)
            }
        })
        back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        val myWebView: WebView = findViewById(R.id.cameraResult)
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        myWebView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressBar2.visibility = View.GONE
            }
        })


        var fileName : String = intent.getStringExtra("fileName")
        myWebView.loadUrl("http://http://10.0.2.2:5000/static/up/${fileName}")
//        myWebView.loadUrl("http://13.125.244.99:5000/result")
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



        val tmp: JsonModel.ResUpload? = intent.getSerializableExtra("res") as? JsonModel.ResUpload
        tmp?.res?.let { docs.addAll(it) }
        myAdapter.notifyDataSetChanged()
    }

    fun onDetailGo(view:View)
    {
        var Intent = Intent(this@CameraResultActivity, DetailActivity::class.java)
        startActivity(Intent)
        Log.i("TT", "상세 페이지 이동")
    }


    internal inner class MyAdapter: BaseAdapter(){
        internal lateinit var viewHolder: ViewHolder
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            // 널세이프 => !!, ?., ?:
            // convertView가 존재하면 그냥 assign, 만약 null 이면 뒤에코드 수행
            var view:View = convertView ?: layoutInflater.inflate(R.layout.customcell_layout, parent, false)

            if (convertView == null) {
                viewHolder = ViewHolder()
                viewHolder.poster            = view.findViewById(R.id.poster)
                viewHolder.display_itemname  = view.findViewById(R.id.display_itemname)
                viewHolder.display_classname = view.findViewById(R.id.display_classname)
                view.tag   = viewHolder
                Log.i("TT","초기화"+position)
            } else {
                viewHolder = view.tag as ViewHolder
            }

            val data = getItem(position)
            viewHolder.display_itemname.setText(data.name)
            viewHolder.display_classname.setText(data.rate.toString())
            //Picasso.get().load(data.poster).into(viewHolder.poster)
            println(data.name)
            println(data.rate)
            return view

        }
        // 셀 한개 획득
        override fun getItem(position: Int): JsonModel.Yacs {
            //return docs[position]
            return docs.get(position)
        }
        // 사용 안함
        override fun getItemId(position: Int): Long {
            return 0
        }
        // 데이터의 개수
        override fun getCount(): Int {
            return docs.size
        }

    }

    // 셀 하나의 뷰들을 담는 클레스
    internal inner class ViewHolder {
        lateinit var poster: ImageView
        lateinit var display_itemname: TextView
        lateinit var display_classname: TextView
    }



//    fun onCameraSearchResult(view: View){
//        // 카메라 검색 결과
//        // 결과 클릭 후 detail로 이동
//        Log.i("CSHR","로그")
//    }
}
