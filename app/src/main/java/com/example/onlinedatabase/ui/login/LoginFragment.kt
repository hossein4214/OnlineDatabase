package com.example.onlinedatabase.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlinedatabase.R
import com.example.onlinedatabase.network.Database
import com.example.onlinedatabase.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataSource = Database.getInstance(context!!).databaseDao
        val viewModelFactory = LoginViewModelFactory(dataSource, context!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isTvRegisterClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                viewModel.doneTvRegisterClicked()

            }
        })

        viewModel.isBtLoginClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.loggingIn(
                    binding.fragmentLoginUsername.editableText.toString(),
                    binding.fragmentLoginPassword.editableText.toString()
                )
                viewModel.doneBtLoginClicked()
            }
        })
        viewModel.isTransitionToWelcome.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                this.findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(
                        user
                    )
                )
                viewModel.doneTransitionToWelcome()
            }
        })



        return binding.root
    }


/*
    private fun animating(binding: FragmentLoginBinding) {
        binding.fragmentLoginBtLogin.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentLoginLinear.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentLoginPassword.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentLoginUsername.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
        binding.fragmentLoginImageView.apply {
            this.visibility = View.VISIBLE
            this.animate().alpha(1.0f).duration = 2000
        }
    }
*/


/*
    private fun logining(binding: FragmentLoginBinding) {
        val username = binding.fragmentLoginUsername.text.toString().trim()
        val password = binding.fragmentLoginPassword.text.toString().trim()
        OnlineApiService.OnlineApi.retrofitService.findUser(username, password)
            .enqueue(object : Callback<Users> {
                override fun onFailure(call: Call<Users>, t: Throwable) {
                    Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    if (response.body()?.getResponse().equals("ok")) {
                        val bundle = Bundle()
                        bundle.putString("name", response.body()?.getName())
                        binding.root.findNavController()
                            .navigate(R.id.action_loginFragment_to_welcomeFragment, bundle)
                        DatabaseHelper(this@LoginFragment.context).insertUser(
                            response.body()?.getName().toString(),
                            username,
                            password
                        )
                    } else {
                        Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
*/

}
