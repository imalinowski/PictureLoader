package com.example.pictureloader.viewmodel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureloader.R
import com.example.pictureloader.model.NetHandler
import com.example.pictureloader.model.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedAdapter(private val flowerList: MutableList<Photo>):
    RecyclerView.Adapter<FeedAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val textView: TextView = itemView.findViewById(R.id.text)
        private val image: ImageView = itemView.findViewById(R.id.image)
        fun bind(photo: Photo){
            textView.text = photo.title
            val netHandler = NetHandler.getInstance()
            if(photo.image != null) {
                Log.i("RASPBERRY", "${photo.url} image loaded from cache")
                image.setImageBitmap(photo.image)
            }
            else{ // load image
                image.visibility = View.INVISIBLE
                CoroutineScope(Dispatchers.IO).launch {
                    netHandler.requestGET(photo.url) {
                        Log.i("RASPBERRY", "${photo.url} image loaded")
                        photo.image = netHandler.getImage(it)
                        CoroutineScope(Dispatchers.Main).launch{
                            image.visibility = View.VISIBLE
                            image.setImageBitmap(photo.image)
                        }
                    }
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
        holder.bind(flowerList[position])
    }

    override fun getItemCount(): Int {
        return flowerList.size
    }

}