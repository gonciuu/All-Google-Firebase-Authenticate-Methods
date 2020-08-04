package com.example.authloginmethods.screens.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {

    private val list = MutableLiveData<ArrayList<String?>>()

    fun setInfo(userUid:String,userLogin:String?,userPhone:String?,userName:String?){
        list.value!!.add(userUid)
        list.value!!.add(userLogin)
        list.value!!.add(userPhone)
        list.value!!.add(userName)
    }
    fun getInfo() : LiveData<ArrayList<String?>> =  list

}