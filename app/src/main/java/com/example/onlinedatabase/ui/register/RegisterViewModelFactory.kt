package com.example.onlinedatabase.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlinedatabase.network.DatabaseDao

class RegisterViewModelFactory(
    val database: DatabaseDao,
    val context: Context
) :ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(database,context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
