package com.example.authloginmethods.auth

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.authloginmethods.R
import com.example.authloginmethods.screens.view_models.UserDetailsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.fragment_phone_number.*
import java.util.concurrent.TimeUnit

class Authenticate(private val fragment: Fragment) {

    private val auth = FirebaseAuth.getInstance()
    private var userDetailsViewModel: UserDetailsViewModel = ViewModelProvider(fragment.requireActivity()).get(UserDetailsViewModel::class.java)



    //--------------------------------Sign in Anon--------------------------------------------
    fun signInAnomyus() {
        auth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userDetailsViewModel.setInfo(setListOfUserInfo(task.result!!.user!!))
                try {
                    fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)//navigate to fragment
                }catch (ex:Exception){}
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
            userDetailsViewModel.setInfo(setListOfUserInfo(user))               //get info about user account
            fragment.findNavController()
                .navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)
        }
    }
    //===========================================================================================

    //-------------------------------Login with phone number-----------------------------
    fun loginWithPhoneNumber(phoneNumber:String){

        var storedVerificationId:String? = null

        auth.setLanguageCode("pl")

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("TAG", "onVerificationCompleted:$credential")             //verifi completed
                fragment.error_message.text = ""
                signInWithPhoneAuthCredential(credential)
            }

            @SuppressLint("SetTextI18n")
            override fun onVerificationFailed(e: FirebaseException) {
                Log.w("TAG", "onVerificationFailed", e)

                when (e) {                                                              //verifi failed
                    is FirebaseAuthInvalidCredentialsException -> fragment.error_message.text = "Bad phone number. Try again with another number. ${e.message}"
                    is FirebaseTooManyRequestsException -> fragment.error_message.text = "To many requests try again later. ${e.message}"
                    else -> fragment.error_message.text = "Error ${e.message}"
                }
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId                                   //send code to user to confirmation
                fragment.userInputVerificationId.visibility = View.VISIBLE
                fragment.userVerifyCodeButton.visibility = View.VISIBLE
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify                                      //verifi phone number
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            fragment.requireActivity(), // Activity (for callback binding)
            callbacks)


        fragment.userVerifyCodeButton.setOnClickListener {
            try{
                val credential = PhoneAuthProvider.getCredential(storedVerificationId!!,fragment.userInputVerificationId.text.toString())
                signInWithPhoneAuthCredential(credential)       //check verifi code
            }catch (ex:Exception){}

        }


    }
    //=======================================================================================


    //---------------------------------------------SIGN IN WITH CREADENCIAL (PHONE NUMBER VERIFICATION---------------------------------------------
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userDetailsViewModel.setInfo(setListOfUserInfo(task.result!!.user!!))
                    fragment.findNavController().navigate(R.id.action_phoneNumberFragment_to_userDetailsFragment)//navigate to fragment
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        fragment.error_message.text = "Bad verification code ${task.exception?.message}"
                    }
                }
            }
    }
    //=================================================================================================================================================


    //--------------login with email and password--------------

    fun loginWithEmailAndPassword(email:String,password:String){
        Toast.makeText(fragment.context,"Loading...", Toast.LENGTH_SHORT).show()
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful) {
                userDetailsViewModel.setInfo(setListOfUserInfo(task.result!!.user!!))
                try {
                    fragment.findNavController().navigate(R.id.action_loginFragment_to_userDetailsFragment)//navigate to fragment
                }catch (ex:java.lang.Exception){}
            } else Toast.makeText(fragment.context, "Error when creating account", Toast.LENGTH_SHORT).show()
        }
    }

    //--------------register with email and password--------------
    fun registerWithEmailAndPassword(email:String,password:String){
        Toast.makeText(fragment.context,"Loading...", Toast.LENGTH_SHORT).show()
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {task->
            if (task.isSuccessful) {
                userDetailsViewModel.setInfo(setListOfUserInfo(task.result!!.user!!))//set info about user account
                try {
                    fragment.findNavController().navigate(R.id.action_loginFragment_to_userDetailsFragment)//navigate to fragment
                }catch (ex:java.lang.Exception){}
            } else Toast.makeText(fragment.context, "Error when creating account", Toast.LENGTH_SHORT).show()
        }
    }
    //=============================================================

    //-----------------------set user data in list and return this-----------------------
    private fun setListOfUserInfo(user:FirebaseUser) : ArrayList<String> {
        return arrayListOf(user.uid, try{ user.displayName!!} catch(ex:Exception){"No display name"},
            try{ user.phoneNumber!!} catch(ex:Exception){"No phone number"}, try{ user.email!!} catch(ex:Exception){"No email"})
    }
    //===================================================================================


    //-------------------------------------------------login with google account--------------------------------------------------
    fun firebaseAuthWithGoogle(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                userDetailsViewModel.setInfo(setListOfUserInfo(task.result?.user!!))//set info about user account
                try {
                    fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment) //complete
                }catch (ex:java.lang.Exception){}
            }else{
                Snackbar.make(fragment.requireView(), "Authentication Failed. ${task.exception}", Snackbar.LENGTH_SHORT).show() //failure
            }
        }

    }
    //=============================================================================================================================

    //---------------------------login with github account-----------------------
    fun loginWithGithub(provider: OAuthProvider.Builder){
        auth.startActivityForSignInWithProvider(fragment.requireActivity(),provider.build())
            .addOnSuccessListener { authResult->
                val user = authResult.additionalUserInfo
                val firebaseUser = auth.currentUser
                userDetailsViewModel.setInfo(arrayListOf<String>(try{firebaseUser!!.uid}catch (ex:Exception){"empty uid"},
                    try{user!!.isNewUser.toString()}catch (ex:Exception){""},
                    try{user!!.username!!}catch (ex:Exception){"empty username"},
                    try{user!!.providerId!!}catch (ex:Exception){"empty provider id"}))
                fragment.findNavController().navigate(R.id.action_loginWithGithub_to_userDetailsFragment)
            }.addOnFailureListener {
                Log.d("TAG","${it.message}")
            }
    }
    //=============================================================================


    //----------------------login with twitter account-------------------------
    fun loginWithTwitter(provider: OAuthProvider.Builder){
        auth.startActivityForSignInWithProvider(fragment.requireActivity(),provider.build())
            .addOnSuccessListener { authResult ->
                val user = authResult.additionalUserInfo
                val firebaseUser = auth.currentUser
                userDetailsViewModel.setInfo(
                    arrayListOf<String>(
                        try { user!!.username!! } catch (ex: Exception) { "no username" },
                        try { user!!.isNewUser.toString() } catch (ex: Exception) { "" },
                        try { firebaseUser!!.uid } catch (ex: Exception) { "empty uid" },
                        try { user!!.providerId!! } catch (ex: Exception) { "empty provider id" }
                    )
                )
                fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)
            }
            .addOnFailureListener {
                Log.d("TAG","${it.message}")
            }
    }

    //-------------------------------login with microsoft-------------------------------------
    fun loginWithMicrosoft(provider: OAuthProvider.Builder){

        auth.startActivityForSignInWithProvider(fragment.requireActivity(),provider.build())
            .addOnSuccessListener { authResult->
                val user = authResult.additionalUserInfo
                val firebaseUser = auth.currentUser


                userDetailsViewModel.setInfo(arrayListOf<String>(try{user!!.username!!}catch (ex:Exception){""},
                    try{user!!.isNewUser.toString()}catch (ex:Exception){""},
                    try{firebaseUser!!.uid}catch (ex:Exception){""},
                    try{user!!.providerId!!}catch (ex:Exception){""}))

                fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)
            }.addOnFailureListener {
                Log.d("TAG","${it.message}")
            }
    }

    //============================================================================


    //---------------------------------------------login with yahoo account--------------------------------------------------
    fun loginWithYahoo(provider: OAuthProvider.Builder){
        auth.startActivityForSignInWithProvider(fragment.requireActivity(),provider.build())
            .addOnSuccessListener { result->
                Log.d("TAG","${result.additionalUserInfo!!.username}")
                val user = result.additionalUserInfo
                val firebaseUser = result.user
                userDetailsViewModel.setInfo(arrayListOf<String>(try{firebaseUser!!.uid}catch (ex:Exception){"empty uid"},
                    try{user!!.isNewUser.toString()}catch (ex:Exception){""},
                    try{user!!.username!!}catch (ex:Exception){"empty username"},
                    try{user!!.providerId!!}catch (ex:Exception){"empty provider id"}))
                fragment.findNavController().navigate(R.id.action_loginMethodsFragment_to_userDetailsFragment)
            }.addOnFailureListener {
                Log.d("TAG","${it.message}")
            }
    }
    //=========================================================================================================================


    //=========================================================================================

    //---------------------------login with apple id-------------------------------
    fun loginWithApple(provider: OAuthProvider.Builder){

        auth.startActivityForSignInWithProvider(fragment.requireActivity(),provider.build())
            .addOnSuccessListener { authResult ->
                Log.d("TAG","$authResult")
                val user = authResult.additionalUserInfo
                val firebaseUser = auth.currentUser
                userDetailsViewModel.setInfo(arrayListOf<String>(try{user!!.username!!}catch (ex:Exception){""},
                    try{user!!.isNewUser.toString()}catch (ex:Exception){""},
                    try{firebaseUser!!.uid}catch (ex:Exception){""},
                    try{user!!.providerId!!}catch (ex:Exception){""}))
                fragment.findNavController().navigate(R.id.action_loginWithGithub_to_userDetailsFragment)
            }.addOnFailureListener {
                Log.d("TAG","${it.message}")
            }

    }
    //===============================================================================


}