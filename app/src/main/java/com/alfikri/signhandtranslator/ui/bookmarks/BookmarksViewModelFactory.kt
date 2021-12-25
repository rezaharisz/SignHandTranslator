package com.alfikri.signhandtranslator.ui.bookmarks

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.di.Injection

class BookmarksViewModelFactory(private val dictionaryRepository: DictionaryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(BookmarksViewModel::class.java) -> {
                BookmarksViewModel(dictionaryRepository) as T
            }
            else -> throw Throwable("Unkwon ViewModel Class" + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: BookmarksViewModelFactory? = null

        fun getInstance(context: Context): BookmarksViewModelFactory =
            instance ?: synchronized(this){
                instance ?: BookmarksViewModelFactory(Injection.getRepository(context)).apply { instance = this }
            }
    }

}