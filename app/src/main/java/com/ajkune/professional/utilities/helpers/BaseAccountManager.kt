package com.ajkune.professional.utilities.helpers

import android.content.Context
import android.content.SharedPreferences
import com.ajkune.professional.architecture.db.MyDataBase
import com.ajkune.professional.architecture.db.dao.UserDao
import com.ajkune.professional.architecture.models.User
import com.ajkune.professional.utilities.data.AppPreferences
import kotlinx.coroutines.*
import javax.inject.Inject

class BaseAccountManager @Inject constructor(private val context: Context){

    private val userDao: UserDao = MyDataBase.getDatabase(context).userDao()

    private  val userIdKey = "UserId"
    private  val userTokenKey = "UserToken"
    val PREFS_FILENAME = "com.ajkune.proffesional"
    val ADDRESS_PREFERENCES = "selected_preferences"
    val CURRENT_LANGUAGE = "current_language"

    var user : User? = null
    var token : String? = null

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun create(user : User, token: String) {
        this.user = user
        this.token = token

        val job = GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            userDao.insert(user)

            this.coroutineContext.cancel()
        }

        val shared = AppPreferences.getShared(context)
        val editor = shared.edit()
        editor.putInt(userIdKey, user.id)
        editor.putString(userTokenKey, token)
        editor.apply()
    }

    fun getDeviceToken(context: Context):String?{
        return AppPreferences.getNotificationToken(context)
    }

    fun save(user: User) {

        this.user = user

        val job = GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            userDao.updateUsers(user)
            this.coroutineContext.cancel()
        }
        val shared = AppPreferences.getShared(context)
        val editor = shared.edit()
        editor.putInt(userIdKey, user.id)
        editor.apply()
    }

    fun start() {
        val shared = AppPreferences.getShared(context)

        val userId = shared.getInt(userIdKey, 0)
        val token = shared.getString(userTokenKey, null)

        val job = GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            user =   userDao.getUserById(userId)
            this.coroutineContext.cancel()
        }
        this.token = token

    }

    var currentUserAddress : String?
        get() = sharedPreferences.getString(ADDRESS_PREFERENCES, null)
        set(value) = sharedPreferences.edit().putString(ADDRESS_PREFERENCES, value).apply()

    fun delete() {
        //print(user?.username)
        val shared = AppPreferences.getShared(context)
        val editor = shared.edit()
        editor.remove(userIdKey)
        editor.remove(userTokenKey)
        editor.apply()

        val job = GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT ) {
            userDao.deleteAllUsers()
            this.coroutineContext.cancel()
        }
        job.cancel()
        this.user = null
        this.token = null
    }

    fun isLogged(): Boolean {
        return user != null
    }

    var language: String?
        get() = sharedPreferences.getString(CURRENT_LANGUAGE, null)
        set(value) = sharedPreferences.edit().putString(CURRENT_LANGUAGE, value).apply()
}