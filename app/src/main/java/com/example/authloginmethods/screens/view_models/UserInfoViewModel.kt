package com.example.authloginmethods.screens.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {

    private val list = MutableLiveData<ArrayList<String>>()

    fun setInfo(userUid:String){
        val myList = arrayListOf<String>(userUid)
        list.value = myList
    }
    fun getInfo() : LiveData<ArrayList<String>> =  list

}