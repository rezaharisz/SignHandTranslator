package com.alfikri.signhandtranslator.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.findNavController
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.FragmentProfileBinding
import com.alfikri.signhandtranslator.ui.edit.EditActivity
import com.alfikri.signhandtranslator.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

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

        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())

        callFirebase(user, userDb)

        binding.tvEdit.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            activity?.finish()
        }

        binding.btnAbout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_about, null)
            alertDialog.setView(dialogView)
            alertDialog.setCancelable(true)
            alertDialog.create()
            alertDialog.show()

            userDb?.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                    try {
                        val tvAbout = dialogView.findViewById<TextView>(R.id.tv_about)
                        tvAbout.text = snapshot.child(ABOUT_ME).value.toString()
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(@NonNull error: DatabaseError) {
                    Log.e(ERROR_MSG, error.message)
                }

            })
        }

        binding.btnBookmarks.setOnClickListener {
            view.findNavController().navigate(R.id.action_profile_to_bookmarks)
        }
    }

    private fun callFirebase(user: FirebaseUser?, userDb: DatabaseReference?){
        val storageReference = FirebaseStorage.getInstance().getReference("images/${user?.uid.toString()}")

        userDb?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                try {
                    binding.tvName.text = snapshot.child(NAME).value.toString()
                    binding.tvUsername.text = snapshot.child(USERNAME).value.toString()
                    binding.tvCity.text = snapshot.child(CITY).value.toString()
                    binding.include.tvPhoneNumber.text = snapshot.child(PHONE_NUMBER).value.toString()

                    if (snapshot.child(GENDER).value != null){
                        binding.include.tvGender.text = snapshot.child(GENDER).value.toString()
                    } else{
                        binding.include.tvGender.text = ""
                    }

                    if (user != null) {
                        binding.include.tvEmail.text = user.email
                    }

                    storageReference.downloadUrl.addOnSuccessListener {
                        context?.let { it1 ->
                            binding.ivProfile.setImageURI(null)

                            val requestOptions = RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)

                            Glide.with(it1)
                                .load(it)
                                .override(130,130)
                                .apply(requestOptions)
                                .into(binding.ivProfile)
                        }
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