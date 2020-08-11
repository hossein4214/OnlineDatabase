package com.example.onlinedatabase.ui.register

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.onlinedatabase.R
import com.example.onlinedatabase.database.Users
import com.example.onlinedatabase.databinding.FragmentRegisterBinding
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.DatabaseDao
import com.example.onlinedatabase.network.OnlineApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(
    val database: DatabaseDao,
    val context: Context
) :ViewModel() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    val isBtRegisterClicked = MutableLiveData<Boolean>()
    val isNavToLogin = MutableLiveData<Boolean>()
    val isNavToWelcome = MutableLiveData<User>()


    init {
        isBtRegisterClicked.value  = false
        isNavToLogin.value = false

    }


    fun onBtRegister(){
        isBtRegisterClicked.value = true
    }

    fun registeration(name:String,username:String,password:String,cPassword:String) {
        if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            if (password == cPassword) {
                OnlineApiService.OnlineApi.retrofitService.createUser(name, username, password)
                    .enqueue(object : Callback<Users> {
                        override fun onFailure(call: Call<Users>, t: Throwable) {
                            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Users>, response: Response<Users>) {
                            when {
                                response.body()?.getResponse().equals("ok") -> {
                                    coroutineScope.launch {
                                        insertUserToDatabase(name,username,password)
                                    }
                                    isNavToWelcome.value = User(name,username,password)
                                }
                                response.body()?.getResponse().equals("exist") -> {
                                    Toast.makeText(context, "User Already Exist", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                response.body()?.getResponse().equals("error") -> {
                                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    })
            } else {
                Toast.makeText(context, "Confirm Password Is Incorrect", Toast.LENGTH_SHORT).show()
            }
        } else
            Toast.makeText(context, "Blank Field Error!", Toast.LENGTH_SHORT).show()
    }

    private suspend fun insertUserToDatabase(name:String,username:String,password:String) {
        withContext(Dispatchers.IO){
            database.insertUser(User(name,username,password))
        }
    }


    fun onTvLogin(){
        isNavToLogin.value = true
    }


    fun  doneNavToLogin(){
        isNavToLogin.value = false
    }
    fun doneBtRegisterClicked(){
        isBtRegisterClicked.value = false
    }

    fun doneNavToWelcome(){
        isNavToWelcome.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}