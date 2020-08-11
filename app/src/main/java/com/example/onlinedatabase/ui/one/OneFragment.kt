package com.example.onlinedatabase.ui.one

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.onlinedatabase.R
import com.example.onlinedatabase.databinding.FragmentOneBinding
import com.example.onlinedatabase.network.Database

class OneFragment : Fragment() {

    lateinit var viewModel: OneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Database.getInstance(context!!).databaseDao
        val viewModelFactory = OneViewModelFactory(database)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OneViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentOneBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_one,container,false)
        binding.lifecycleOwner = this

        /*var isTimerEnded = false
        viewModel.isTimerEnd.observe(viewLifecycleOwner, Observer {
            if(it)
                isTimerEnded = it
        })*/

        viewModel.isGoToLoginFragment.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(OneFragmentDirections.actionOneFragmentToLoginFragment())
                viewModel.doneGoToLoginFragment()
            }
        })

        viewModel.isGoToWelcomeFragment.observe(viewLifecycleOwner, Observer {
            if(it != null){
                this.findNavController().navigate(OneFragmentDirections.actionOneFragmentToWelcomeFragment(it))
                viewModel.doneGoToWelcomeFragment()
            }
        })

        return binding.root
    }
}