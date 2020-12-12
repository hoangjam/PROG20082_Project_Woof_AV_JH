package com.example.prog20082_project_av_jh.database

import android.location.Location
import androidx.lifecycle.LiveData
import com.example.prog20082_project_av_jh.model.User

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-11-25
*/
class UserRepository(
    private val userDao: UserDao
) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun insertAll(user: User){
        userDao.insertAll(user)
    }

    fun updateUser(user: User){
        userDao.updateUsers(user)
    }

    fun getUserByEmail(email: String) : User? {
        return userDao.getUserByEmail(email)
    }

    fun getUserByLoginInfo(email: String, password: String) : User?{
        return userDao.getUserByLoginInfo(email, password)
    }

//    fun getUserLikedInfo(email: String) : User?{
//        return userDao.getUserByLikedList(email)
//    }
}