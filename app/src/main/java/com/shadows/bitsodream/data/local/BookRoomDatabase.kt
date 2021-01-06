package com.shadows.bitsodream.data.local

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shadows.bitsodream.data.local.dao.BooksDAO
import com.shadows.bitsodream.data.local.models.Book

@Database(entities = [Book::class],version = 1)
abstract class BookRoomDatabase: RoomDatabase() {

    abstract fun bookDao(): BooksDAO

    companion object {

        @Volatile
        private var INSTANCE: BookRoomDatabase? = null

        fun getDatabase(context: Application): BookRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookRoomDatabase::class.java,
                    "book_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}