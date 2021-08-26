package com.alfikri.signhandtranslator.ui.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfikri.signhandtranslator.data.DictionaryRepository
import com.alfikri.signhandtranslator.di.Injection

class DictionaryViewModelFactory(private val dictionaryRepository: DictionaryRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(DictionaryViewModel::class.java) -> {
                DictionaryViewModel(dictionaryRepository) as T
            }
            else -> throw Throwable("Unkwon ViewModel Class" + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: DictionaryViewModelFactory? = null

        fun getInstance(): DictionaryViewModelFactory =
            instance ?: synchronized(this){
                instance ?: DictionaryViewModelFactory(Injection.getRepository()).apply { instance = this }
            }
    }

}