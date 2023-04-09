package com.taruns.herway.navigationdrawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.adapter.emergency_cont_adapter
import com.taruns.herway.authentication.login.loginActivity
import com.taruns.herway.authentication.register.AddEmergencyContactActivity
import com.taruns.herway.databinding.ActivityEmergencyBinding
import com.taruns.herway.databinding.ActivityProfileBinding
import com.taruns.herway.models.ContactModel
import com.taruns.herway.models.UserModel
import com.taruns.herway.models.contact_list_model

class EmergencyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyBinding
     var  userDataModel: UserModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityEmergencyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userDataModel= intent.getSerializableExtra("userModel") as UserModel?
        Log.i("usermodel",userDataModel.toString())
        binding.addContBtn.setOnClickListener {
            var intent=Intent(this, AddEmergencyContactActivity::class.java)
            intent.putExtra("userModel",userDataModel)
            startActivity(intent)
        }
        binding.changePinBack.setOnClickListener {
            var intent=Intent(this, MainActivity::class.java)
            intent.putExtra("userModel",userDataModel)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }


    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.contactRecv.layoutManager = layoutManager




      //  for(i in 0..34)
        //cont_list.add(i,contactListModel)

        var reference =
            userDataModel?.phone?.let {
                FirebaseDatabase.getInstance().getReference("Users").child(
                    it
                )
            }
        reference?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("checkk",snapshot.toString())
                userDataModel=snapshot.getValue(UserModel::class.java)!!
                var cont_list=userDataModel?.eContacts as MutableList<ContactModel>?
                Log.i("cont_list",cont_list.toString())
                var adapter = emergency_cont_adapter()
                if(cont_list!=null) {
                    adapter.contact_list = cont_list
                    binding.contactRecv.adapter = adapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("check","cancelled")
            }

        })



    }
}