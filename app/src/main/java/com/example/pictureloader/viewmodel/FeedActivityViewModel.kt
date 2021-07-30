package com.example.pictureloader.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureloader.model.NetHandler
import com.example.pictureloader.model.Photo
import org.json.JSONArray
import org.json.JSONTokener

class FeedActivityViewModel : ViewModel() {
    private val netHandler: NetHandler = NetHandler.getInstance()
    val photos = MutableLiveData<MutableList<Photo>>().apply { value = mutableListOf() }

    fun init(userId: Int) {
        netHandler.requestGET("https://jsonplaceholder.typicode.com/user/$userId/albums") { _in ->
            run {
                val albums = JSONTokener(netHandler.getString(_in)).nextValue() as JSONArray
                for (i in 0 until albums.length())
                    loadAlbum(albums.getJSONObject(i).getInt("id"))
            }
        }
    }

    private fun loadAlbum(albumId:Int){
        netHandler.requestGET("https://jsonplaceholder.typicode.com/albums/$albumId/photos") { _in ->
            run {
                val photos = JSONTokener(netHandler.getString(_in)).nextValue() as JSONArray
                for (i in 0 until photos.length())
                    this.photos.value?.add(Photo(
                        photos.getJSONObject(i).getString("title").toString(),
                        photos.getJSONObject(i).getString("url").toString()
                    ))
                this.photos.postValue(this.photos.value)
            }
        }
    }
}