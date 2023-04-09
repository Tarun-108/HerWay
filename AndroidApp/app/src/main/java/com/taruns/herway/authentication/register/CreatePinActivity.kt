package com.taruns.herway.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityCreateAccountBinding
import com.taruns.herway.databinding.ActivityCreatePinBinding
import com.taruns.herway.models.UserModel

class CreatePinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userModel: UserModel? = intent.getSerializableExtra("userModel") as UserModel?

        binding.nextButton.setOnClickListener{
            var text1 = binding.editTextPassword.text.toString().trim()
            var text2 = binding.editTextPasswordCnf.text.toString().trim()
            if(text1.isEmpty() ){
                binding.editTextPassword.error = "Enter pin"
            } else if(text2.isEmpty()){
                binding.editTextPasswordCnf.error = "Confirm pin"
            } else if(text1!=text2){
                binding.editTextPasswordCnf.error = "Pin not matched"
            }
            else{
                val intent =  Intent(this@CreatePinActivity, AddEmergencyContactActivity::class.java)
                userModel?.pin = text1
                intent.putExtra("userModel", userModel)
                Toast.makeText(this@CreatePinActivity, "Pin Created", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        }



    }
}