package com.taruns.herway.navigationdrawer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.adapter.emergency_cont_adapter
import com.taruns.herway.authentication.login.loginActivity
import com.taruns.herway.databinding.ActivityEmergencyBinding
import com.taruns.herway.databinding.ActivityProfileBinding
import com.taruns.herway.models.contact_list_model

class EmergencyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmergencyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmergencyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.addContBtn.setOnClickListener {
         //   var intent=Intent(this,)
           // startActivity(intent)
        }
        binding.changePinBack.setOnClickListener {
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.contactRecv.layoutManager = layoutManager


        var cont_list = mutableListOf<contact_list_model>()
        var contactListModel=contact_list_model("chchh","ihowch")
      //  for(i in 0..34)
        //cont_list.add(i,contactListModel)


        var adapter = emergency_cont_adapter()
        adapter.contact_list=cont_list
        binding.contactRecv.adapter = adapter

    }
}