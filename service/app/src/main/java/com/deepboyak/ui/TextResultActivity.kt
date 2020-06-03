package com.deepboyak.ui

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.deepboyak.R
import com.example.kotlinbase.JsonModel
import kotlinx.android.synthetic.main.activity_text_search.*
import kotlinx.android.synthetic.main.customcell_layout.*


class TextResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_search)
        myAdapter = MyAdapter()
        listView.adapter = myAdapter
        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id -> Log.i("TT","클립") }

//        listView.setOnItemClickListener(AdapterView.OnItemClickListener(){ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
//            fun onItemClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ){
//                Log.i("TT","클립")
//            }
//        })
        val tmp: JsonModel.ResResult? = intent.getSerializableExtra("res") as? JsonModel.ResResult
        tmp?.res?.let { docs.addAll(it) }
        myAdapter.notifyDataSetChanged()

        home.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var Intent = Intent(this@TextResultActivity, SearchHomeActivity::class.java)
//                var Intent = Intent(this@TextResultActivity, DetailActivity::class.java)
                startActivity(Intent)
            }
        })

        back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
    }
    fun onDetailGo(view:View)
    {
        var Intent = Intent(this@TextResultActivity, DetailActivity::class.java)
        startActivity(Intent)
        Log.i("TT", "상세 페이지 이동")
    }

    private lateinit var myAdapter: TextResultActivity.MyAdapter
    internal var docs = mutableListOf<JsonModel.Documents>()

    fun onTextSearchResult(view:View) {
        // 텍스트 서치 결과
        // 결과 클릭 후 detail로 이동
        Log.i("TT", "로그")

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        println("*** My thread is now configured to allow connection")
        Log.i("TT", "통신 시작")
    }

    internal inner class MyAdapter: BaseAdapter(){
        internal lateinit var viewHolder: ViewHolder
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            // 널세이프 => !!, ?., ?:
            // convertView가 존재하면 그냥 assign, 만약 null 이면 뒤에코드 수행
            var view:View = convertView ?: layoutInflater.inflate(R.layout.customcell_layout, parent, false)

            if (convertView == null) {
                viewHolder = ViewHolder()
//                viewHolder.poster            = view.findViewById(R.id.poster)
                viewHolder.display_itemname  = view.findViewById(R.id.display_itemname)
                viewHolder.display_classname = view.findViewById(R.id.display_classname)
                view.tag   = viewHolder
                Log.i("TT","초기화"+position)
            } else {
                viewHolder = view.tag as ViewHolder
            }

            val data = getItem(position)
            viewHolder.display_itemname.setText(data.item_name)
            viewHolder.display_classname.setText(data.class_name)
            //Picasso.get().load(data.poster).into(viewHolder.poster)
            println(data.item_name)
            println(data.class_name)
            return view

        }
        // 셀 한개 획득
        override fun getItem(position: Int): JsonModel.Documents {
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
//        lateinit var poster: ImageView
        lateinit var display_itemname: TextView
        lateinit var display_classname: TextView
    }



}

