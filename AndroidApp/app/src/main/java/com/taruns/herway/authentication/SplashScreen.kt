package com.taruns.herway.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.authentication.login.loginActivity
import com.taruns.herway.authentication.register.CreateAccountActivity
import com.taruns.herway.models.UserModel


class SplashScreen : AppCompatActivity() {
    var userDataModel: UserModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val btn = findViewById<Button>(R.id.getStarted)
        var fb=Firebase.auth.currentUser
        var fb1=Firebase.auth.currentUser?.phoneNumber
        if(fb!=null){
            Toast.makeText(this,"Please Wait we are checking Your Credential",Toast.LENGTH_SHORT).show();
            var intent=Intent(this,MainActivity::class.java)
            var reference =
                fb1?.let { FirebaseDatabase.getInstance().getReference("Users").child(it) }
            reference?.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("checkk",snapshot.toString())
                    userDataModel=snapshot.getValue(UserModel::class.java)!!
                    intent.putExtra("userModel", userDataModel)
                    startActivity(intent)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("check","cancelled")
                }

            })

        }

        btn.setOnClickListener {
            var intent=Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getUserdata() {

    }
}