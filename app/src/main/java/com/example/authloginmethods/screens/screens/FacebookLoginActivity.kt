package com.example.authloginmethods.screens.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_facebook_login.*
import java.lang.Exception

class FacebookLoginActivity : AppCompatActivity() {

    private lateinit var callbackManager:CallbackManager
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)
        auth = FirebaseAuth.getInstance()
        callbackManager= CallbackManager.Factory.create()

        fbLoginButton.setReadPermissions("email", "public_profile")
        fbLoginButton.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                Log.d("TAG", "facebook:onSuccess:$result")
                loginWithFb(result!!.accessToken)
            }

            override fun onCancel() {
                Log.d("TAG", "facebook:onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.d("TAG", "facebook:onError", error)
            }

        })



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)

    }


    private fun loginWithFb(token :AccessToken){
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task->
            if(task.isSuccessful){
                val user = task.result!!.user
                try{fbNameText.text = user.displayName}catch (ex:Exception){fbNameText.text = "No name"}
                try{fbUidText.text = user.uid}catch (ex:Exception){fbUidText.text = "No uid"}
            }else{
                Log.w("TAG", "signInWithCredential:failure", task.exception)
                Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }



    }
}