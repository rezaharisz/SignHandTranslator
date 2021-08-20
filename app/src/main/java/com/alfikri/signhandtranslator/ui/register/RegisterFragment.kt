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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null

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
            when{
                binding.edFullname.text.toString().isEmpty() -> {
                    binding.edFullname.error = "Please enter your name"
                }
                binding.edEmail.text.toString().isEmpty() -> {
                    binding.edEmail.error = "Please enter your email"
                }
                binding.edPassword.text.toString().isEmpty() -> {
                    binding.edPassword.error = "Please enter your password"
                }
                else -> {
                    callFirebase()
                }
            }

        }

        binding.tvAlreadymember.setOnClickListener {
            view.findNavController().navigate(R.id.action_register_to_login)
        }
    }

    private fun callFirebase(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference?.child("PROFILE")
        showProgress(true)

        firebaseAuth.createUserWithEmailAndPassword(binding.edEmail.text.toString(), binding.edPassword.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val currentUser = firebaseAuth.currentUser
                    val userDb = databaseReference?.child(currentUser?.uid.toString())

                    userDb?.child("name")?.setValue(binding.edFullname.text.toString())

                    showProgress(false)

                    Toast.makeText(context, "Register Successfull, Please Login First", Toast.LENGTH_SHORT).show()

                    view?.findNavController()?.navigate(R.id.action_register_to_login)
                } else{
                    showProgress(false)

                    Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
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