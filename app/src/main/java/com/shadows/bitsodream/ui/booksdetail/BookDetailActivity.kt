package com.shadows.bitsodream.ui.booksdetail

import android.os.Bundle
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.Entry
import com.google.android.material.appbar.AppBarLayout
import com.shadows.bitsodream.R
import com.shadows.bitsodream.databinding.ActivityBookDetailBinding
import com.shadows.bitsodream.domain.models.Resource
import com.shadows.bitsodream.domain.models.Status
import com.shadows.bitsodream.domain.models.Ticker
import com.shadows.bitsodream.ui.BaseActivity
import com.shadows.bitsodream.ui.booksdetail.charts.MyLineChart
import com.shadows.bitsodream.utils.BOOK
import com.shadows.bitsodream.utils.presentation.PresentationUtils
import com.shadows.bitsodream.utils.presentation.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class BookDetailActivity:BaseActivity() {

    private val binding by viewBinding(ActivityBookDetailBinding::inflate)
    private lateinit var ticker: Ticker

    private val viewModel: BooksDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        coordinateMotion()
        //get book from last activity
        ticker = intent.getParcelableExtra(BOOK) as Ticker
        setView()
        //call the historic changes for the API

        viewModel.getBookHistoric(ticker.book)
        listenForHistoricChanges()


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.updateChart.setOnClickListener {
            viewModel.getBookHistoric(ticker.book)
        }

    }
    //method to assign values to text views
    private fun setView(){
        val currency = ticker.book.minor
        binding.tvBook.text = ticker.book.major
        binding.tvDayHigh.text = getString(R.string.day_high,currency ,ticker.high)
        binding.tvAskPrice.text = getString(R.string.ask_price,currency,ticker.ask)
        binding.tvDayLow.text = getString(R.string.day_low,currency, ticker.ask)
        binding.tv24Volume.text = getString(R.string.volume,ticker.volume)
        binding.tvValue.text = getString(R.string.last_value, currency,ticker.last)

        //calculation of the spread value according to instructions
       val spread = ticker.spread
        binding.tvSpread.text = getString(R.string.spread,currency,spread)
    }

    //This method will listen for the Historic of the book and use it to call the populate graph method with the data necessary for the graph
    private fun listenForHistoricChanges(){
        viewModel.bookHistoricResponse.observe(this, Observer{
            when(it){
                is Resource.Success ->{
                    isLoading(false)
                    //this will set the chart with the behaviour of the book in the last month
                    val historic = it.data as ArrayList<Entry>
                    val chart = MyLineChart(binding.chartYearDifference,historic,"${ticker.book.major}'s historic of the last month")
                    chart.setupChart()

                }
                is Resource.Loading ->{
                    isLoading(true)
                }
                is Resource.Failure -> {
                    isLoading(false)
                    PresentationUtils.showDialog(this,it.throwable.message?:"Error"){
                        viewModel.getBookHistoric(ticker.book)
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