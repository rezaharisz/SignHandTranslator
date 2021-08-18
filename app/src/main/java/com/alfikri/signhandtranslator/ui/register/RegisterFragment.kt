package com.alfikri.signhandtranslator.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            //DO REGISTER ACTION WITH FIREBASE

            Toast.makeText(context, "Register Successfull, Please Login First", Toast.LENGTH_SHORT).show()

            view.findNavController().navigate(R.id.action_register_to_login)
        }

        binding.tvAlreadymember.setOnClickListener {
            view.findNavController().navigate(R.id.action_register_to_login)
        }
    }

}