package com.shadows.bitsodream.utils

import android.util.Log
import com.google.gson.Gson

fun logD(message:String, data:Any?){
    Log.d("Shadow", "$message ${Gson().toJson(data)}")
}
fun logE(message:String, data:Any?){
    Log.e("Shadow", "$message ${Gson().toJson(data)}")
}
