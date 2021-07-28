package com.example.pictureloader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pictureloader.R
import com.example.pictureloader.model.HttpHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.setOnClickListener{
            Thread {
                HttpHandler.requestGET(
                    "https://storage.yandexcloud.net/pioneer.geoscan.aero/pioneer_mini/ESP_32/ESP32.json"
                ) { text -> runOnUiThread { textView.text = text } }
            }.start()
        }
    }
}