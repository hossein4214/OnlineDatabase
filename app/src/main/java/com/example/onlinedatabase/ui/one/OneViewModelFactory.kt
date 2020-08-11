package com.example.onlinedatabase.ui.one

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlinedatabase.network.DatabaseDao

class OneViewModelFactory(val database:DatabaseDao) :ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OneViewModel::class.java)){
            return OneViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}