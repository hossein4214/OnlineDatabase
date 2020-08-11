package com.example.onlinedatabase.ui.welcome

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.DatabaseDao

class WelcomeViewModelFactory(
    val context: Context,
    val database: DatabaseDao,
    val user: User
) :ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WelcomeViewModel::class.java)){
            return WelcomeViewModel(context,database,user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}