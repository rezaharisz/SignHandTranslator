package com.alfikri.signhandtranslator.data.local.sources

import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.utils.DataDummy

class LocalDataSources private constructor(private val dataDummy: DataDummy){

    fun getDictionary(): ArrayList<DataDictionary> = dataDummy.getDictionary()

    companion object{
        private var INSTANCE: LocalDataSources? = null

        fun getInstance(dataDummy: DataDummy): LocalDataSources =
            INSTANCE ?: LocalDataSources(dataDummy)
    }

}