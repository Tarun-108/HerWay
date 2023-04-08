package com.taruns.herway.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.taruns.herway.R
import com.taruns.herway.authentication.login.loginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val btn = findViewById<Button>(R.id.getStarted)
        btn.setOnClickListener {
            var intent=Intent(this,loginActivity::class.java)
            startActivity(intent)
        }
    }
}