package com.alfikri.signhandtranslator.ui.edit

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.alfikri.signhandtranslator.databinding.FragmentEditBinding
import com.alfikri.signhandtranslator.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.alfikri.signhandtranslator.R

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
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

        val itemSpinner = resources.getStringArray(R.array.gender_array)

        spinnerAdapter(itemSpinner)

        binding.spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val value = parent?.getItemAtPosition(position).toString()
                if (value == itemSpinner[0]){
                    (view as TextView).setTextColor(Color.GRAY)
                } else{
                    (view as TextView).setTextColor(Color.WHITE)
                }
                Toast.makeText(context, resources.getStringArray(R.array.gender_array)[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.clearFocus()
            }

        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.edName.text.toString()
            val username = binding.edUsername.text.toString()
            val phone = binding.edPhone.text.toString()
            val city = binding.edCity.text.toString()
            val about = binding.edAbout.text.toString()

            updateFirebase(name, username, phone, city, about)
        }
    }

    private fun callFirebase(){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())

        userDb?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.edName.setText(snapshot.child(NAME).value.toString())
                binding.edUsername.setText(snapshot.child(USERNAME).value.toString())
                binding.edPhone.setText(snapshot.child(PHONE_NUMBER).value.toString())
                binding.edCity.setText(snapshot.child(CITY).value.toString())
                binding.edAbout.setText(snapshot.child(ABOUT_ME).value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ERROR_MSG, error.message)
            }

        })
    }

    private fun updateFirebase(name: String, username: String, phone: String, city: String, about: String){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())
        val userHashMap = mapOf(
            NAME to name,
            USERNAME to username,
            PHONE_NUMBER to phone,
            CITY to city,
            ABOUT_ME to about
        )

        userDb?.updateChildren(userHashMap
        )?.addOnSuccessListener {
            Toast.makeText(context, "Data has been updated", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
            Toast.makeText(context, "Failed Updating Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun spinnerAdapter(genderArray: Array<String>){
        val spinnerAdapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, genderArray){
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0){
                    view.setTextColor(Color.GRAY)
                } else{
                    view.setTextColor(Color.WHITE)
                }

                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGender.adapter = spinnerAdapter
    }

}