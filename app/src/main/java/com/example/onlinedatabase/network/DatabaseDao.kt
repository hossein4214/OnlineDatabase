package com.example.onlinedatabase.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.onlinedatabase.model.User

@Dao
interface DatabaseDao {

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT COUNT(name) FROM user_table")
    fun getCountUser():Int

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser():User

}