package com.shadows.bitsodream.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_table")
data class Book(@PrimaryKey @ColumnInfo(name = "name")val name: String, val comment: String)
