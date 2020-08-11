package com.example.onlinedatabase.ui.welcome


import android.os.Bundle
import android.util.Log
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
import com.example.onlinedatabase.databinding.FragmentWelcomeBinding
import com.example.onlinedatabase.model.User
import com.example.onlinedatabase.network.Database


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentWelcomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        val dataBase = Database.getInstance(context!!).databaseDao
        val user = WelcomeFragmentArgs.fromBundle(arguments!!).user

        val viewModelFactory = WelcomeViewModelFactory(context!!, dataBase, user)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        Log.e("HosseinStar", "Run3")
        viewModel.isBackToLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                Log.e("HosseinStar", "Run4")
                viewModel.doneLogOutClicked()
                this.findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
            }
        })

        //binding.textView.text = "Hello ".plus(DatabaseHelper(context).google_queryName())

        return binding.root
    }

}
