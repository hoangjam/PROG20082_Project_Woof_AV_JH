package com.example.prog20082_project_av_jh.model

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.prog20082_project_av_jh.database.Converters
import com.example.prog20082_project_av_jh.database.UserDao
import com.example.prog20082_project_av_jh.database.WoofDatabase
import java.util.*
import kotlin.collections.ArrayList

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
    @ColumnInfo(name = "dog_id") var dogId: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "dog_size") var dogSize: Int?,
    @ColumnInfo(name = "liked_list") var likedList: ArrayList<String>?,
    @ColumnInfo(name = "matched_list") var matchedList: ArrayList<String>?,
    @ColumnInfo(name = "disliked_list") var dislikedList: ArrayList<String>?,
    @ColumnInfo(name = "latitude") var lat: Double?,
    @ColumnInfo(name = "longitude") var lng: Double?
) {
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
        UUID.randomUUID().toString(),
        0,
        arrayListOf<String>(),
        arrayListOf<String>(),
        arrayListOf<String>(),
        0.0,
        0.0
    )

    constructor(
        name: String,
        email: String,
        phone: String,
        password: String,
        dName: String,
        gender: String,
        breed: String,
        age: Int,
        bio: String,
        dogSize: Int
    ) : this(
        name,
        email,
        phone,
        password,
        dName,
        gender,
        breed,
        age,
        bio,
        UUID.randomUUID().toString(),
        dogSize,
        arrayListOf<String>(),
        arrayListOf<String>(),
        arrayListOf<String>(),
        0.0,
        0.0
    )
}
