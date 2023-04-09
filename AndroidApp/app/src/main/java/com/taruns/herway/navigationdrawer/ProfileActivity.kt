package com.taruns.herway.navigationdrawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityFeedbackBinding
import com.taruns.herway.databinding.ActivityProfileBinding
import com.taruns.herway.models.UserModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
     var  userDataModel: UserModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userDataModel= intent.getSerializableExtra("userModel") as UserModel?
        setData()
        binding.UpdateProfile.setOnClickListener {
            if(check_validation()){
               //update to firebase

                var reference = Firebase.database.reference
                userDataModel?.name=binding.edName.text.toString().trim()
                userDataModel?.email=binding.edEmail.text.toString().trim()

                reference.child("Users").child(userDataModel?.phone!!).setValue(userDataModel)
                Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                var intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.changePinBack.setOnClickListener {
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setData() {
        binding.phoneText.text=userDataModel?.phone
        binding.edName.hint=userDataModel?.name
        binding.edEmail.hint=userDataModel?.email
        binding.edName.hint=userDataModel?.name
    }

    private fun check_validation(): Boolean {
        var xx=true;
        xx=xx&&(binding.edEmail.toString().trim().length>0)
        if(!xx) {
            binding.edEmail.setError("Check Email")
            binding.edEmail.requestFocus()
            return  xx
        }
        xx=xx&&(binding.edName.toString().trim().length>0)
        if(!xx) {

            binding.edName.setError("Enter Name")
            binding.edName.requestFocus()
            return  xx
        }
        xx=xx&&(binding.edHomeAdd.toString().trim().length>0)
        if(!xx) {

            binding.edHomeAdd.setError("Enter Proper Address")
            binding.edHomeAdd.requestFocus()
            return  xx
        }
        return xx

    }
}