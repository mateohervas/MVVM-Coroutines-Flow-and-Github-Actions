package com.shadows.bitsodream.di


import android.app.Application
import com.shadows.bitsodream.data.local.BookRoomDatabase
import com.shadows.bitsodream.data.local.dao.BooksDAO
import com.shadows.bitsodream.data.remote.MyBitsoDataSource
import com.shadows.bitsodream.domain.repository.BookDetailRepository
import com.shadows.bitsodream.ui.booksdetail.BooksDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val bookDetailModule = module{
    single<MyBitsoDataSource>(override=true){ MyBitsoDataSource.create()}
    fun provideDatabase(application: Application) : BookRoomDatabase {
        return BookRoomDatabase.getDatabase(application)
    }
    fun provideBooksDAO(db: BookRoomDatabase): BooksDAO{
        return db.bookDao()
    }
    single { provideDatabase(androidApplication()) }
    single { provideBooksDAO(get()) }
    single<BookDetailRepository> { BookDetailRepository(get(),get()) }
    viewModel { BooksDetailViewModel(get()) }
}