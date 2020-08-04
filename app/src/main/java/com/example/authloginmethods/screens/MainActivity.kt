package com.example.authloginmethods.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import com.example.authloginmethods.screens.view_models.UserInfoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val authenticate = Authenticate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        anomyusLogin.setOnClickListener {
            Toast.makeText(applicationContext,"Loading...",Toast.LENGTH_SHORT).show()
            authenticate.signInAnomyus(this)
        }
    }

}