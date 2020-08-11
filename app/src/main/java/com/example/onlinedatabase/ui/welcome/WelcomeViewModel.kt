package com.example.onlinedatabase.ui.welcome

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.DatabaseDao
import kotlinx.coroutines.*

class WelcomeViewModel(
    val context: Context,
    val database: DatabaseDao,
    val user: User
) :ViewModel() {
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val isBackToLogin = MutableLiveData<Boolean>()

    init {
        isBackToLogin.value = false
    }

    fun onLogOutClicked(){
        uiScope.launch {
            Toast.makeText(context, "Logging Out", Toast.LENGTH_SHORT).show()
            isBackToLogin.value = true
            deleteUser(user)
        }
    }

    private suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO){
            database.deleteUser(user)
        }
    }

    fun doneLogOutClicked(){
        isBackToLogin.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}