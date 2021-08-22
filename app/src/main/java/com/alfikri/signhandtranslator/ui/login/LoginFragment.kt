package com.alfikri.signhandtranslator.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

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

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            when{
                binding.edEmail.text.toString().isEmpty() -> binding.edEmail.error = "Please enter your email"
                binding.edPassword.text.toString().isEmpty() -> binding.edPassword.error = "Please enter your password"
                else -> {
                    callFirebase()
                }
            }
        }

        binding.tvForgotpassword.setOnClickListener {
            view.findNavController().navigate(R.id.action_login_to_reset)
        }

        binding.tvRegister.setOnClickListener {
            view.findNavController().navigate(R.id.action_login_to_register)
        }
    }

    private fun callFirebase(){
        showProgress(true)

        firebaseAuth.signInWithEmailAndPassword(binding.edEmail.text.toString(), binding.edPassword.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    showProgress(false)
                    view?.findNavController()?.navigate(R.id.action_login_to_homepage)

                    binding.btnLogin.isEnabled = false
                } else{
                    showProgress(false)
                    Toast.makeText(context, "Login failed, please check your email and password", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun showProgress(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

}