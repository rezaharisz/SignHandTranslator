package com.alfikri.signhandtranslator.di

import android.content.Context
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.data.local.room.SignHandDatabase
import com.alfikri.signhandtranslator.data.local.sources.LocalDataSources

object Injection {
    fun getRepository(context: Context): DictionaryRepository{
        val database = SignHandDatabase.getInstance(context)
        val localDataSources = LocalDataSources.getInstance(database.signHandDao())

        return DictionaryRepository.getInstance(localDataSources)
    }
}