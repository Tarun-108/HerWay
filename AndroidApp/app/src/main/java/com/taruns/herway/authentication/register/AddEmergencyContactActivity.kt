package com.taruns.herway.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityAddEmergencyContactBinding
import com.taruns.herway.databinding.ActivityCreatePinBinding
import com.taruns.herway.models.ContactModel
import com.taruns.herway.models.UserModel
import com.taruns.herway.models.post_list_model
import java.text.SimpleDateFormat
import java.util.*

class AddEmergencyContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEmergencyContactBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmergencyContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
        val userModel: UserModel = intent.getSerializableExtra("userModel") as UserModel

        binding.finishSetup.setOnClickListener{
            val contact: ContactModel = ContactModel("","","")
            contact.name = binding.name.text.toString()
            contact.email = binding.email.text.toString()
            contact.phone = binding.number.text.toString()
            userModel.eContacts?.add(contact)
            Log.d("Tagggg", contact.phone.toString())
            Log.d("Tagggg", userModel.phone.toString())
            userModel.phone?.let { it1 ->
                database.child("Users").child(it1).setValue(userModel)
            }
            val intent =  Intent(this@AddEmergencyContactActivity, MainActivity::class.java)
            Toast.makeText(this@AddEmergencyContactActivity, "Profile Created", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }



    }
}