package com.example.pictureloader.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureloader.R
import com.example.pictureloader.model.User

class UserAdapter(private val flowerList: MutableList<User>):
    RecyclerView.Adapter<UserAdapter.FlowerViewHolder>() {

    class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val flowerTextView: TextView = itemView.findViewById(R.id.flower_text)
        fun bind(word: String){
            flowerTextView.text = word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return FlowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        holder.bind(flowerList[position].name)
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }
}