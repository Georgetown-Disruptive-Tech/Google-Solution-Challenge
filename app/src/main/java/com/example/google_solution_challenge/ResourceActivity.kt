package com.example.google_solution_challenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ResourceActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var data : SharedPreferences
    var answerList = ArrayList<Answer>()
    var generalResourceURL = ""
    var stateResourceURL = ""
    var nationalResourceURL = ""
    var recommendedResourceURL = ""
    lateinit var generalResourceButton : Button
    lateinit var stateResourceButton : Button
    lateinit var nationalResourceButton: Button
    lateinit var recommendedResourceButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)
        generalResourceButton = findViewById(R.id.generalResources)
        nationalResourceButton = findViewById(R.id.nationalResources)
        stateResourceButton = findViewById(R.id.stateResources)
        recommendedResourceButton = findViewById(R.id.recommendedResources)

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        data = getSharedPreferences("com.example.google_solution_challenge", Context.MODE_PRIVATE)
        val db = Firebase.firestore
        val university = sharedPreferences.getString("University", "")
        val generalResources = db.collection("general-resources")
        val stateResources = university?.let { db.collection("state-resources").document(it) }
        val nationalResources = db.collection("national-resources")


        if (stateResources != null) {
            stateResources.get().addOnSuccessListener { querySnapshot ->
                val text = "State Resource - " + (querySnapshot.data?.get("Name") as String)
                stateResourceURL = querySnapshot.data?.get("URL") as String
                stateResourceButton.setText(text)
            }
        }
        nationalResources.get().addOnSuccessListener { querySnapshot ->
            val res = querySnapshot.shuffled().take(1)
            res.forEach { document ->
                val text = "National Resource - " + (document.data["Name"] as String)
                nationalResourceURL = (document.data["URL"] as String)
                nationalResourceButton.setText(text)
            }
        }
        generalResources.get().addOnSuccessListener { querySnapshot ->
            val res = querySnapshot.shuffled().take(1)
            res.forEach { document ->
                val text = "General Resource - " + (document.data["Name"] as String)
                generalResourceURL = (document.data["URL"] as String)
                generalResourceButton.setText(text)
            }
        }

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
            R.id.homeButton ->{
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    //end onOptionsItemSelected
    fun onClick(view: View) {
        when(view.id){
            R.id.nationalResources -> {
                val uri: Uri = Uri.parse(nationalResourceURL) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            R.id.stateResources -> {
                val uri: Uri = Uri.parse(stateResourceURL) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            R.id.generalResources -> {
                val uri: Uri = Uri.parse(generalResourceURL) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }


        }


    }


}
