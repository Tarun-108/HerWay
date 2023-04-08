package com.taruns.herway.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.taruns.herway.R
import com.taruns.herway.adapter.community_post_adapter
import com.taruns.herway.adapter.emergency_cont_adapter
import com.taruns.herway.databinding.ActivityCommunityBinding
import com.taruns.herway.databinding.ActivityEmergencyBinding
import com.taruns.herway.models.contact_list_model
import com.taruns.herway.models.post_list_model

class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setRecyclerView()
    }
    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.communityRecv.layoutManager = layoutManager


        var post_list = mutableListOf<post_list_model>()
        var PostListModel= post_list_model("chchh","ihowch","uu","iui")
        for(i in 0..34)
            post_list.add(i,PostListModel)


        var adapter = community_post_adapter()
        adapter.post_list=post_list
        binding.communityRecv.adapter = adapter

    }
}