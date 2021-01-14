package com.shadows.bitsodream.ui.books.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shadows.bitsodream.R
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.databinding.CardviewBookBinding
import com.shadows.bitsodream.domain.models.Ticker
import com.shadows.bitsodream.ui.booksdetail.BookDetailActivity
import com.shadows.bitsodream.utils.BOOK
import kotlinx.android.synthetic.main.cardview_book.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class BooksAdapter(): RecyclerView.Adapter<BooksAdapter.ViewHolder>(){

    private val books: ArrayList<Ticker> = ArrayList<Ticker>()

    fun addAll(list: List<Ticker>){
        books.clear()
        list.forEach {
            item ->
            books.add(item)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_book,parent,false)
        val binding = CardviewBookBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(books[position])
    }

    override fun getItemCount(): Int = books.size

    inner class ViewHolder(val binding: CardviewBookBinding): RecyclerView.ViewHolder(binding.root){

        @ExperimentalCoroutinesApi
        fun bindView(book: Ticker){
            binding.apply {
                tvValue.text = book.book.major
                tvCurrentPrice.text = itemView.context.getString(R.string.x_space_y,book.book.minor,book.last)
                root.setOnClickListener {
                    val intent = Intent(itemView.context,BookDetailActivity::class.java)
                    intent.putExtra(BOOK,book)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }




}

