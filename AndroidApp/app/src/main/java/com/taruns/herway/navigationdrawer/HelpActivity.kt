package com.taruns.herway.navigationdrawer

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityChangePinBinding
import com.taruns.herway.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.contactUs.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, "email")
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "message")
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
        }


    }
}