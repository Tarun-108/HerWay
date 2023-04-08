package com.taruns.herway.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityFeedbackBinding
import com.taruns.herway.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.UpdateProfile.setOnClickListener {
            if(check_validation()){
               //update to firebase
                Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun check_validation(): Boolean {
        var xx=true;
        xx=xx&&(binding.edEmail.toString().trim().length>0)
        if(!xx) {
            binding.edEmail.setError("Check Email")
            binding.edEmail.requestFocus()
        }
        xx=xx&&(binding.edName.toString().trim().length>0)
        if(!xx) {

            binding.edName.setError("Enter Name")
            binding.edName.requestFocus()
        }
        xx=xx&&(binding.edHomeAdd.toString().trim().length>0)
        if(!xx) {

            binding.edHomeAdd.setError("Enter Proper Address")
            binding.edHomeAdd.requestFocus()
        }
        return xx

    }
}