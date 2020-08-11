package com.example.onlinedatabase.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.onlinedatabase.database.*
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.DatabaseDao
import com.example.onlinedatabase.network.OnlineApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(
    val database: DatabaseDao,
    val context: Context
) : ViewModel() {
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    val isBtLoginClicked = MutableLiveData<Boolean>()
    val isTvRegisterClicked = MutableLiveData<Boolean>()
    val isTransitionToWelcome = MutableLiveData<User>()
    

    init {
        isBtLoginClicked.value = false
        isTvRegisterClicked.value = false
    }

    fun onBtLogin() {
        isBtLoginClicked.value = true
    }

    fun onTvRegister() {
        isTvRegisterClicked.value = true
    }





    fun loggingIn(username: String, password: String) {
        OnlineApiService.OnlineApi.retrofitService.findUser(username, password)
            .enqueue(object : Callback<Users> {
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    if (response.body()?.getResponse().equals("ok")) {
                        uiScope.launch {
                            insertUserToDatabase(
                                response.body()?.getName().toString(),
                                username,
                                password
                            )
                        }
                        isTransitionToWelcome.value = User(response.body()?.getName().toString(),
                            username,
                            password)
                    } else {
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private suspend fun insertUserToDatabase(name: String, username: String, password: String) {
        withContext(Dispatchers.IO) {
            database.insertUser(
                User(
                    name,
                    username,
                    password
                )
            )
        }
    }



    fun doneTransitionToWelcome() {
        isTransitionToWelcome.value = null
    }

    fun doneTvRegisterClicked() {
        isTvRegisterClicked.value = false
    }


    fun doneBtLoginClicked() {
        isBtLoginClicked.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
