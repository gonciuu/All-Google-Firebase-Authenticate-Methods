package com.example.authloginmethods.auth

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.authloginmethods.screens.MainActivity
import com.example.authloginmethods.screens.ShowUserAuthDetails
import com.example.authloginmethods.screens.view_models.UserInfoViewModel
import com.google.firebase.auth.FirebaseAuth

class Authenticate {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var userInfoViewModel: UserInfoViewModel

    fun signInAnomyus(activity:MainActivity){
        userInfoViewModel = ViewModelProvider(activity).get(UserInfoViewModel::class.java)
        auth.signInAnonymously().addOnCompleteListener {task->
            if(task.isSuccessful){
                activity.startActivity(Intent(activity.applicationContext,ShowUserAuthDetails::class.java))
                val user = task.result!!.user
                userInfoViewModel.setInfo(user.uid,user.email,user.phoneNumber,user.displayName)
            }
            else Toast.makeText(activity.applicationContext,"Error when creating anon account",Toast.LENGTH_SHORT).show()
        }
    }


}