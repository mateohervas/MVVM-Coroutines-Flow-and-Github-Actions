package com.shadows.bitsodream.domain.repository


import com.shadows.bitsodream.data.remote.MyBitsoDataSource
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

open class BooksRepository(private val myBitsoDataSource: MyBitsoDataSource) {

    //calling the API to get the list of books with the use of Coroutines and flow
     fun getBooks(): Flow<BaseResponse<ArrayList<Book>>> {
        return flow {
            emit(myBitsoDataSource.getBooks())
        }.flowOn(Dispatchers.IO)
    }

}