package com.example.authloginmethods.screens.screens

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import com.example.authloginmethods.screens.view_models.LoginViewModel
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

        registerOrLoginButton.setOnClickListener {
            loginOrRegister()
        }



    }


    private fun loginOrRegister() {
        isLogin = !isLogin
        registerOrLoginButton.text = if(isLogin) "Click here to Register" else  "Click here to Login"
        userLoginButton.text = if(isLogin) "Login" else "Register"
    }

}