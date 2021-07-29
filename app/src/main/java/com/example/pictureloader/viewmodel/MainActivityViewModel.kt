package com.example.pictureloader.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureloader.model.NetHandler

class MainActivityViewModel : ViewModel() {
    val text = MutableLiveData<String>()
    private val netHandler:NetHandler = NetHandler.getInstance()

    fun requestGET(){
        netHandler.requestGET("https://jsonplaceholder.typicode.com/users") {
                _in -> text.postValue(netHandler.getString(_in))
        }
    }

}