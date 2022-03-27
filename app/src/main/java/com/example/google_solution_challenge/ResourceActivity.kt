package com.example.google_solution_challenge

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer

class ResourceActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var data : SharedPreferences
    var answerList = ArrayList<Answer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        data = getSharedPreferences("com.example.google_solution_challenge", Context.MODE_PRIVATE)

        setContentView(R.layout.activity_resource)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.journalButton -> {
                intent.setClass(this, JournalActivity::class.java)
                startActivity(intent)
            }
            R.id.clearButton -> {
                answerList.clear()
                data.edit().remove("answers").apply()
            }
        }
        return true
    }

    //end onOptionsItemSelected
    fun onClick(view: View) {

    }


}
