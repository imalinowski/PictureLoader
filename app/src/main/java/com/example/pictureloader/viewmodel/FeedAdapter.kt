package com.example.pictureloader.viewmodel

import android.opengl.Visibility
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureloader.R
import com.example.pictureloader.model.NetHandler
import com.example.pictureloader.model.Photo

class FeedAdapter(private val flowerList: MutableList<Photo>):
    RecyclerView.Adapter<FeedAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val textView: TextView = itemView.findViewById(R.id.text)
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(word: String,url:String){
            textView.text = word
            val netHandler = NetHandler.getInstance()
            image.visibility = View.INVISIBLE
            netHandler.requestGET(url) {
                Handler(Looper.getMainLooper()).post { // think twice
                    image.visibility = View.VISIBLE
                    image.setImageBitmap(netHandler.getImage(it))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(flowerList[position].title,flowerList[position].url)
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

}