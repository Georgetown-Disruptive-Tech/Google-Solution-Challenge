package com.example.google_solution_challenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UniversityActivity : AppCompatActivity() {

    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var continueButton: Button
    private var selected = ""
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_university)

        autoCompleteTextView = findViewById(R.id.options)
        continueButton = findViewById(R.id.continueButton)

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)


        val db = Firebase.firestore
        val uniResources = db.collection("university-resources")
        val items = getListOfUniversities(uniResources)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteTextView.setAdapter(adapter)
        continueButton.visibility = View.INVISIBLE
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selected = s.toString()
                with(sharedPreferences.edit()) {
                    putString("University", s.toString())
                    apply()
                }
                continueButton.visibility = View.VISIBLE
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

        continueButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getListOfUniversities(collection: CollectionReference) : MutableList<String> {
        val res = mutableListOf<String>()
        collection.get().addOnSuccessListener { querySnapshot ->
            querySnapshot.forEach { document ->
                res.add(document.data["Name"] as String)
            }
        }
        return res
    }

}