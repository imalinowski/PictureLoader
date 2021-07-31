package com.example.pictureloader.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureloader.R
import com.example.pictureloader.model.DBHelper
import com.example.pictureloader.model.NetHandler
import com.example.pictureloader.viewmodel.FeedActivityViewModel
import com.example.pictureloader.viewmodel.FeedAdapter
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private val viewModel: FeedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        viewModel.init(intent.getIntExtra("id",1))
        val adapter: FeedAdapter =
            FeedAdapter(viewModel.photos.value!!, NetHandler.getInstance(), DBHelper(this))
        recycler_view_feed.adapter = adapter
        viewModel.photos.observe(this,{
            adapter.notifyItemInserted(it.size - 1)
        })
    }
}