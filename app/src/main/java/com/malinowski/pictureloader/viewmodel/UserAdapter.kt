package com.malinowski.pictureloader.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureloader.R
import com.malinowski.pictureloader.model.User

class UserAdapter(private val flowerList: MutableList<User>, val itemClick : (id:Int)->Unit ):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val textView: TextView = itemView.findViewById(R.id.name)
        fun bind(word: String, listener : () -> Unit){
            textView.text = word
            itemView.setOnClickListener { listener() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(flowerList[position].name) {
            itemClick(flowerList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }
}