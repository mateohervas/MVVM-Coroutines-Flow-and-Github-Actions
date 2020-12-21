package com.shadows.bitsodream.data.remote

import com.shadows.bitsodream.BuildConfig
import com.shadows.bitsodream.data.remote.model.BaseResponse
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.data.remote.model.BookStatistic
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface MyBitsoDataSource {

    @GET("v3/ticker/")
    suspend fun getBooks(): BaseResponse<ArrayList<Book>>

    @GET("https://bitso.com/trade/chartJSON/{book}/1month")
    suspend fun getChart(@Path("book")book:String): ArrayList<BookStatistic>

    companion object{

        fun create(): MyBitsoDataSource{

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            //BuildConfig.HOST gets the host base url depending on the flavor. For this assignment both flavors have the same URL

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(MyBitsoDataSource::class.java)
        }
    }
}