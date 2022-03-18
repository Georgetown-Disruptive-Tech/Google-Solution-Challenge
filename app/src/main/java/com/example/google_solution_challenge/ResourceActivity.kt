package com.example.google_solution_challenge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ResourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

    }
    fun onClick(view: View)
    {
        intent = getIntent()
        intent.setClass(this, JournalActivity::class.java)
        startActivity(intent)
    }


}