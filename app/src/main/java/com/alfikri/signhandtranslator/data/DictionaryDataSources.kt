package com.alfikri.signhandtranslator.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary

interface DictionaryDataSources {

    fun getDictionary(): LiveData<PagedList<DataDictionary>>

    fun insertDictionary(dataDictionary: List<DataDictionary>)

}