package com.example.pictureloader.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureloader.R
import com.example.pictureloader.viewmodel.FeedActivityViewModel
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private val viewModel: FeedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        id.text = intent.getIntExtra("id",1).toString()
        viewModel.init(intent.getIntExtra("id",1))
    }
}