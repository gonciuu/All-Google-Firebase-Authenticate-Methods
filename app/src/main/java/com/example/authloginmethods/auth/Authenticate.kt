package com.example.authloginmethods.auth

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.authloginmethods.R
import com.example.authloginmethods.screens.view_models.UserDetailsViewModel

import com.example.authloginmethods.screens.view_models.UserInfoViewModel
import com.google.firebase.auth.FirebaseAuth

class Authenticate {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    fun signInAnomyus(fragment: Fragment) {
        userDetailsViewModel = ViewModelProvider(fragment.requireActivity()).get(UserDetailsViewModel::class.java)
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result!!.user
                val listOfInfo = arrayListOf<String>(user.uid, if(!user.displayName.isNullOrEmpty()) user.displayName!! else "No display name",
                    if(!user.displayName.isNullOrEmpty()) user.phoneNumber!! else "No phone number", if(!user.email.isNullOrEmpty()) user.email!! else "No email")
                userDetailsViewModel.setInfo(listOfInfo)
                fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)
                Log.d("TAG",user.uid)
            } else Toast.makeText(fragment.context, "Error when creating anon account", Toast.LENGTH_SHORT).show()
        }
    }


}