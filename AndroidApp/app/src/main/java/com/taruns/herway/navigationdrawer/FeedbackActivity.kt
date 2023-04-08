package com.taruns.herway.navigationdrawer

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.taruns.herway.databinding.ActivityFeedbackBinding
import com.taruns.herway.models.feedBack_model

class FeedbackActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityFeedbackBinding
    var category=arrayOf<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSpinner()
    }

    private fun setSpinner() {
        binding.spinner.onItemSelectedListener=this

        var category1=arrayOf<String?>("Pick a Category",
        "Touching/ Groping",
        "Stalking/Commenting",
        "Ogling/ FacialExpressions/Staring",
        "Sexual Invites",
        "Catcalls/ Whistling",
        "Taking Pictures",
        "Chain Snatching/Robbery",
        "Sexual Assault",
        "Poor/No StreetLighting",
        "Indecent exposure")
        category=category1
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            category)
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = ad

    }

    private fun check_validation(): Boolean {
        var xx=true;
        xx=xx&&(binding.location.toString().trim().length>0)
        if(!xx) {
            binding.location.setError("Check Location")
            binding.location.requestFocus()
        }
        xx=xx&&(binding.date.toString().trim().length>0)
        if(!xx) {

            binding.date.setError("Enter Proper Date")
            binding.date.requestFocus()
        }
        xx=xx&&(binding.time.toString().trim().length>0)
        if(!xx) {

            binding.time.setError("Enter Proper Time")
            binding.time.requestFocus()
        }
        return xx

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        binding.submit.setOnClickListener {
            if (category[position] != "Pick a Category" && check_validation()) {
                //send data to ml

                send_data_to_firebase(position)
            }
        }
    }




    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Please Select a category", Toast.LENGTH_LONG).show()
    }
    private fun send_data_to_firebase(position: Int) {
        var reference =
            FirebaseDatabase.getInstance().getReference("feedbacks").push()
        var obj=feedBack_model(binding.location.text.toString().trim(),binding.date.text.toString()
            ,binding.time.text.toString().trim(), category[position])
        reference.setValue(obj)
        Toast.makeText(applicationContext, "Submitted", Toast.LENGTH_LONG).show()

    }
}