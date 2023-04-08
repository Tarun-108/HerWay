package com.taruns.herway.authentication.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityAddEmergencyContactBinding

class AddEmergencyContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEmergencyContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmergencyContactBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_emergency_contact)
    }
}