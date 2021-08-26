package com.alfikri.signhandtranslator.ui.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var dictionaryAdapter: DictionaryAdapter
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

        val factory = DictionaryViewModelFactory.getInstance()
        dictionaryViewModel = ViewModelProvider(this, factory)[DictionaryViewModel::class.java]

        dictionaryAdapter = DictionaryAdapter(mutableListOf<DataDictionary>() as ArrayList<DataDictionary>)

        dictionaryViewModel.getDictionary().observe(viewLifecycleOwner, {
            setAdapter()

            if (it != null){
                dictionaryAdapter.setData(it)
                it.clear()
            }
        })
    }

    private fun setAdapter(){
        binding.rvDictionary.adapter = dictionaryAdapter
        binding.rvDictionary.layoutManager = LinearLayoutManager(context)
        binding.rvDictionary.setHasFixedSize(true)
    }

}