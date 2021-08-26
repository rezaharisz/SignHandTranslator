package com.alfikri.signhandtranslator.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.FragmentProfileBinding
import com.alfikri.signhandtranslator.ui.edit.EditActivity
import com.alfikri.signhandtranslator.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null

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

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.reference?.child(PROFILE)

        callFirebase()

        binding.tvEdit.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            context?.startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            //DO ACTION TO SHOW ABOUT DIALOG
        }

        binding.btnBookmarks.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_bookmarks)
        }
    }


    private fun callFirebase(){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())

        userDb?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                try {
                    binding.tvName.text = snapshot.child(NAME).value.toString()
                    binding.tvUsername.text = snapshot.child(USERNAME).value.toString()
                    binding.tvCity.text = snapshot.child(CITY).value.toString()
                    binding.include.tvPhoneNumber.text = snapshot.child(PHONE_NUMBER).value.toString()
                    binding.include.tvGender.text = snapshot.child(GENDER).value.toString()

                    if (user != null) {
                        binding.include.tvEmail.text = user.email
                    }
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