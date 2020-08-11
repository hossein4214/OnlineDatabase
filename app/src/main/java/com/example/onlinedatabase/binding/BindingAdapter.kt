package com.example.onlinedatabase.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.onlinedatabase.R
import com.example.onlinedatabase.model.User

@BindingAdapter("fragment_welcome_textView")
fun TextView.setName(name: String){
    name.let {
        text = resources.getString(R.string.hello).plus(name)
    }
}
