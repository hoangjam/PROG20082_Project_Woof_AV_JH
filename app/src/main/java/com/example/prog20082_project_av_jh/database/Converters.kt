package com.example.prog20082_project_av_jh.database

import androidx.room.TypeConverter
import com.google.gson.Gson

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-12-02
*/
class Converters {
    @TypeConverter
    fun listToJson(value: ArrayList<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): ArrayList<Int>? {
        val values = Gson().fromJson(value, ArrayList::class.java) as ArrayList<Int>
        return values
    }
}