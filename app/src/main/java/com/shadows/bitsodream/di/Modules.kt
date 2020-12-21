package com.shadows.bitsodream.di

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
val myBitsoModules = listOf(
    mainModule,
    booksModule,
    bookDetailModule
)