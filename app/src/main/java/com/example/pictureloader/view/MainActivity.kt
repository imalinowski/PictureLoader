package com.example.pictureloader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pictureloader.R
import com.example.pictureloader.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.text.observe(this,{
            textView.text = it
        })
        viewModel.image.observe(this,{
            imageView.setImageBitmap(it)
        })
        textView.setOnClickListener {
            viewModel.requestGET()
        }
    }
}