package com.example.prog20082_project_av_jh.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.prog20082_project_av_jh.database.UserRepository
import com.example.prog20082_project_av_jh.database.WoofDatabase
import com.example.prog20082_project_av_jh.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-11-25
*/
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepo: UserRepository
    var allUsers: LiveData<List<User>>

    private var matchedUser : MutableLiveData<User>?

    init{
        val userDao = WoofDatabase.getDatabase(application).userDao()
        userRepo = UserRepository(userDao)

        allUsers = userRepo.allUsers

        matchedUser = MutableLiveData()
    }

    fun insertAll(user: User) = viewModelScope.launch(Dispatchers.IO){
        userRepo.insertAll(user)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.updateUser(user)
    }

    private fun getUserByLoginInfoCoroutine(email: String, password: String)  = viewModelScope.launch(Dispatchers.IO) {
        val user : User? = userRepo.getUserByLoginInfo(email, password)
        matchedUser?.postValue(user)
    }

    fun getUserByLoginInfo(email: String, password: String) : MutableLiveData<User>?{
        getUserByLoginInfoCoroutine(email, password)
        return matchedUser
    }

    private  fun getUserByEmailCoroutine(email: String) = viewModelScope.launch (Dispatchers.IO){
        val user: User? = userRepo.getUserByEmail(email)
        matchedUser?.postValue(user)
    }

    fun getUserByEmail(email: String) : MutableLiveData<User>?{
        getUserByEmailCoroutine(email)
        Log.d("UserViewModel : ", matchedUser.toString())
        return matchedUser
    }
}