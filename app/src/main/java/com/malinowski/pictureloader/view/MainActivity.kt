package com.malinowski.pictureloader.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pictureloader.R
import com.malinowski.pictureloader.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.activity.viewModels
import com.malinowski.pictureloader.viewmodel.UserAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = UserAdapter(viewModel.users.value!!) {
            Log.i("RASPBERRY", it.toString())
            startActivity(Intent(this,FeedActivity::class.java).apply {
                putExtra("id",it)
            })
        }
        recycler_view.adapter = adapter
        viewModel.users.observe(this,{
            adapter.notifyItemInserted(it.size - 1)
        })
    }
}