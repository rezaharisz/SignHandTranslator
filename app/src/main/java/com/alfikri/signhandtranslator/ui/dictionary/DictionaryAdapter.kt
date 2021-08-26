package com.alfikri.signhandtranslator.ui.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.databinding.ItemDictionaryBinding
import com.bumptech.glide.Glide

class DictionaryAdapter(private val listDictionary: ArrayList<DataDictionary>): RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    fun setData(dataDictionary: List<DataDictionary>){
        listDictionary.addAll(dataDictionary)
        notifyDataSetChanged()
    }

    inner class DictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDictionaryBinding.bind(itemView)

        fun bind(dataDictionary: DataDictionary){
            Glide.with(itemView)
                .load(dataDictionary.imageHand)
                .override(150,150)
                .into(binding.ivHandsign)
            binding.tvAlphabet.text = dataDictionary.alphabet
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false)
        return DictionaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(listDictionary[position])
    }

    override fun getItemCount(): Int {
        return listDictionary.size
    }

}