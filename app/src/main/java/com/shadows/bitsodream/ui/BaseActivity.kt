package com.shadows.bitsodream.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.shadows.bitsodream.utils.ConnectivityChangesCallback
import com.shadows.bitsodream.utils.MyInternetMonitoringService
import com.shadows.bitsodream.utils.presentation.showView
import kotlinx.android.synthetic.main.loader.*
import kotlinx.android.synthetic.main.message_noconnection.*


open class BaseActivity: AppCompatActivity(), ConnectivityChangesCallback {



    lateinit var myInternetMonitoringService: MyInternetMonitoringService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myInternetMonitoringService = MyInternetMonitoringService(this)
        myInternetMonitoringService.connectionStateMonitor()
        myInternetMonitoringService.enable(this)
    }

    fun isLoading(flag: Boolean){
        llLoading.showView(flag)
        if(flag){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

    }


    //callback to interact with connection changes
    override fun onChanged(amConnected: Boolean) {
        runOnUiThread {
            container_connectivity.showView(!amConnected)
        }
    }
}