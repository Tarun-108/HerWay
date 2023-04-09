package com.taruns.herway.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.authentication.login.loginActivity
import com.taruns.herway.authentication.register.CreateAccountActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val btn = findViewById<Button>(R.id.getStarted)
        var fb=Firebase.auth.currentUser
        if(fb!=null){
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btn.setOnClickListener {
            var intent=Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
}