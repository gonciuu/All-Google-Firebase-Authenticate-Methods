package com.example.authloginmethods.screens.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }


    private var isLogin = true
    private lateinit var authenticate:Authenticate


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authenticate = Authenticate(this)

        //change isLogin variable
        registerOrLoginButton.setOnClickListener {
            loginOrRegister()
        }

        //-------------register or login with email and password--------------
        userLoginButton.setOnClickListener {
            val email = userInputEmail.text.toString()
            val password = userInputPassword.text.toString()
            if(isLogin) authenticate.loginWithEmailAndPassword(email, password) else  authenticate.registerWithEmailAndPassword(email, password)
        }


    }


    //-----change isLogin variable and set text on buttons-----
    private fun loginOrRegister() {
        isLogin = !isLogin
        registerOrLoginButton.text = if(isLogin) "Click here to Register" else  "Click here to Login"
        userLoginButton.text = if(isLogin) "Login" else "Register"
    }

}