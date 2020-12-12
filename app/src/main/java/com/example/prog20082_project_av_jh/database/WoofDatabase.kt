package com.example.prog20082_project_av_jh.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.prog20082_project_av_jh.model.User

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-11-25
*/
@Database(entities = arrayOf(User::class), version = 16)
@TypeConverters(Converters::class)
abstract class WoofDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object{

        @Volatile
        private var INSTANCE: WoofDatabase? = null

        fun getDatabase(context: Context) : WoofDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WoofDatabase::class.java,
                    "com_project_av_jh_woof_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}