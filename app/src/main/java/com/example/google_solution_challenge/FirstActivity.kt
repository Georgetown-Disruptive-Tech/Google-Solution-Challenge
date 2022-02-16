package com.example.google_solution_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputLayout

class FirstActivity : AppCompatActivity() {
    lateinit var textFiled : AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        textFiled = findViewById(R.id.text)
        (textFiled.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }


}