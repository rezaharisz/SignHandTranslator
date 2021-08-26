package com.alfikri.signhandtranslator.di

import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.data.local.sources.LocalDataSources
import com.alfikri.signhandtranslator.utils.DataDummy

object Injection {
    fun getRepository(): DictionaryRepository{
        val localDataSources = LocalDataSources.getInstance(DataDummy)

        return DictionaryRepository.getInstance(localDataSources)
    }
}