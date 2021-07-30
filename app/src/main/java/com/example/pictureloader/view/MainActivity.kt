package com.example.pictureloader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pictureloader.R
import com.example.pictureloader.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.activity.viewModels
import com.example.pictureloader.viewmodel.UserAdapter

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = UserAdapter(viewModel.users.value!!)
        recycler_view.adapter = adapter
        viewModel.users.observe(this,{
            adapter.notifyItemInserted(it.size - 1)
        })
    }
}