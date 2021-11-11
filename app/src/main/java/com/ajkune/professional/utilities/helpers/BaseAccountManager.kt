package com.ajkune.professional.utilities.helpers

import android.content.Context
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

    var user : User? = null
    var token : String? = null

    fun create(user : User, token: String) {
        this.user = user
        this.token = token

        val job = GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            userDao.insert(user)

            this.coroutineContext.cancel()
        }

        val shared = AppPreferences.getShared(context)
        val editor = shared.edit()
        editor.putString(userIdKey, user.id)
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
        editor.putString(userIdKey, user.id)
        editor.apply()
    }

    fun start() {
        val shared = AppPreferences.getShared(context)

        val userId = shared.getString(userIdKey, "")
        val token = shared.getString(userTokenKey, null)

        val job = GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
            user =   userDao.getUserById(userId!!)
            this.coroutineContext.cancel()
        }
        this.token = token

    }

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
}