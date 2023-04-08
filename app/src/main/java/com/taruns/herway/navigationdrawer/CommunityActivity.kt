package com.taruns.herway.navigationdrawer

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.taruns.herway.R
import com.taruns.herway.adapter.community_post_adapter
import com.taruns.herway.databinding.ActivityCommunityBinding
import com.taruns.herway.models.post_list_model


class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setRecyclerView()
        binding.openBtmNav.setOnClickListener {
            ShowBottomSheetDialog()
        }
    }

    private fun ShowBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(com.taruns.herway.R.layout.bottom_sheet_dialog_post)



        bottomSheetDialog.show()
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


