package com.alfikri.signhandtranslator.data

import androidx.lifecycle.MutableLiveData
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.data.local.sources.LocalDataSources
import com.alfikri.signhandtranslator.utils.DataDummy

class DictionaryRepository private constructor(private val localDataSources: LocalDataSources): DictionaryDataSources {

    private val listDictionary = MutableLiveData<ArrayList<DataDictionary>>()

    override fun getDictionary(): MutableLiveData<ArrayList<DataDictionary>> {
        val dictionaryItem = localDataSources.getDictionary()
        listDictionary.postValue(dictionaryItem)

        return listDictionary
    }

    companion object{
        @Volatile
        private var instance: DictionaryRepository? = null

        fun getInstance(localDataSources: LocalDataSources): DictionaryRepository =
            instance ?: synchronized(this){
                instance ?: DictionaryRepository(localDataSources).apply { instance = this }
            }
    }
}