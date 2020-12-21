package com.shadows.bitsodream.ui.data

import com.shadows.bitsodream.data.remote.MyBitsoDataSource
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import com.shadows.bitsodream.data.remote.model.ErrorResponse

class MockDataSource: MyBitsoDataSource {
    override suspend fun getBooks(): BaseResponse<ArrayList<Book>> {
       return BaseResponse(true,ArrayList(),null)
    }

    override suspend fun getChart(book: String): ArrayList<BookStatistic> {
        return ArrayList()
    }
}
class MockErrorSource: MyBitsoDataSource{
    override suspend fun getBooks(): BaseResponse<ArrayList<Book>> {
        return BaseResponse(false,null, ErrorResponse("",""))
    }

    override suspend fun getChart(book: String): ArrayList<BookStatistic> {
        return arrayListOf(BookStatistic())
    }

}