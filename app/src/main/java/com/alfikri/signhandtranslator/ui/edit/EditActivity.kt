package com.alfikri.signhandtranslator.ui.edit

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.databinding.ActivityEditBinding
import com.alfikri.signhandtranslator.utils.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var databaseReference: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var genderText: String? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = Html.fromHtml("<font color=#FFFFFF>" + "Edit" + "</font>", 0)

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

                genderText = resources.getStringArray(R.array.gender_array)[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.clearFocus()
            }
        }

        binding.tvChangePic.setOnClickListener {
            pickImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null) {
                imageUri = data.data
                Glide.with(this)
                    .load(imageUri)
                    .override(50,50)
                    .into(binding.ivProfile)

                uploadImage()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save -> {
                val name = binding.edName.text.toString()
                val username = binding.edUsername.text.toString()
                val phone = binding.edPhone.text.toString()
                val city = binding.edCity.text.toString()
                val about = binding.edAbout.text.toString()

                updateFirebase(name, username, phone, city, genderText.toString(), about)

                super.onBackPressed()
            }

            R.id.action_close -> {
                super.onBackPressed()
            }
            else -> null
        } ?: return super.onOptionsItemSelected(item)

        return true
    }

    private fun callFirebase(){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())

        userDb?.addValueEventListener(object : ValueEventListener {
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

    private fun updateFirebase(name: String, username: String, phone: String, city: String, gender: String, about: String){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())
        val userHashMap = mapOf(
            NAME to name,
            USERNAME to username,
            PHONE_NUMBER to phone,
            CITY to city,
            GENDER to gender,
            ABOUT_ME to about
        )

        userDb?.updateChildren(userHashMap
        )?.addOnSuccessListener {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show()
        }?.addOnFailureListener {
            Toast.makeText(this, "Failed Updating Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun spinnerAdapter(genderArray: Array<String>){
        val user = firebaseAuth.currentUser
        val userDb = databaseReference?.child(user?.uid.toString())

        val spinnerAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderArray){
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

        userDb?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.edName.setText(snapshot.child(NAME).value.toString())

                val spinnerPosition = spinnerAdapter.getPosition(snapshot.child(GENDER).value.toString())
                binding.spGender.setSelection(spinnerPosition)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ERROR_MSG, error.message)
            }

        })
    }

    private fun pickImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun uploadImage(){
        val user = firebaseAuth.currentUser

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageReference = FirebaseStorage.getInstance().getReference("images/${user?.uid.toString()}")

        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {
                binding.ivProfile.setImageURI(null)
                Toast.makeText(this, "Image successfully uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing){
                    progressDialog.dismiss()
                }
            } .addOnFailureListener{
                if (progressDialog.isShowing){
                    progressDialog.dismiss()
                }
                Toast.makeText(this, "Failed uploading image", Toast.LENGTH_SHORT).show()
            }
        }
    }

}