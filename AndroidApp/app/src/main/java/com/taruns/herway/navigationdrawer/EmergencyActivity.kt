package com.taruns.herway.navigationdrawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
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

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.contactRecv.layoutManager = layoutManager



        var cont_list=userDataModel?.eContacts as MutableList<ContactModel>?
      //  for(i in 0..34)
        //cont_list.add(i,contactListModel)


        var adapter = emergency_cont_adapter()
        adapter.contact_list=cont_list!!
        binding.contactRecv.adapter = adapter

    }
}