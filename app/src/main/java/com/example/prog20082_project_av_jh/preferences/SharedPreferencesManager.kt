package com.example.prog20082_project_av_jh.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-12-11
*/
object SharedPreferencesManager {

    private var sharedPreferences: SharedPreferences? = null

    val EMAIL = "KEY_EMAIL"
    val PASSWORD = "KEY_PASSWORD"
//    val LOCATION = "KEY_LOCATION"

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences =
                context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        }
    }

    fun write(key: String?, value: String?) {
        apply {
            sharedPreferences!!.edit().putString(key, value).apply()
        }
    }

    fun read(key: String?, defaultValue: String?): String?{
        with(sharedPreferences) {
            if (this!!.contains(key)){
                return sharedPreferences!!.getString(key,defaultValue)
            }
        }
        return defaultValue
    }

//    fun removeAll(key: String?) {
//        with(sharedPreferences!!.edit()) {
//            if (sharedPreferences == null && sharedPreferences!!.contains(key)) {
//                remove(EMAIL)
//                remove(PASSWORD)
//
//                apply()
//            }
//        }
//    }
}
