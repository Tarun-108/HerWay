package com.taruns.herway.authentication.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityAddEmergencyContactBinding
import com.taruns.herway.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }
}