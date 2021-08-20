package com.alfikri.signhandtranslator.resetpass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alfikri.signhandtranslator.databinding.FragmentResetPassBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPassFragment : Fragment() {

    private var _binding: FragmentResetPassBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnReset.setOnClickListener {
            if (binding.edEmail.text?.isEmpty() == true){
                binding.edEmail.error = "Please enter your email"
            } else{
                val email = binding.edEmail.text.toString()
                resetPassword(email)
            }
        }
    }

    private fun resetPassword(email: String){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context, "Check your email for reset password link", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context, "Reset password failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}