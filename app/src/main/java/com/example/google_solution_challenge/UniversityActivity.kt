package com.example.google_solution_challenge

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class UniversityActivity : AppCompatActivity() {

    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var continueButton : Button
    var selected = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_university)
        autoCompleteTextView = findViewById(R.id.options)
        continueButton = findViewById(R.id.continueButton)

        val items = resources.getStringArray(R.array.universities)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
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
    }

    fun switchActivity(view : View){
        val intent = Intent(this, QuestionActivity::class.java)
        startActivity(intent)
    }

}