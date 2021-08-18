package com.alfikri.signhandtranslator.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            //DO SOME LOGIN PROCESS WITH FIREBASE

            view.findNavController().navigate(R.id.action_login_to_homepage)
        }

        binding.tvForgotpassword.setOnClickListener {
            //DO FORGOT PASSWORD ACTION
        }

        binding.tvRegister.setOnClickListener {
            view.findNavController().navigate(R.id.action_login_to_register)
        }
    }

}