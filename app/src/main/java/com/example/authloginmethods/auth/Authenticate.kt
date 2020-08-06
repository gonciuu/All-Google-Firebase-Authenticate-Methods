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
                fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)//navigate to fragment
                Log.d("TAG",user.uid)
            } else Toast.makeText(fragment.context, "Error when creating anon account", Toast.LENGTH_SHORT).show()
        }
    }
    //=========================================================================================


    fun logOut(){
        auth.signOut()
        fragment.findNavController().navigate(R.id.action_userDetailsFragment_to_loginMethodsFragment)
    }


}