package com.example.onlinedatabase.ui.register


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.onlinedatabase.R
import com.example.onlinedatabase.network.OnlineApiService
import com.example.onlinedatabase.database.Users
import com.example.onlinedatabase.databinding.FragmentRegisterBinding
import com.example.onlinedatabase.network.Database
import com.example.onlinedatabase.ui.login.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Database.getInstance(context!!).databaseDao
        val viewModelFactory = RegisterViewModelFactory(database, context!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.isBtRegisterClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.registeration(
                    binding.fragmentRegisterName.editableText.toString(),
                    binding.fragmentRegisterUsername.editableText.toString(),
                    binding.fragmentRegisterPassword.editableText.toString(),
                    binding.fragmentRegisterCPassword.editableText.toString()
                )
                viewModel.doneBtRegisterClicked()
            }
        })

        viewModel.isNavToWelcome.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToWelcomeFragment(it))
                viewModel.doneNavToWelcome()
            }
        })

        viewModel.isNavToLogin.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                viewModel.doneNavToLogin()
            }
        })


        return binding.root
    }

/*
    private fun animating(binding: FragmentRegisterBinding) {

        binding.imageViewBack.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentRegisterBtLogin.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentRegisterCPassword.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentRegisterPassword.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentRegisterUsername.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentRegisterName.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentRegisterImageView.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
    }
*/

    private fun registeration(binding: FragmentRegisterBinding) {
        val name = binding.fragmentRegisterName.text.toString()
        val username = binding.fragmentRegisterUsername.text.toString().trim()
        val password = binding.fragmentRegisterPassword.text.toString().trim()
        val c_password = binding.fragmentRegisterCPassword.text.toString().trim()
        if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            if (password == c_password) {
                OnlineApiService.OnlineApi.retrofitService.createUser(name, username, password)
                    .enqueue(object : Callback<Users> {
                        override fun onFailure(call: Call<Users>, t: Throwable) {
                            Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Users>, response: Response<Users>) {
                            if (response.body()?.getResponse().equals("ok")) {
                                //DatabaseHelper(context).insertUser(name, username, password)
                                binding.root.findNavController()
                                    .navigate(R.id.action_registerFragment_to_welcomeFragment)
                            } else if (response.body()?.getResponse().equals("exist")) {
                                Toast.makeText(context, "User Already Exist", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (response.body()?.getResponse().equals("error")) {
                                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
            } else {
                Toast.makeText(context, "Confirm Password Is Incorrect", Toast.LENGTH_SHORT).show()
            }
        } else
            Toast.makeText(context, "Blank Field Error!", Toast.LENGTH_SHORT).show()
    }


}

