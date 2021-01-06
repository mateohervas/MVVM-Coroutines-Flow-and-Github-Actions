package com.shadows.bitsodream.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shadows.bitsodream.data.local.models.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDAO {

    @Query("SELECT * FROM books_table WHERE name=:name ")
    suspend fun getBookByName(name:String): Book

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookComment(book: Book)


}