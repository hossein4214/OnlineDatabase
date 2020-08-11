package com.example.onlinedatabase.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.DatabaseDao
import java.lang.IllegalArgumentException

class LoginViewModelFactory(private val dataSource: DatabaseDao,
                            private val context: Context
):ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(dataSource,context) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }

}