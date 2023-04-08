package com.taruns.herway.navigationdrawer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityChangePinBinding
import com.taruns.herway.databinding.ActivityFeedbackBinding

class ChangePinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePinBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.changePinBtn.setOnClickListener {
            if(check_validation()){
                Toast.makeText(applicationContext, "Successfully Changed", Toast.LENGTH_LONG).show()

                //update it to database
            }
        }
    }
    private fun check_validation(): Boolean {
        var xx=true;
       xx=xx&&validate_curr_pin()
        if(!xx){
            binding.currPinEdText.setError("Check Your Current Pin")
            binding.currPinEdText.requestFocus()
        }
        xx=xx&&(binding.newPinEdText.toString().trim().length>=4)
        if(!xx){
            binding.newPinEdText.setError("Pin should be of 4 digits")
            binding.newPinEdText.requestFocus()
        }
        xx=xx&&(binding.confPinEdText.toString().trim().length>=4)
        if(!xx){
            binding.confPinEdText.setError("Pin should be of 4 digits")
            binding.confPinEdText.requestFocus()
        }
        xx=xx&&(binding.confPinEdText.toString()==binding.newPinEdText.toString())
        if(!xx){
            binding.confPinEdText.setError("Pin Doesn't Match")
            binding.confPinEdText.requestFocus()
        }
        return xx
    }

    private fun validate_curr_pin(): Boolean {
//check using firebase
        return true;
    }
}