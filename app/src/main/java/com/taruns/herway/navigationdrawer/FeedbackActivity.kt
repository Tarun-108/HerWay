package com.taruns.herway.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.taruns.herway.R
import com.taruns.herway.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityFeedbackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSpinner()
    }

    private fun setSpinner() {
        binding.spinner.onItemSelectedListener=this

        var category=arrayOf<String?>("Pick a Category",
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
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            category)
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = ad

    }

    private fun check_validation(): Boolean {
        var xx=false;
        xx=xx&&(binding.location.toString().trim().length>0)
        if(!xx)binding.location.requestFocus()
        xx=xx&&(binding.date.toString().trim().length==10)
        if(!xx)binding.location.requestFocus()
        xx=xx&&(binding.time.toString().trim().length>0)
        if(!xx)binding.location.requestFocus()
        return xx

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Toast.makeText(applicationContext, "Selected", Toast.LENGTH_LONG).show()
        if(check_validation()){
            //send data to ml
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext, "Please Select a category", Toast.LENGTH_LONG).show()
    }
}