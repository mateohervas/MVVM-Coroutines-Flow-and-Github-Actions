package com.shadows.bitsodream.ui.books

import androidx.lifecycle.*
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.domain.models.Resource
import com.shadows.bitsodream.domain.models.Ticker
import com.shadows.bitsodream.domain.repository.BooksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BooksViewModel(private val booksRepository: BooksRepository): ViewModel() {

    val booksResponse = MutableLiveData<Resource<List<Ticker>?>>()

    //this method will get the result of the APIs and handle the states
    fun getBooks(){
        viewModelScope.launch {
            booksRepository.getBooks()
                .onStart {
                    booksResponse.value = Resource.Loading()
                }
                .catch { exception ->
                    booksResponse.value = Resource.Failure(exception)
                }
                .collect {
                    if(it.error==null){
                        val payload = it.payload as List<Ticker>
                        booksResponse.value = Resource.Success(payload)
                    }else{
                        val throwable = Throwable(it.error.message)
                        booksResponse.value = Resource.Failure(throwable)
                    }

                }
        }
    }

}