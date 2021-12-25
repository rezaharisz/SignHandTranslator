package com.alfikri.signhandtranslator.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.data.local.sources.LocalDataSources
import com.alfikri.signhandtranslator.utils.AppExecutors
import com.alfikri.signhandtranslator.utils.PAGE_SIZE
import com.alfikri.signhandtranslator.utils.PLACEHOLDERS

class DictionaryRepository private constructor(private val localDataSources: LocalDataSources, private val appExecutors: AppExecutors): DictionaryDataSources {

    companion object{
        @Volatile
        private var instance: DictionaryRepository? = null

        fun getInstance(localDataSources: LocalDataSources, appExecutors: AppExecutors): DictionaryRepository =
            instance ?: synchronized(this){
                instance ?: DictionaryRepository(localDataSources, appExecutors).apply { instance = this }
            }
    }

    override fun getDictionary(): LiveData<PagedList<DataDictionary>> {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(PLACEHOLDERS)
            .build()

        return LivePagedListBuilder(localDataSources.getDictionary(), config).build()
    }

    override fun insertDictionary(dataDictionary: List<DataDictionary>) {
        localDataSources.insertDictionary(dataDictionary)
    }

    override fun getBookmarks(): LiveData<PagedList<DataDictionary>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return LivePagedListBuilder(localDataSources.getBookmarks(), config).build()
    }

    override fun setBookmark(dataDictionary: DataDictionary, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSources.setBookmark(dataDictionary, state)
        }
    }

}