package com.alfikri.signhandtranslator.ui.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary

class DictionaryViewModel(private val dictionaryRepository: DictionaryRepository): ViewModel() {

        fun getDictionary(): LiveData<PagedList<DataDictionary>> = dictionaryRepository.getDictionary()

}