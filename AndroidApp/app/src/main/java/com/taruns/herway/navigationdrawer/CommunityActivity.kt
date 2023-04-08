package com.taruns.herway.navigationdrawer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.taruns.herway.MainActivity
import com.taruns.herway.R
import com.taruns.herway.adapter.community_post_adapter
import com.taruns.herway.databinding.ActivityCommunityBinding
import com.taruns.herway.models.post_list_model
import java.text.SimpleDateFormat
import java.util.*


class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    var adapter = community_post_adapter()
    var post_list = mutableListOf<post_list_model>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.openBtmNav.setOnClickListener {
            ShowBottomSheetDialog()
        }
        binding.changePinBack.setOnClickListener {
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setRecyclerView()
    }

    private fun ShowBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(com.taruns.herway.R.layout.bottom_sheet_dialog_post)

        var post=bottomSheetDialog.findViewById<Button>(R.id.com_post)
        var cancel=bottomSheetDialog.findViewById<Button>(R.id.com_cancel)
        var title=bottomSheetDialog.findViewById<EditText>(R.id.com_title)
        var desc=bottomSheetDialog.findViewById<EditText>(R.id.com_desc)
        cancel?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        post?.setOnClickListener {

            //send datas to fire base
            if(title?.text.toString().trim().length==0 ){
                title?.requestFocus()
                title?.setError("Give the Title")

            }
            else if(desc?.text.toString().trim().length==0){
                desc?.requestFocus()
                desc?.setError("Give the Description")
            }
            else {
                var reference =
                    FirebaseDatabase.getInstance().getReference("community_posts").push()
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val date = sdf.format(Date())
                var new_post = post_list_model(
                    name="name",date= date,
                   title= title?.text.toString().trim(), description = desc?.text.toString().trim()
                )
                reference.setValue(new_post);
                Toast.makeText(getApplicationContext(), "Successfully Posted", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }


        }


        bottomSheetDialog.show()
    }



    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecv.layoutManager = layoutManager



/*
        var PostListModel= post_list_model("chchh","ihowch","uu","iui")
        for(i in 0..34)
            post_list.add(i,PostListModel)

 */


        val reference = FirebaseDatabase.getInstance().getReference("community_posts")


        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("check","ondata")
                post_list.clear()

                for (s in snapshot.children) {
                    Log.i("checkk",s.value.toString())
                    var obj=s.getValue(post_list_model::class.java)
                    Log.i("obj",obj.toString())
                    post_list.add(obj!!)
                }
                adapter.post_list=post_list
                binding.communityRecv.adapter = adapter
                adapter.notifyDataSetChanged()

               // val x = binding.communityRecv.adapter
               // binding.communityRecv.smoothScrollToPosition(x!!.itemCount)

            }

            override fun onCancelled(error: DatabaseError) {
            Log.i("check","cancelled")
            }

        })




    }
}


