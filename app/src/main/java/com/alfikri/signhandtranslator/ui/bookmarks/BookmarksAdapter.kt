package com.alfikri.signhandtranslator.ui.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.databinding.ItemBookmarksBinding
import com.bumptech.glide.Glide

class BookmarksAdapter : PagedListAdapter<DataDictionary, BookmarksAdapter.BookmarksViewHolder>(DIFF_CALLBACK) {

    class BookmarksViewHolder(private val binding: ItemBookmarksBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataDictionary: DataDictionary){
            Glide.with(itemView)
                .load(dataDictionary.imageHand)
                .override(150,150)
                .into(binding.ivHandsign)
            binding.tvAlphabet.text = dataDictionary.alphabet
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val itemDictionary = ItemBookmarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksViewHolder(itemDictionary)
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        val bookmarksItem = getItem(position)
        if (bookmarksItem != null){
            holder.bind(bookmarksItem)
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataDictionary>(){
            override fun areItemsTheSame(oldItem: DataDictionary, newItem: DataDictionary): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataDictionary, newItem: DataDictionary): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}