package com.shadows.bitsodream.ui.books

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shadows.bitsodream.R
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.databinding.ActivityMainBinding
import com.shadows.bitsodream.domain.models.Resource
import com.shadows.bitsodream.domain.models.Status
import com.shadows.bitsodream.domain.models.Ticker
import com.shadows.bitsodream.ui.BaseActivity
import com.shadows.bitsodream.ui.books.adapters.BooksAdapter
import com.shadows.bitsodream.utils.logD
import com.shadows.bitsodream.utils.presentation.PresentationUtils
import com.shadows.bitsodream.utils.presentation.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class BooksActivity:BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: BooksViewModel by viewModel()
    private val booksAdapter by lazy {
        BooksAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        viewModel.getBooks()
        setUpRecyclerView()
        populateRecyclerView()

        binding.refresher.setOnRefreshListener {
            viewModel.getBooks()
            binding.refresher.isRefreshing = false
        }
    }

    //method to observe changes in API call and add values to Recycler View
    private fun populateRecyclerView(){
        viewModel.booksResponse.observe(this, {
            when(it){
                is Resource.Success ->{
                    isLoading(false)
                    val books = it.data as List<Ticker>
                    booksAdapter.addAll(books)
                }
                is Resource.Loading ->{
                    isLoading(true)
                }
                is Resource.Failure -> {
                    isLoading(false)
                    PresentationUtils.showDialog(this,it.throwable.message?:"Error"){
                        viewModel.getBooks()
                    }
                }
            }
        })
    }

    private fun setUpRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvBooks.layoutManager = layoutManager
        binding.rvBooks.adapter = booksAdapter
    }


}