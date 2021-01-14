package com.shadows.bitsodream.domain.mappers

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.domain.models.Ticker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class RemoteToDomainMapperTest{

    @Test
    fun books_mapper_should_return_empty_list(){
        val list = arrayListOf(Book())
        assertEquals(RemoteToDomainMapper.booksToTickers(list), emptyList<Ticker>())
    }

}