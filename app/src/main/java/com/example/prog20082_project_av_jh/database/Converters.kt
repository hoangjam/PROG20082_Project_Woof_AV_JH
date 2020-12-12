package com.example.prog20082_project_av_jh.database

import android.location.Location
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
    fun listToJson(value: ArrayList<String>?) = Gson().toJson(value)

//    @TypeConverter
//    fun listStrToJson(value: ArrayList<String>?) = Gson().toJson(value)
//

    @TypeConverter
    fun jsonToList(value: String): ArrayList<String>? {
        val values = Gson().fromJson(value, ArrayList::class.java) as ArrayList<String>
        return values
    }

    //    @TypeConverter
//    fun jsonToStrList(value: String): ArrayList<String>? {
//        val values = Gson().fromJson(value, ArrayList::class.java) as ArrayList<String>
//        return values
//    }

}