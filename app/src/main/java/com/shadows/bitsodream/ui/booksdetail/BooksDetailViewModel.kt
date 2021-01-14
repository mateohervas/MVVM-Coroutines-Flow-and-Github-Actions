package com.shadows.bitsodream.ui.booksdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import com.shadows.bitsodream.domain.models.BookDomain
import com.shadows.bitsodream.domain.models.Resource
import com.shadows.bitsodream.domain.repository.BookDetailRepository
import com.shadows.bitsodream.utils.logD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BooksDetailViewModel(private val bookDetailRepository: BookDetailRepository): ViewModel() {

    //this Mutable Live Data object uses an ArrayList of an Entry because the view is interested only in Entries for the chart
    val bookHistoricResponse = MutableLiveData<Resource<ArrayList<Entry>>>()

    //method that will listen for the repository method results and will emit the state with the data accordingly
    fun getBookHistoric(book:BookDomain){
        viewModelScope.launch {
            bookDetailRepository.getBookHistory("${book.major}_${book.minor}")
                .onStart {
                    bookHistoricResponse.value = Resource.loading(null)
                }.catch {
                    bookHistoricResponse.value = Resource.error(null,it.message?:"Error")
                }
                .collect {

                        val bookHistoric = it
                    //this snippet gets the data from the repository and transforms it to Entries for the chart
                        if(bookHistoric.isNotEmpty()){
                            val graphEntries = ArrayList<Entry>()
                            bookHistoric.forEachIndexed { index, bookStatistic ->
                                val entry = Entry(index.toFloat(),bookStatistic.value!!.toFloat())
                                graphEntries.add(entry)
                            }
                            bookHistoricResponse.value = Resource.success(graphEntries)
                        }

                }
        }
    }

}