package com.example.authloginmethods.screens.screens


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import kotlinx.android.synthetic.main.login_methods_fragment.*

class LoginMethodsFragment : Fragment() {
    private val authenticate = Authenticate()
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
        anomyusLogin.setOnClickListener {
            Toast.makeText(context,"Loading...", Toast.LENGTH_SHORT).show()
            authenticate.signInAnomyus(this)
        }
    }


}