package com.shadows.bitsodream.ui.books.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shadows.bitsodream.R
import com.shadows.bitsodream.data.remote.model.Book
import com.shadows.bitsodream.ui.booksdetail.BookDetailActivity
import com.shadows.bitsodream.utils.BOOK
import kotlinx.android.synthetic.main.cardview_book.view.*

class BooksAdapter(): RecyclerView.Adapter<BooksAdapter.ViewHolder>(){

    private val books = ArrayList<Book>()

    fun addAll(list: ArrayList<Book>){
        list.forEach {
            item ->
            books.add(item)
            notifyItemInserted(books.size-1)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_book,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(books[position])
    }

    override fun getItemCount(): Int = books.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(book: Book){
            itemView.tv_value.text = book.book?.substringBefore("_")
            itemView.tv_current_price.text = itemView.context.getString(R.string.x_space_y,book.book?.substringAfter("_"),book.last)
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,BookDetailActivity::class.java)
                intent.putExtra(BOOK,book)
                itemView.context.startActivity(intent)
            }
        }

    }




}

