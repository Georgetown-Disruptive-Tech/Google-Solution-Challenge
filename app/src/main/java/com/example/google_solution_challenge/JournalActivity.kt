package com.example.google_solution_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.hw5.Answer
import java.time.LocalDate

class JournalActivity : AppCompatActivity() {
    lateinit var list : ListView
    var selectedDate = LocalDate.now().toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)
        list = findViewById(R.id.list)
        answerList = ObjectSerializer.deserialize(sharedPreferences
            .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))) as ArrayList<Answer>
        var sublist = ArrayList<Answer>()
        for (a in answerList){
            if(a.date == selectedDate)
            {
                sublist.add(a)
            }
        }
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sublist)
        list.adapter = arrayAdapter
    }

}