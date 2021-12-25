package com.alfikri.signhandtranslator.di

import android.content.Context
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.data.local.room.SignHandDatabase
import com.alfikri.signhandtranslator.data.local.sources.LocalDataSources
import com.alfikri.signhandtranslator.utils.AppExecutors

object Injection {
    fun getRepository(context: Context): DictionaryRepository{
        val database = SignHandDatabase.getInstance(context)
        val localDataSources = LocalDataSources.getInstance(database.signHandDao())
        val appExecutors = AppExecutors()

        return DictionaryRepository.getInstance(localDataSources, appExecutors)
    }
}