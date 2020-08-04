package com.example.authloginmethods.auth

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.authloginmethods.screens.MainActivity
import com.example.authloginmethods.screens.ShowUserAuthDetails
import com.google.firebase.auth.FirebaseAuth

class Authenticate {

    private val auth = FirebaseAuth.getInstance()

    fun signInAnomyus(activity:MainActivity){
        auth.signInAnonymously().addOnCompleteListener {task->
            if(task.isSuccessful){
                activity.startActivity(Intent(activity.applicationContext,ShowUserAuthDetails::class.java))
                Log.d("TAG",task.result!!.user.uid)
            }
            else Toast.makeText(activity.applicationContext,"Error when creating anon account",Toast.LENGTH_SHORT).show()
        }
    }


}