package com.example.pictureloader.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureloader.model.NetHandler
import com.example.pictureloader.model.User
import org.json.JSONArray
import org.json.JSONTokener

class MainActivityViewModel : ViewModel() {
    val users = MutableLiveData<MutableList<User>>().apply { value = mutableListOf() }
    private val netHandler:NetHandler = NetHandler.getInstance()

    init{
        netHandler.requestGET("https://jsonplaceholder.typicode.com/users"){
            _in -> run {
                val jsonArray = JSONTokener(netHandler.getString(_in)).nextValue() as JSONArray
                Log.i("RASPBERRY",jsonArray.toString())
                for (i in 0 until jsonArray.length())
                    users.value?.add(User(
                        jsonArray.getJSONObject(i).getString("name"),
                        jsonArray.getJSONObject(i).getInt("id")
                    ))
                users.postValue(users.value)
            }
        }
    }

}