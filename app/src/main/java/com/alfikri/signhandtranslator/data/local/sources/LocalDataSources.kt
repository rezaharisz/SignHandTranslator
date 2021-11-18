package com.alfikri.signhandtranslator.data.local.sources

import androidx.paging.DataSource
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.data.local.room.SignHandDao

class LocalDataSources private constructor(private val signHandDao: SignHandDao){

    fun getDictionary(): DataSource.Factory<Int, DataDictionary> = signHandDao.getData()

    fun insertDictionary(dataDictionary: List<DataDictionary>) = signHandDao.insertData(dataDictionary)

    fun getBookmarks(): DataSource.Factory<Int, DataDictionary> = signHandDao.getBookmarks()

    fun setBookmark(dataDictionary: DataDictionary, state: Boolean){
        dataDictionary.setFavorite = state
        signHandDao.updateBookmarks(dataDictionary)
    }

    companion object{
        private var INSTANCE: LocalDataSources? = null

        fun getInstance(signHandDao: SignHandDao): LocalDataSources =
            INSTANCE ?: LocalDataSources(signHandDao)
    }

}