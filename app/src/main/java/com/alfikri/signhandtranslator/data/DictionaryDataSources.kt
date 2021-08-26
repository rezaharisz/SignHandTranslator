package com.alfikri.signhandtranslator.data

import androidx.lifecycle.MutableLiveData
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary

interface DictionaryDataSources {
    fun getDictionary(): MutableLiveData<ArrayList<DataDictionary>>
}