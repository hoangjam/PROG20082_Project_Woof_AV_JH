package com.example.prog20082_project_av_jh.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.prog20082_project_av_jh.database.Converters
import com.example.prog20082_project_av_jh.database.UserDao
import com.example.prog20082_project_av_jh.database.WoofDatabase

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-11-25
*/ 

@Entity(tableName = "Users", primaryKeys = arrayOf("email"))
data class User(
        @ColumnInfo(name = "owner_name") var oName: String,
        @ColumnInfo(name = "email") var email: String,
        @ColumnInfo(name = "phone_number") var phoneNumber: String,
        @ColumnInfo(name = "password") var password: String,
        @ColumnInfo(name = "dog_name") var dName: String,
        @ColumnInfo(name = "gender") var gender: String?,
        @ColumnInfo(name = "breed") var breed: String?,
        @ColumnInfo(name = "age") var age: Int?,
        @ColumnInfo(name = "bio") var bio: String?,
        @ColumnInfo(name = "dog_id") var dogId: Int,
        @ColumnInfo(name = "dog_size") var dogSize: String?,
        @ColumnInfo(name = "liked_list") var likedList: ArrayList<Int>?,
        @ColumnInfo(name = "matched_list") var matchedList: ArrayList<Int>?
){
    constructor() : this(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            "",
            0,
            "",
            arrayListOf<Int>(),
            arrayListOf<Int>()
    )
}
