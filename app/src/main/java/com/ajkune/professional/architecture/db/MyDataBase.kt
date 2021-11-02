package com.ajkune.professional.architecture.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ajkune.professional.architecture.db.dao.UserDao
import com.ajkune.professional.architecture.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1)
abstract class MyDataBase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MyDataBase? = null

        fun getDatabase(context: Context): MyDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    "MyDataBase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}