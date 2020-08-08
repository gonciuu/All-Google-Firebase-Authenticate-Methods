package com.example.authloginmethods.screens.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import kotlinx.android.synthetic.main.fragment_phone_number.*


class PhoneNumberFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authenticate = Authenticate(this)
        userLoginWithPhoneButton.setOnClickListener {
            authenticate.loginWithPhoneNumber(userInputPhoneNumber.text.toString())
        }
    }

}