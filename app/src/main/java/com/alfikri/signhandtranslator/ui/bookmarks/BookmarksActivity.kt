package com.alfikri.signhandtranslator.ui.bookmarks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.databinding.ActivityBookmarksBinding

class BookmarksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarksBinding
    private lateinit var bookmarksRv: RecyclerView
    private lateinit var bookmarksViewModel: BookmarksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }

        bookmarksRv = findViewById(R.id.rv_bookmarks)
        bookmarksRv.layoutManager = LinearLayoutManager(this)
        bookmarksRv.setHasFixedSize(true)

        val factory = BookmarksViewModelFactory.getInstance(this)
        bookmarksViewModel = ViewModelProvider(this, factory)[BookmarksViewModel::class.java]

        bookmarksViewModel.getBookmarks().observe(this, {
            getAdapter(it)
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAdapter(dictionary: PagedList<DataDictionary>){
        val bookmarksAdapter = BookmarksAdapter()

        bookmarksAdapter.submitList(dictionary)
        bookmarksAdapter.notifyDataSetChanged()
        bookmarksRv.adapter = bookmarksAdapter
    }
}