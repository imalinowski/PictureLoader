package com.malinowski.pictureloader.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureloader.R
import com.malinowski.pictureloader.model.DBHelper
import com.malinowski.pictureloader.model.NetHandler
import com.malinowski.pictureloader.viewmodel.FeedActivityViewModel
import com.malinowski.pictureloader.viewmodel.FeedAdapter
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private val viewModel: FeedActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        viewModel.init(intent.getIntExtra("id",1))

        val adapter =
            FeedAdapter(viewModel.photos.value!!, NetHandler.getInstance(), DBHelper(this))
        recycler_view_feed.adapter = adapter

        viewModel.photos.observe(this,{
            adapter.notifyItemInserted(it.size - 1)
        })
    }

    override fun onStop() {
        super.onStop()
        (recycler_view_feed.adapter as FeedAdapter).clear()
    }

}