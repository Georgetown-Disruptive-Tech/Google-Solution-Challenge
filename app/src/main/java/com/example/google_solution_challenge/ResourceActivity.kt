package com.example.google_solution_challenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


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
        title = "Resources"


        sharedPreferences =
            getSharedPreferences("com.example.google_solution_challenge", Context.MODE_PRIVATE)
        answerList = ObjectSerializer.deserialize(
            sharedPreferences
                .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))
        ) as ArrayList<Answer>
        val sublist = ArrayList<Answer>()
        val flag = arrayOf(0, 0, 0)
        val today = LocalDate.now().toString()
        val calendar: Calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date : Date? = format.parse(today)

        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        val yesterday: String = format.format(calendar.time)
        calendar.add(Calendar.DATE, -1)
        val twoDaysAgo: String = format.format(calendar.time)

        for (a in answerList) {
            when (a.date) {
                twoDaysAgo -> {
                    sublist.add(a)
                    flag[0] = 1
                }
                yesterday -> {
                    sublist.add(a)
                    flag[1] = 1
                }
                today -> {
                    sublist.add(a)
                    flag[2] = 1
                }
            }
        }
        if(flag[0] == 1 && flag[1] == 1 && flag[2] == 1)
        {
            //if user logs continuously for three days
            //check signs of depression
            var depressionMeter = 0
            for(a in sublist){
                if(a.question.contains("stress") && a.answer == "Yes") depressionMeter ++
                if(a.question.contains("satisfied") && (a.answer == "Not" || a.answer == "Barely")) depressionMeter ++
                if(a.question.contains("insomnia") && (a.answer.contains("insomnia") || a.answer == "asleep")) depressionMeter ++
                if(a.question.contains("performance") && (a.answer == "Always")) depressionMeter ++
                if(a.question.contains("guilt") && (a.answer == "Always")) depressionMeter ++
                if(a.question.contains("purging") && (a.answer == "Yes")) depressionMeter ++
                if (a.question.contains("social") && (a.answer == "Always")) depressionMeter++
                if (a.question.contains("intake") && (a.answer == "Yes")) depressionMeter++
            }
            if (depressionMeter >= 4) {
                recommendedResourceURL =
                    "https://www.everydayhealth.com/depression/guide/resources/"
                recommendedResourceButton.text = "Recommended Resource - Depression"
            }
        }
        initializeResources()
        setOnClickListeners()
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
            R.id.homeButton -> {
                intent.setClass(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun initializeResources() {

        generalResourceButton = findViewById(R.id.generalResources)
        nationalResourceButton = findViewById(R.id.nationalResources)
        stateResourceButton = findViewById(R.id.stateResources)
        recommendedResourceButton = findViewById(R.id.recommendedResources)

        val db = Firebase.firestore
        val university = sharedPreferences.getString("University", "")
        val generalResources = db.collection("general-resources")
        val stateResources = db.collection("state-resources")
        val nationalResources = db.collection("national-resources")

        stateResources.get().addOnSuccessListener { // querySnapshot ->
            //val text = "State Resource - " + (querySnapshot.data?.get("Name") as String)
            //stateResourceURL = querySnapshot.data?.get("URL") as String
            //stateResourceButton.text = text
        }
        nationalResources.get().addOnSuccessListener { querySnapshot ->
            val res = querySnapshot.shuffled().take(1)
            res.forEach { document ->
                val text = "National Resource - " + (document.data["Name"] as String)
                nationalResourceURL = (document.data["URL"] as String)
                nationalResourceButton.text = text
            }
        }
        generalResources.get().addOnSuccessListener { querySnapshot ->
            val res = querySnapshot.shuffled().take(1)
            res.forEach { document ->
                val text = "General Resource - " + (document.data["Name"] as String)
                generalResourceURL = (document.data["URL"] as String)
                generalResourceButton.text = text
            }
        }
    }

    private fun setOnClickListeners() {

        generalResourceButton = findViewById(R.id.generalResources)
        nationalResourceButton = findViewById(R.id.nationalResources)
        stateResourceButton = findViewById(R.id.stateResources)
        recommendedResourceButton = findViewById(R.id.recommendedResources)

        nationalResourceButton.setOnClickListener {
            try {
                val httpIntent = Intent(Intent.ACTION_VIEW)
                httpIntent.data = Uri.parse(nationalResourceURL)
                startActivity(httpIntent)
            } catch (e: Exception) {
                print(e.stackTrace)
            }
        }
        stateResourceButton.setOnClickListener {
            try {
                val httpIntent = Intent(Intent.ACTION_VIEW)
                httpIntent.data = Uri.parse(stateResourceURL)
                startActivity(httpIntent)
            } catch (e: Exception) {
                print(e.stackTrace)
            }
        }
        recommendedResourceButton.setOnClickListener {
            try {
                val httpIntent = Intent(Intent.ACTION_VIEW)
                httpIntent.data = Uri.parse(recommendedResourceURL)
                startActivity(httpIntent)
            } catch (e: Exception) {
                print(e.stackTrace)
            }
        }
        generalResourceButton.setOnClickListener {
            try {
                var uri: Uri =
                    Uri.parse(generalResourceURL) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } catch (e: Exception) {
                print(e.stackTrace)
            }
        }
    }
}
