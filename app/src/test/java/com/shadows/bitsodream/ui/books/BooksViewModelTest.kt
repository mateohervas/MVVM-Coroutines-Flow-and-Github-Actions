package com.shadows.bitsodream.ui.books

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.ErrorResponse
import com.shadows.bitsodream.di.myBitsoModules
import com.shadows.bitsodream.domain.models.Resource
import com.shadows.bitsodream.domain.models.Status
import com.shadows.bitsodream.domain.repository.BooksRepository
import com.shadows.bitsodream.ui.data.MockDataSource
import com.shadows.bitsodream.ui.data.MockErrorSource
import com.shadows.bitsodream.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTestRule
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class BooksViewModelTest {

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
         val repo = BooksRepository(MockDataSource())
        val viewModel =BooksViewModel(repo)
        //given
        val expected = BaseResponse<ArrayList<Book>>(true,ArrayList(),null)

        runBlocking {
            //when
            val flow = repo.getBooks().first()
            assertEquals(expected,flow)
            viewModel.getBooks()
        }

        val resource = Resource<ArrayList<Book>>(Status.SUCCESS,expected.payload,null)
        viewModel.booksResponse.observeForever {
            assertEquals(it,resource)
        }
    }


    @Test
    fun getBooksException_shouldGetErrorMessage(){
        val repo = BooksRepository(MockErrorSource())
        val viewModel =BooksViewModel(repo)
        //given
        val expected = BaseResponse<ArrayList<Book>>(false,null, ErrorResponse("",""))
        runBlocking {
            //when
            val flow = repo.getBooks().first()
            assertEquals(expected,flow)
            viewModel.getBooks()
        }
        val resource = Resource<ArrayList<Book>>(Status.ERROR,null,"")
        viewModel.booksResponse.observeForever {
            assertEquals(it,resource)
        }
    }
}