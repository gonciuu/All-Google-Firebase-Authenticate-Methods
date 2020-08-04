package com.example.authloginmethods.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.authloginmethods.R
import com.example.authloginmethods.screens.view_models.UserInfoViewModel

class ShowUserAuthDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user_auth_details)
        val userInfoViewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)

        userInfoViewModel.getInfo().observe(this, Observer {
            info->

        })
    }
}