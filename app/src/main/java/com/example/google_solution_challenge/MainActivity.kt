package com.example.google_solution_challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer
import org.json.JSONArray
import org.json.JSONObject




class MainActivity : AppCompatActivity() {

    private lateinit var JournalButton: Button
    private lateinit var QuestionsButton: Button
    private lateinit var ExportButton: Button
    private lateinit var ResourcesButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Health"
        JournalButton = findViewById(R.id.JournalButton)
        QuestionsButton = findViewById(R.id.QuestionsButton)
        ExportButton = findViewById(R.id.ExportButton)
        ResourcesButton = findViewById(R.id.ResourcesButton)

        JournalButton.setOnClickListener {
            val intent = Intent(this, JournalActivity::class.java)
            startActivity(intent)
        }

        QuestionsButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        ExportButton.setOnClickListener {
            var answerList = ArrayList<Answer>()
            val sharedPreferences = getSharedPreferences("com.example.google_solution_challenge", Context.MODE_PRIVATE)
            answerList = ObjectSerializer.deserialize(
                sharedPreferences
                    .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))
            ) as ArrayList<Answer>
            if(answerList.isEmpty())
            {
                Toast.makeText(this, "No entries to export!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var current = ""
            var jsonArray = JSONArray()
            var jsonObject = JSONObject()
            var dateObject = JSONObject()
            for (a in answerList){
                if(a.date != current){
                    if(current!= "") dateObject.put(current, jsonObject)
                    current = a.date
                    if(jsonArray.length() != 0) jsonArray.put(dateObject)

                    jsonObject = JSONObject()
                }
                jsonObject.put(a.question, a.answer)
            }
            jsonObject.put(answerList.last().question, answerList.last().answer)
            dateObject.put(current, jsonObject)
            jsonArray.put(dateObject)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, jsonArray.toString())
                type = "text/json"
            }


            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        ResourcesButton.setOnClickListener {
            val intent = Intent(this, ResourceActivity::class.java)
            startActivity(intent)
        }

    }
}