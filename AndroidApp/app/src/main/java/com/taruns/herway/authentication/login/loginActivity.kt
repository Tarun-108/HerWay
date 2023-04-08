package com.taruns.herway.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taruns.herway.R
import com.taruns.herway.authentication.register.CreateAccountActivity
import com.taruns.herway.databinding.ActivityCommunityBinding
import com.taruns.herway.databinding.ActivityLoginBinding

class loginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signUp.setOnClickListener {
            var intent= Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }

        binding.loginGetOtpBtn.setOnClickListener {


        }
    }
}