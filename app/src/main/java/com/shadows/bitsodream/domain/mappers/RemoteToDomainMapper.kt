package com.shadows.bitsodream.domain.mappers

import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.domain.models.BookDomain
import com.shadows.bitsodream.domain.models.Ticker
import com.shadows.bitsodream.utils.logD

class RemoteToDomainMapper {
    companion object{
        fun booksToTickers(list: ArrayList<Book>): List<Ticker> {
            return list.mapNotNull { book ->
                book.bookToTicker()
            }

        }

        private fun Book.bookToTicker(): Ticker? {

            val bookMajor = this.book?.substringBefore("_")?:return null
            val bookPrice = this.last?:return null
            val bookMinor = this.book?.substringAfter("_")?:return null
            val high = this.high?:return null
            val low = this.low?:return null
            val volume = this.volume?:return null
            val vwap = this.vwap?:return null
            val ask = this.ask?:return null
            val spread = this.calculateSpread()
            val bookDomain = BookDomain(bookMajor,bookMinor)
            return Ticker(volume,high,bookPrice,low,bookDomain ,vwap, ask, spread)
        }

        private fun Book.calculateSpread(): String {
            return this.ask?.toDoubleOrNull()?.let {
                this.bid?.toDoubleOrNull()?.minus(it)
            }.toString()
        }
    }

}