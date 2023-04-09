package com.taruns.herway.navigationdrawer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityChangePinBinding
import com.taruns.herway.databinding.ActivityFeedbackBinding
import com.taruns.herway.models.UserModel

class ChangePinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePinBinding
     var  userDataModel: UserModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userDataModel= intent.getSerializableExtra("userModel") as UserModel?
        binding.changePinBtn.setOnClickListener {
            if(check_validation()){


                //update it to database

                var reference =Firebase.database.reference
                userDataModel?.pin=binding.newPinEdText.text.toString().trim()
                reference.child("Users").child(userDataModel?.phone!!).setValue(userDataModel)
                Toast.makeText(applicationContext, "Successfully Changed", Toast.LENGTH_LONG).show()
                var intent= Intent(this, MainActivity::class.java)
                intent.putExtra("userModel",userDataModel)
                startActivity(intent)
            }
        }
        binding.changePinBack.setOnClickListener {
            var intent= Intent(this, MainActivity::class.java)
            intent.putExtra("userModel",userDataModel)
            startActivity(intent)
        }
    }
    private fun check_validation(): Boolean {
        var xx=true;
       xx=xx&&validate_curr_pin()
        if(!xx){
            binding.currPinEdText.setError("Check Your Current Pin")
            binding.currPinEdText.requestFocus()
            return xx
        }
        xx=xx&&(binding.newPinEdText.text.toString().trim().length>=4)
        if(!xx){
            binding.newPinEdText.setError("Pin should be of 4 digits")
            binding.newPinEdText.requestFocus()
            return xx
        }
        xx=xx&&(binding.confPinEdText.text.toString().trim().length>=4)
        if(!xx){
            binding.confPinEdText.setError("Pin should be of 4 digits")
            binding.confPinEdText.requestFocus()
            return xx
        }
        xx=xx&&(binding.confPinEdText.text.toString()==binding.newPinEdText.text.toString())
        if(!xx){
            binding.confPinEdText.setError("Pin Doesn't Match")
            binding.confPinEdText.requestFocus()
            return xx
        }
        return xx
    }

    private fun validate_curr_pin(): Boolean {
        return (userDataModel?.pin==binding.currPinEdText.text.toString().trim())
    }
}