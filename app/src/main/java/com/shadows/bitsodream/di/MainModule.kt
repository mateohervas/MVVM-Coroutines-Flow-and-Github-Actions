package com.shadows.bitsodream.di



import com.shadows.bitsodream.ui.BaseActivity
import org.koin.dsl.module

val mainModule = module{
    factory { BaseActivity() }
}