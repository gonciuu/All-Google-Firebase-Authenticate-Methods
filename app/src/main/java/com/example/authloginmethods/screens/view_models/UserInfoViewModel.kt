package com.example.authloginmethods.screens.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {

    private val uid = MutableLiveData<String>()


    fun setInfo(userUid:String){
        uid.value = userUid

    }
    fun getInfo(){

    }


}