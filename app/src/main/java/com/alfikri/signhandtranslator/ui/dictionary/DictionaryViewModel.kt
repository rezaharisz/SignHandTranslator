package com.alfikri.signhandtranslator.ui.dictionary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary

class DictionaryViewModel(private val dictionaryRepository: DictionaryRepository): ViewModel() {
    fun getDictionary(): MutableLiveData<ArrayList<DataDictionary>> = dictionaryRepository.getDictionary()
}