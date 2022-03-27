package com.example.google_solution_challenge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var JournalButton: Button
    private lateinit var QuestionsButton: Button
    private lateinit var SettingsButton: Button
    private lateinit var ResourcesButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        JournalButton = findViewById(R.id.JournalButton)
        QuestionsButton = findViewById(R.id.QuestionsButton)
        SettingsButton = findViewById(R.id.SettingsButton)
        ResourcesButton = findViewById(R.id.ResourcesButton)

        JournalButton.setOnClickListener {
            val intent = Intent(this, JournalActivity::class.java)
            startActivity(intent)
        }

        QuestionsButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        SettingsButton.setOnClickListener {

        }

        ResourcesButton.setOnClickListener {
            val intent = Intent(this, ResourceActivity::class.java)
            startActivity(intent)
        }

    }
}