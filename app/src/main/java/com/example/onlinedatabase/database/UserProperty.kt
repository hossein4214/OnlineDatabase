package com.example.onlinedatabase.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Users {
    @SerializedName("response")
    @Expose
    private val response= String()
    @SerializedName("name")
    @Expose
    private val name: String = String()
    fun getResponse(): String {
        return response
    }
    fun getName():String{
        return name
    }
}

class GoogleUser{
    @SerializedName("response")
    @Expose
    private val response=String()
    @SerializedName("name")
    @Expose
    private val name=String()
    @SerializedName("image_url")
    @Expose
    private val imageUrl=String()

    fun  getResponse():String{
        return response
    }
    fun  getName():String{
        return name
    }
    fun  getImageUrl():String{
        return imageUrl
    }
}

