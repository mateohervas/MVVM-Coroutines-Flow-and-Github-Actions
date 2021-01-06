package com.shadows.bitsodream.domain.repository

import com.shadows.bitsodream.data.local.dao.BooksDAO
import com.shadows.bitsodream.data.remote.MyBitsoDataSource
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookDetailRepository(private val myBitsoDataSource: MyBitsoDataSource, private val booksDAO: BooksDAO) {

    //calling the API to get the historic of an specific book  with the use of Coroutines and flow
    fun getBookHistory(book:String): Flow<ArrayList<BookStatistic>> {
        return flow {
            emit(myBitsoDataSource.getChart(book))
        }.flowOn(Dispatchers.IO)
    }

    fun getBookById(id: String): Flow<com.shadows.bitsodream.data.local.models.Book> {
        return flow{
            emit(booksDAO.getBookByName(id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertBook(book: com.shadows.bitsodream.data.local.models.Book){
        booksDAO.insertBookComment(book)
    }
}