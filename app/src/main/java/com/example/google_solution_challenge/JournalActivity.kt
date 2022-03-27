package com.example.google_solution_challenge

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer
import java.time.LocalDate

class JournalActivity : AppCompatActivity() {
    private lateinit var list: ListView

    @RequiresApi(Build.VERSION_CODES.O)
    var selectedDate = LocalDate.now().toString()
    lateinit var text: TextView
    private lateinit var calendar: CalendarView

    private lateinit var sharedPreferences: SharedPreferences
    var answerList = ArrayList<Answer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)
        text = findViewById(R.id.journal)
        text.text = "Journal Log\n$selectedDate"
        calendar = findViewById(R.id.calendar)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var monthString = (month + 1).toString()
            var dayOfMonthString = dayOfMonth.toString()
            if (month < 10) {
                monthString = "0$monthString"
            }
            if (dayOfMonth < 10) {
                dayOfMonthString = "0$dayOfMonthString"
            }
            selectedDate = "$year-$monthString-$dayOfMonthString"
            text.text = "Journal Log\n$selectedDate"
            updateList()

        }
        list = findViewById(R.id.list)
        answerList = ObjectSerializer.deserialize(
            sharedPreferences
                .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))
        ) as ArrayList<Answer>
        updateList()

    }

    private fun updateList() {
        val sublist = ArrayList<Answer>()
        for (a in answerList) {
            if (a.date == selectedDate) {
                sublist.add(a)
            }
        }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sublist)
        list.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()
    }

}