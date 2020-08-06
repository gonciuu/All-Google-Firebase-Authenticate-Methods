package com.example.authloginmethods.screens.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.authloginmethods.R
import com.example.authloginmethods.auth.Authenticate
import com.example.authloginmethods.screens.view_models.UserDetailsViewModel
import kotlinx.android.synthetic.main.user_details_fragment.*

class UserDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = UserDetailsFragment()
    }


    private lateinit var authenticate: Authenticate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authenticate = Authenticate(this)
        //get user info and set it into layout
        val userDetailsViewModel = ViewModelProvider(requireActivity()).get(UserDetailsViewModel::class.java)
        userDetailsViewModel.getInfo().observe(viewLifecycleOwner, Observer {
                info->
            mainText.text = info[0]
            userInfo1.text = info[1]
            userInfo2.text = info[2]
            userInfo3.text = info[3]
        })

        //log out button
        logOutButton.setOnClickListener {
            authenticate.logOut()
        }

    }

}