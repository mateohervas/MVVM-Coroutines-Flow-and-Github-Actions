package com.shadows.bitsodream.di


import com.shadows.bitsodream.data.remote.MyBitsoDataSource
import com.shadows.bitsodream.domain.repository.BookDetailRepository
import com.shadows.bitsodream.ui.booksdetail.BooksDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val bookDetailModule = module{
    single<MyBitsoDataSource>(override=true){ MyBitsoDataSource.create()}
    single<BookDetailRepository> { BookDetailRepository(get()) }
    viewModel { BooksDetailViewModel(get()) }
}