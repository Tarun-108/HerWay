package com.taruns.herway.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taruns.herway.R
import com.taruns.herway.models.contact_list_model
import com.taruns.herway.models.post_list_model

class community_post_adapter:RecyclerView.Adapter<community_post_adapter.ViewHolder>() {
    var post_list = mutableListOf<post_list_model>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.single_row_Name)
        val date = itemView.findViewById<TextView>(R.id.single_row_date)
        val title = itemView.findViewById<TextView>(R.id.single_row_title)
        val desc = itemView.findViewById<TextView>(R.id.single_row_descp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_community_post, parent, false)
        return community_post_adapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return post_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=post_list[position].name
        holder.date.text=post_list[position].date
        holder.title.text=post_list[position].title
        holder.desc.text=post_list[position].description
    }
}