package com.alfikri.signhandtranslator.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEdit.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_edit)
        }

        binding.btnAbout.setOnClickListener {
            //DO ACTION TO SHOW ABOUT DIALOG
        }

        binding.btnBookmarks.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_bookmarks)
        }
    }

}