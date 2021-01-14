package com.shadows.bitsodream.ui.booksdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.mikephil.charting.data.Entry
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import com.shadows.bitsodream.data.remote.model.ErrorResponse
import com.shadows.bitsodream.di.myBitsoModules
import com.shadows.bitsodream.domain.models.BookDomain
import com.shadows.bitsodream.domain.models.Resource
import com.shadows.bitsodream.domain.models.Status
import com.shadows.bitsodream.domain.repository.BookDetailRepository
import com.shadows.bitsodream.domain.repository.BooksRepository
import com.shadows.bitsodream.ui.books.BooksViewModel
import com.shadows.bitsodream.ui.data.MockDataSource
import com.shadows.bitsodream.ui.data.MockErrorSource
import com.shadows.bitsodream.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTestRule
import org.mockito.MockitoAnnotations
import java.security.KeyStore

@ExperimentalCoroutinesApi
class BooksDetailViewModelTest {


    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(myBitsoModules)
    }
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loadKoinModules(myBitsoModules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(myBitsoModules)
        stopKoin()
    }

    @Test
    fun getBooksSuccessfully_shouldGetListOfBooks() {
        val repo = BookDetailRepository(MockDataSource())
        val viewModel = BooksDetailViewModel(repo)
        //given
        val expected = ArrayList<BookStatistic>()
        val bookDomain = BookDomain("btc","mxn")
        val book = "btc_mxn"
        runBlocking {
            //when
            val flow = repo.getBookHistory(book).first()
            assertEquals(expected,flow)
            viewModel.getBookHistoric(bookDomain)
        }
        viewModel.bookHistoricResponse.observeForever {
            if (it.status == Status.SUCCESS) {
                assertNotNull(it.data)
            }
        }
    }


    @Test
    fun getBooksException_shouldGetErrorMessage(){
        val repo = BookDetailRepository(MockDataSource())
        val viewModel = BooksDetailViewModel(repo)
        val book = BookDomain("btc","mxn")

        runBlocking {
            viewModel.getBookHistoric(book)
        }

        viewModel.bookHistoricResponse.observeForever {
            if(it.status==Status.ERROR){
                assertNull(it.data)
            }
        }
    }
}
