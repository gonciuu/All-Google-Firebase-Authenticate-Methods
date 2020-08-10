package com.example.authloginmethods.screens.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import kotlinx.android.synthetic.main.login_methods_fragment.*

class LoginMethodsFragment : Fragment() {
    private lateinit var authenticate: Authenticate
    companion object {
        fun newInstance() = LoginMethodsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_methods_fragment, container, false)
    }


    //------click on login methods buttons------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authenticate = Authenticate(this)
        authenticate.getCurrentUser()//check if user is already exist 

        //--------anomyus login---------
        anomyusLogin.setOnClickListener {
            makeLoadingText()
            authenticate.signInAnomyus()
        }


        //-------------email and password login--------------
        loginAndPasswordLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginMethodsFragment_to_loginFragment)

            //phone number login
            phoneLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginMethodsFragment_to_phoneNumberFragment)

            }
        }
    }

    private fun makeLoadingText() =  Toast.makeText(context,"Loading...", Toast.LENGTH_SHORT).show()

}