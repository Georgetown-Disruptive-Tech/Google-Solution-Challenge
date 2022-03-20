package com.example.google_solution_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import android.widget.TextView
import com.example.hw5.Answer
import java.time.LocalDate

class JournalActivity : AppCompatActivity() {
    lateinit var list : ListView
    var selectedDate = LocalDate.now().toString()
    lateinit var text : TextView
    lateinit var calendar : CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)
        text = findViewById(R.id.journal)
        text.setText("Journal Log\n$selectedDate")
        calendar = findViewById(R.id.calendar)
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var monthString = (month + 1).toString()
            var dayOfMonthString = dayOfMonth.toString()
            if (month < 10) {
                monthString = "0" + monthString
            }
            if (dayOfMonth < 10) {
                dayOfMonthString = "0" + dayOfMonthString
            }
            selectedDate = year.toString() + "-" + monthString + "-" + dayOfMonthString
            text.setText("Journal Log\n$selectedDate")
            updateList()

        }
        list = findViewById(R.id.list)
        answerList = ObjectSerializer.deserialize(sharedPreferences
            .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))) as ArrayList<Answer>
        updateList()

    }
    fun updateList(){
        var sublist = ArrayList<Answer>()
        for (a in answerList){
            if(a.date.equals(selectedDate))
            {
                sublist.add(a)
            }
        }
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sublist)
        list.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()
    }

}