package com.rjt.coroutinepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rjt.coroutinepractice.ViewModel.UserViewModel

class ThirdActivity : AppCompatActivity() {

    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        //Initialize the viewModel first
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //We are going to observe out user object
        viewModel.user.observe(this, Observer{

            println("DEBUG: $it")

        })
        //
        viewModel.setUserId("1")

    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.cancelJobs()
    }

}
