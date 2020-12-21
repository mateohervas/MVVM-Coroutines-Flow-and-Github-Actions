package com.shadows.bitsodream.ui.booksdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.Entry
import com.google.android.material.appbar.AppBarLayout
import com.shadows.bitsodream.R
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import com.shadows.bitsodream.databinding.ActivityBookDetailBinding
import com.shadows.bitsodream.domain.models.Status
import com.shadows.bitsodream.ui.BaseActivity
import com.shadows.bitsodream.ui.booksdetail.charts.MyLineChart
import com.shadows.bitsodream.utils.BOOK
import com.shadows.bitsodream.utils.presentation.PresentationUtils
import com.shadows.bitsodream.utils.presentation.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class BookDetailActivity:BaseActivity() {

    private val binding by viewBinding(ActivityBookDetailBinding::inflate)
    private lateinit var book: Book

    private val viewModel: BooksDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        coordinateMotion()
        //get book from last activity
        book = intent.getParcelableExtra(BOOK) as Book
        setView()
        //call the historic changes for the API
        if(!book.book.isNullOrEmpty()){
            viewModel.getBookHistoric(book.book!!)
            listenForHistoricChanges()
        } else
            PresentationUtils.makeToast(this,"Could not retrieve graph")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.updateChart.setOnClickListener {
            viewModel.getBookHistoric(book.book!!)
        }

    }
    //method to assign values to text views
    private fun setView(){
        val currency = book.book?.substringAfter("_")
        binding.tvBook.text = book.book?.substringBefore("_")?.capitalize()?:"notAvailable"
        binding.tvDayHigh.text = getString(R.string.day_high,currency ,book.high)
        binding.tvAskPrice.text = getString(R.string.ask_price,currency,book.ask)
        binding.tvDayLow.text = getString(R.string.day_low,currency, book.ask)
        binding.tv24Volume.text = getString(R.string.volume,book.volume)
        binding.tvValue.text = getString(R.string.last_value, currency,book.last)

        //calculation of the spread value according to instructions
       val spread = book.ask?.toDoubleOrNull()?.let { book.bid?.toDoubleOrNull()?.minus(it) }
        binding.tvSpread.text = getString(R.string.spread,currency,spread.toString())
    }

    //This method will listen for the Historic of the book and use it to call the populate graph method with the data necessary for the graph
    private fun listenForHistoricChanges(){
        viewModel.bookHistoricResponse.observe(this, Observer{
            when(it.status){
                Status.SUCCESS ->{
                    isLoading(false)
                    //this will set the chart with the behaviour of the book in the last month
                    val historic = it.data as ArrayList<Entry>
                    val chart = MyLineChart(binding.chartYearDifference,historic,"${book.book}'s historic of the last month")
                    chart.setupChart()

                }
                Status.LOADING ->{
                    isLoading(true)
                }
                Status.ERROR -> {
                    isLoading(false)
                    PresentationUtils.showDialog(this,it.message?:"Error"){
                        viewModel.getBookHistoric(book.book!!)
                    }
                }
            }
        })
    }

    //method in charge of animating app bar layout in book detail view
    private fun coordinateMotion() {
        val listener = AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / binding.appbarLayout.totalScrollRange.toFloat()
            binding.motionLayout.progress = seekPosition
        }
        binding.appbarLayout.addOnOffsetChangedListener(listener)
    }


}