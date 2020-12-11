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
    fun listToJson(value: ArrayList<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): ArrayList<Int>? {
        val values = Gson().fromJson(value, ArrayList::class.java) as ArrayList<Int>
        return values
    }

    @TypeConverter
    fun toLocation(value: String?): Location? {
        val location = Gson().fromJson(value, Location::class.java) as Location
        return location
    }

    @TypeConverter
    fun toLocationString(location: Location?): String? {
        return Gson().toJson(location)
    }

}