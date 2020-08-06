package com.example.authloginmethods.auth

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.authloginmethods.R
import com.example.authloginmethods.screens.view_models.UserDetailsViewModel

import com.google.firebase.auth.FirebaseAuth

class Authenticate(private val fragment: Fragment) {

    private val auth = FirebaseAuth.getInstance()
    private var userDetailsViewModel: UserDetailsViewModel = ViewModelProvider(fragment.requireActivity()).get(UserDetailsViewModel::class.java)



    //--------------------------------Sign in Anon--------------------------------------------
    fun signInAnomyus() {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result!!.user
                val listOfInfo = arrayListOf<String>(user.uid, if(!user.displayName.isNullOrEmpty()) user.displayName!! else "No display name",
                    if(!user.displayName.isNullOrEmpty()) user.phoneNumber!! else "No phone number", if(!user.email.isNullOrEmpty()) user.email!! else "No email")
                userDetailsViewModel.setInfo(listOfInfo)//set info about user account
                try {
                    fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)//navigate to fragment
                }catch (ex:Exception){}
                Log.d("TAG",user.uid)
            } else Toast.makeText(fragment.context, "Error when creating anon account", Toast.LENGTH_SHORT).show()
        }
    }
    //=========================================================================================


    //----log out-------
    fun logOut(){
        auth.signOut()
        fragment.findNavController().navigate(R.id.action_userDetailsFragment_to_loginMethodsFragment)
    }


    //------------get current user and navigate to userDetails if the user exist---------------
    fun getCurrentUser(){
        val user = auth.currentUser
        if(user!=null) {
            val listOfInfo = arrayListOf<String>(
                user.uid,
                if (!user.displayName.isNullOrEmpty()) user.displayName!! else "No display name",
                if (!user.displayName.isNullOrEmpty()) user.phoneNumber!! else "No phone number",
                if (!user.email.isNullOrEmpty()) user.email!! else "No email"
            )
            userDetailsViewModel.setInfo(listOfInfo)//set info about user account
            fragment.findNavController()
                .navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)
        }
    }
    //===========================================================================================


    //--------------login with email and password--------------

    fun loginWithEmailAndPassword(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful) {
                val user = task.result!!.user
                val listOfInfo = arrayListOf<String>(user.uid, if(!user.displayName.isNullOrEmpty()) user.displayName!! else "No display name",
                    if(!user.displayName.isNullOrEmpty()) user.phoneNumber!! else "No phone number", if(!user.email.isNullOrEmpty()) user.email!! else "No email")
                userDetailsViewModel.setInfo(listOfInfo)//set info about user account
                try {
                    fragment.findNavController().navigate(R.id.action_loginFragment_to_userDetailsFragment)//navigate to fragment
                }catch (ex:java.lang.Exception){}
                Log.d("TAG",user.uid)
            } else Toast.makeText(fragment.context, "Error when creating account", Toast.LENGTH_SHORT).show()
        }
    }

    //--------------register with email and password--------------
    fun registerWithEmailAndPassword(email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful) {
                val user = task.result!!.user
                val listOfInfo = arrayListOf<String>(user.uid, if(!user.displayName.isNullOrEmpty()) user.displayName!! else "No display name",
                    if(!user.displayName.isNullOrEmpty()) user.phoneNumber!! else "No phone number", if(!user.email.isNullOrEmpty()) user.email!! else "No email")
                userDetailsViewModel.setInfo(listOfInfo)//set info about user account
                try {
                    fragment.findNavController().navigate(R.id.action_loginFragment_to_userDetailsFragment)//navigate to fragment
                }catch (ex:java.lang.Exception){}
                Log.d("TAG",user.uid)
            } else Toast.makeText(fragment.context, "Error when creating account", Toast.LENGTH_SHORT).show()
        }
    }



}