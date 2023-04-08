package com.taruns.herway.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.taruns.herway.R
import com.taruns.herway.models.contact_list_model

class emergency_cont_adapter: RecyclerView.Adapter<emergency_cont_adapter.ViewHolder>() {

    var contact_list = mutableListOf<contact_list_model>()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name_cont_list)
        val phone = itemView.findViewById<TextView>(R.id.phone_cont_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_row_emergency_contacts, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
    return contact_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.name.text=contact_list[position].name
    holder.phone.text=contact_list[position].phone
    }
}