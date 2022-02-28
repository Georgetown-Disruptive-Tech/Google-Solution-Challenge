package com.example.google_solution_challenge

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.google_solution_challenge.databinding.ActivityFirstBinding
import com.google.android.material.textfield.TextInputLayout

class FirstActivity : AppCompatActivity() {
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var continueButton : Button
    var selected = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        val items = resources.getStringArray(R.array.universities)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTextView = findViewById(R.id.options)
        autoCompleteTextView.setAdapter(adapter)
        continueButton = findViewById(R.id.continueButton)
        continueButton.setVisibility(View.INVISIBLE)
        autoCompleteTextView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selected = s.toString()
                continueButton.setVisibility(View.VISIBLE)
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        fun onContinue(view: View)
        {
            //proceed to next screen with the selected university.
        }
        
    }
    
    


}