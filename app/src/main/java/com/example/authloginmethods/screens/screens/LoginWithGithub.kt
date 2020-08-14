package com.example.authloginmethods.screens.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import com.google.firebase.auth.ActionCodeSettings.newBuilder
import com.google.firebase.auth.OAuthProvider


class LoginWithGithub : Fragment() {

    private lateinit var authenticate :Authenticate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login_with_github, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //-------------------open github page to login--------------------
        authenticate = Authenticate(this)
        val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")
        authenticate.loginWithGithub(provider)
    }
}