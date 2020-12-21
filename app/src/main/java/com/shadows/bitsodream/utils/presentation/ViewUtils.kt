package com.shadows.bitsodream.utils.presentation

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.shadows.bitsodream.R

fun View.showView(flag: Boolean){

    this.visibility = if(flag){
        View.VISIBLE
    }else{
        View.GONE
    }
}


//this util method creates an alert dialog with retry option
class PresentationUtils{

    companion object{
        fun showDialog(context: Context, message:String, retry:() ->Unit){
            val builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.error))
            builder.setMessage(message)
            builder.setPositiveButton(context.getString(R.string.ok)){ _, _ ->
                builder.create().dismiss()
            }
            builder.setNeutralButton(context.getString(R.string.retry)){_,_ ->
                retry()
                builder.create().dismiss()

            }
            builder.create().show()
        }
        fun makeToast(context: Context, string: String){
            Toast.makeText(context,string,Toast.LENGTH_SHORT).show()
        }
    }
}

