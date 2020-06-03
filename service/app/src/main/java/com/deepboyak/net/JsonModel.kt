package com.example.kotlinbase

import java.io.Serializable
import java.text.DecimalFormat

// JSON을 담는 그릇은 data class로 기술한다
object JsonModel{
    data class ResResult(val res:Collection<Documents>) : Serializable
    data class Documents(val item_name:String,
                         val class_name:String,
                         val class_no: Float): Serializable

    data class ResUpload(val res:Collection<Yacs>): Serializable
    data class Yacs(val name:String, val rate:Int): Serializable
}









/*
data class ResResult(val meta:Meta,
                     val documents:Collection<Documents>)
data class Meta(var is_end:Boolean,
                var pageable_count:Int,
                var total_count:Int)
data class Documents(var collection:String,
                     var datetime:String,
                     var display_sitename:String,
                     var doc_url:String ,
                     var height:Int ,
                     var image_url:String,
                     var thumbnail_url:String ,
                     var width:Int)
 */
