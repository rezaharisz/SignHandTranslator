package com.alfikri.signhandtranslator.ui.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary

class BookmarksViewModel(private val dictionaryRepository: DictionaryRepository): ViewModel() {

    fun getBookmarks(): LiveData<PagedList<DataDictionary>> = dictionaryRepository.getBookmarks()

}