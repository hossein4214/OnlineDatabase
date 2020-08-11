package com.example.onlinedatabase.ui.one

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.DatabaseDao
import kotlinx.coroutines.*
import java.util.logging.Handler

class OneViewModel(val database: DatabaseDao) : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val isGoToWelcomeFragment = MutableLiveData<User>()

    val isGoToLoginFragment = MutableLiveData<Boolean>()

   //val isTimerEnd = MutableLiveData<Boolean>()

    init {
        isGoToLoginFragment.value = false
     //   isTimerEnd.value = false
        timer()
    }


    private fun timer(){
        android.os.Handler().postDelayed(Runnable {
            searchForLoggedInUser()
        },3000)
    }

    private fun searchForLoggedInUser() {
        uiScope.launch {
            val count = searchingLoggedInUser()
            if (count > 0)
                isGoToWelcomeFragment.value = findLoggedInUser()
            else
                isGoToLoginFragment.value = true
        }
    }

    private suspend fun findLoggedInUser(): User {
        return withContext(Dispatchers.IO) {
            database.getUser()
        }
    }

    private suspend fun searchingLoggedInUser(): Int {
        return withContext(Dispatchers.IO) {
            database.getCountUser()
        }
    }

    fun doneGoToLoginFragment(){
        isGoToLoginFragment.value = false
    }
    fun doneGoToWelcomeFragment(){
        isGoToWelcomeFragment.value = null
    }



    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}