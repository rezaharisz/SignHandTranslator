package com.alfikri.signhandtranslator.ui.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.databinding.FragmentDictionaryBinding
import kotlinx.coroutines.launch

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvDictionary: RecyclerView
    private lateinit var dictionaryViewModel: DictionaryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvDictionary = view.findViewById(R.id.rv_dictionary)

        rvDictionary.layoutManager = LinearLayoutManager(activity)
        rvDictionary.setHasFixedSize(true)

        val factory = context?.let { DictionaryViewModelFactory.getInstance(it) }
        dictionaryViewModel = factory?.let { ViewModelProvider(this, it) }!![DictionaryViewModel::class.java]

        dictionaryViewModel.getDictionary().observe(viewLifecycleOwner, {
            getAdapter(it)
        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAdapter(dictionary: PagedList<DataDictionary>){
        val dictionaryAdapter = DictionaryAdapter(DictionaryAdapter.DictionaryClickListener {
            dictionaryViewModel.setBookmark()
        })

        dictionaryAdapter.submitList(dictionary)
        dictionaryAdapter.notifyDataSetChanged()
        rvDictionary.adapter = dictionaryAdapter
    }

}