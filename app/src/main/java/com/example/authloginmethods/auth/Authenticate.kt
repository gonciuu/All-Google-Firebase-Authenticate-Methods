package com.example.authloginmethods.auth

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.authloginmethods.R
import com.example.authloginmethods.screens.view_models.UserDetailsViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

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

    //-------------------------------Login with phone number-----------------------------
    fun loginWithPhoneNumber(phoneNumber:String){

        var storedVerificationId = ""
        var resendToken: PhoneAuthProvider.ForceResendingToken


        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token



                // ...
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            fragment.requireActivity(), // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks

        auth.setLanguageCode("pl")

        Log.d("TAG",phoneNumber)
    }
    //===================================================================================

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
    }

}