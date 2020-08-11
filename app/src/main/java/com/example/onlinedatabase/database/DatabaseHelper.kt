package com.example.onlinedatabase.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/*
class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, "login", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE login (id INTEGER PRIMARY KEY NOT NULL ,name TEXT,username TEXT,password TEXT)")
        db?.execSQL("CREATE TABLE google_login (id INTEGER PRIMARY KEY NOT NULL , email TEXT,name TEXT ,image_url TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS login")
        db?.execSQL("DROP TABLE IF EXISTS google_login")
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    fun insertUser(name: String, username: String, password: String) {
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("username", username)
        contentValues.put("password", password)
        writableDatabase.insert("login", null, contentValues)
    }

    fun delUser(name: String?): Boolean {
        return writableDatabase.delete("login", "name =?", arrayOf(name)) > 0
    }

    fun queryUser(): Int {
        return readableDatabase.query("login", arrayOf("name"), null, null, null, null, null).count
    }

    fun queryName(): String {
        val cursor = readableDatabase.query("login", arrayOf("name"), null, null, null, null, null)
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("name"))
    }

    fun google_delUser(email: String?): Boolean {
        return writableDatabase.delete("google_login", null, null) > 0
    }

    fun google_insertUser(email: String, name: String, image_url: String) {
        val cv = ContentValues()
        cv.put("email", email)
        cv.put("name", name)
        cv.put("image_url", image_url)
        writableDatabase.insert("google_login", null, cv)
    }

    fun google_queryUser(): Int {
        return readableDatabase.query(
            "google_login",
            arrayOf("email"),
            null,
            null,
            null,
            null,
            null
        ).count

    }

    fun google_queryName(): String {
        val cursor =
            readableDatabase.query("google_login", arrayOf("name"), null, null, null, null, null)
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("name"))
    }
}*/
