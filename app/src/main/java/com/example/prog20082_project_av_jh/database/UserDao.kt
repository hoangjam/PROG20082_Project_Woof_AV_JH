package com.example.prog20082_project_av_jh.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.prog20082_project_av_jh.model.User

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-11-25
*/

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg users: User)

    @Update
    fun updateUsers(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM Users WHERE email LIKE :email")
    fun deleteUserByEmail(email: String)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM Users WHERE email LIKE :email")
    fun getUserByEmail(email: String) : User?

    @Query("SELECT * FROM Users WHERE email LIKE :email AND password LIKE :pwd")
    fun getUserByLoginInfo(email: String, pwd: String): User?
}