package com.alfikri.signhandtranslator.ui.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.databinding.ItemDictionaryBinding
import com.bumptech.glide.Glide

class DictionaryAdapter(private val dictionaryClickListener: DictionaryClickListener): PagedListAdapter<DataDictionary, DictionaryAdapter.DictionaryViewHolder>(DIFF_CALLBACK) {

    class DictionaryViewHolder(private val binding: ItemDictionaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataDictionary: DataDictionary, clickListener: (dictionary: DataDictionary) -> Unit){
            Glide.with(itemView)
                .load(dataDictionary.imageHand)
                .override(150,150)
                .into(binding.ivHandsign)
            binding.tvAlphabet.text = dataDictionary.alphabet
            binding.btnBookmark.setOnClickListener { clickListener(dataDictionary) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val itemDictionary = ItemDictionaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DictionaryViewHolder(itemDictionary)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val dictionary = getItem(position)
        if (dictionary != null){
            holder.bind(dictionary, dictionaryClickListener.clickListener)
            holder.bind(getItem(position)!!, dictionaryClickListener.clickListener)
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

    data class DictionaryClickListener(val clickListener: (dictionary: DataDictionary) -> Unit)

}