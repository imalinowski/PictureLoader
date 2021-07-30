package com.example.pictureloader.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureloader.model.NetHandler

class MainActivityViewModel : ViewModel() {
    val text = MutableLiveData<String>()
    val image = MutableLiveData<Bitmap>()
    private val netHandler:NetHandler = NetHandler.getInstance()

    fun requestGET(){
        val url1:String = "https://via.placeholder.com/600/92c952"
        val url2:String = "https://jsonplaceholder.typicode.com/users"
        netHandler.requestGET(url1) { _in -> image.postValue(netHandler.getImage(_in)) }
        netHandler.requestGET(url2) { _in -> text.postValue(netHandler.getString(_in)) }
    }

}