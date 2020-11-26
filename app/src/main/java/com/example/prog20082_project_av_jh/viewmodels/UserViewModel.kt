package com.example.prog20082_project_av_jh.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prog20082_project_av_jh.database.UserRepository
import com.example.prog20082_project_av_jh.database.WoofDatabase
import com.example.prog20082_project_av_jh.model.User

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

//    fun insertAll(user: User) =
}