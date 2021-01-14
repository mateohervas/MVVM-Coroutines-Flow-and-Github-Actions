package com.shadows.bitsodream.domain.repository


import com.shadows.bitsodream.data.remote.MyBitsoDataSource
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.domain.mappers.RemoteToDomainMapper
import com.shadows.bitsodream.domain.models.Ticker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

open class BooksRepository(private val myBitsoDataSource: MyBitsoDataSource) {

    //calling the API to get the list of books with the use of Coroutines and flow
     fun getBooks(): Flow<BaseResponse<List<Ticker>>> {
        return flow {
            val booksResponse = myBitsoDataSource.getBooks()
            val tickers = RemoteToDomainMapper.booksToTickers(booksResponse.payload!!)
            val tickerResponse = BaseResponse(booksResponse.success,tickers,booksResponse.error)
            emit(tickerResponse)
        }.flowOn(Dispatchers.IO)
    }


}