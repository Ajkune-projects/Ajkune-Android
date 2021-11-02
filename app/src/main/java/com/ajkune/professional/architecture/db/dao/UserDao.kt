package com.ajkune.professional.architecture.db.dao

import androidx.room.*
import com.ajkune.professional.architecture.models.User

@Dao
interface UserDao {

    @Query("SELECT * from user_table WHERE user_id LIKE :userId")
    fun getUserById(userId: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun updateUsers(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("Delete from user_table")
    fun deleteAllUsers()
}