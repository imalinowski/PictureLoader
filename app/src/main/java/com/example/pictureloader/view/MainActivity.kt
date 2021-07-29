package com.example.pictureloader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pictureloader.R
import com.example.pictureloader.model.HttpHandler
import com.example.pictureloader.model.NetHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val network:NetHandler = NetHandler.getInstance()
        textView.setOnClickListener{
            network.requestGET("https://jsonplaceholder.typicode.com/users") {
                    _in -> Log.i("RASPBERRY",network.getString(_in))
            }
        }
    }
}