package com.example.authloginmethods.screens.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserDetailsViewModel : ViewModel() {

    //set and get user info

    private val userInfoList = MutableLiveData<ArrayList<String>>()

    fun setInfo(userInfo : ArrayList<String>){
        userInfoList.value = userInfo
    }

    fun getInfo():LiveData<ArrayList<String>> = userInfoList

}