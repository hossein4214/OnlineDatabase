package com.example.onlinedatabase.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="user_table")
data class User(
    @PrimaryKey @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name="username")
    var username:String,
    @ColumnInfo(name = "password")
    var password:String
):Parcelable