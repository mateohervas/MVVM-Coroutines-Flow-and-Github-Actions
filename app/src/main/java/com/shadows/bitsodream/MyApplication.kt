package com.shadows.bitsodream

import android.app.Application
import com.shadows.bitsodream.data.local.BookRoomDatabase
import com.shadows.bitsodream.di.myBitsoModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {


    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(myBitsoModules)
        }
    }
}