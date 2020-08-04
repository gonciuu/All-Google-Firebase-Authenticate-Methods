package com.example.authloginmethods.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authloginmethods.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        anomyusLogin.setOnClickListener {

        }
    }

}