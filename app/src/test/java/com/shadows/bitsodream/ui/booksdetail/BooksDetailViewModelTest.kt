package com.shadows.bitsodream.ui.booksdetail

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.mikephil.charting.data.Entry
import com.shadows.bitsodream.data.local.BookRoomDatabase
import com.shadows.bitsodream.data.local.dao.BooksDAO
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import com.shadows.bitsodream.data.remote.model.ErrorResponse
import com.shadows.bitsodream.di.myBitsoModules
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
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTestRule
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.security.KeyStore

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class BooksDetailViewModelTest {

    private lateinit var db: BookRoomDatabase
    private lateinit var booksDAO: BooksDAO
    private lateinit var booksRepository: BookDetailRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun createDB(){
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, BookRoomDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        booksDAO = db.bookDao()
        booksRepository = BookDetailRepository(MockDataSource(),booksDAO)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getBooksSuccessfully_shouldGetListOfBooks() {

        val viewModel = BooksDetailViewModel(booksRepository)
        //given
        val expected = ArrayList<BookStatistic>()
        val book = "btc"
        runBlocking {
            //when
            val flow = booksRepository.getBookHistory(book).first()
            assertEquals(expected,flow)
            viewModel.getBookHistoric(book)
        }
        viewModel.bookHistoricResponse.observeForever {
            if (it.status == Status.SUCCESS) {
                assertNotNull(it.data)
            }
        }
    }


    @Test
    fun getBooksException_shouldGetErrorMessage(){

        val viewModel = BooksDetailViewModel(booksRepository)
        val book = "btc"

        runBlocking {
            viewModel.getBookHistoric(book)
        }

        viewModel.bookHistoricResponse.observeForever {
            if(it.status==Status.ERROR){
                assertNull(it.data)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetBook() = runBlocking {
        val viewModel = BooksDetailViewModel(booksRepository)
        val book = com.shadows.bitsodream.data.local.models.Book("btc","test")
        booksRepository.insertBook(book)
        viewModel.savingBookResponse.observeForever { resource ->
            if(resource.status == Status.SUCCESS){
                assert(book.name == resource.data?.name)
            }
        }
    }
}
