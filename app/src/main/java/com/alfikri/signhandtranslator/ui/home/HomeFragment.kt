package com.alfikri.signhandtranslator.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.alfikri.signhandtranslator.databinding.FragmentHomeBinding
import com.alfikri.signhandtranslator.ui.camera.CameraActivity
import com.alfikri.signhandtranslator.utils.ERROR_MSG
import com.alfikri.signhandtranslator.utils.PROFILE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.StringBuilder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference?.child(PROFILE)

        callFirebase()

        binding.btnCamera.setOnClickListener {
            val intent = Intent(context, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun callFirebase(){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())

        userDb?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                try {
                    binding.welcomeUser.text = StringBuilder("Hi, " + snapshot.child("name").value.toString() + " !")
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onCancelled(@NonNull error: DatabaseError) {
                Log.e(ERROR_MSG, error.message)
            }

        })

    }

}