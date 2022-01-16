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
import com.alfikri.signhandtranslator.utils.*
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

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference?.child(PROFILE)

        binding.btnRegister.setOnClickListener {
            when{
                binding.edName.text.toString().isEmpty() -> {
                    binding.edName.error = "Please enter your name"
                }
                binding.edEmail.text.toString().isEmpty() -> {
                    binding.edEmail.error = "Please enter your email"
                }
                binding.edPassword.text.toString().isEmpty() -> {
                    binding.edPassword.error = "Please enter your password"
                }
                else -> {
                    if (binding.edPassword.text.toString() == binding.edRepeatPassword.text.toString()){
                        callFirebase()
                    } else{
                        binding.edPassword.error = "Password must same"
                        binding.edRepeatPassword.error = "Password must same"
                    }
                }
            }
        }

        binding.tvAlreadymember.setOnClickListener {
            view.findNavController().navigate(R.id.action_register_to_login)
        }
    }

    private fun callFirebase(){
        showProgress(true)

        firebaseAuth.createUserWithEmailAndPassword(binding.edEmail.text.toString(), binding.edPassword.text.toString())
            .addOnCompleteListener {
                val currentUser = firebaseAuth.currentUser
                val userDb = databaseReference?.child(currentUser?.uid.toString())

                userDb?.child(USERNAME)?.setValue(binding.edUsername.text.toString())
                userDb?.child(NAME)?.setValue(binding.edName.text.toString())
                userDb?.child(CITY)?.setValue(binding.edCity.text.toString())

                showProgress(false)

                Toast.makeText(context, "Register Successful, Please Login First", Toast.LENGTH_SHORT).show()

                view?.findNavController()?.navigate(R.id.action_register_to_login)

                binding.btnRegister.isEnabled = false
            }
            .addOnFailureListener {
                showProgress(false)
                Toast.makeText(context, "Register failed due to ${it.message}", Toast.LENGTH_SHORT).show()
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