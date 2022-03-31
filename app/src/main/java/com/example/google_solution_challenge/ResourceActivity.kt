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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


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
        setTitle("Resources")

        generalResourceButton = findViewById(R.id.generalResources)
        nationalResourceButton = findViewById(R.id.nationalResources)
        stateResourceButton = findViewById(R.id.stateResources)
        recommendedResourceButton = findViewById(R.id.recommendedResources)

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        data = getSharedPreferences("com.example.google_solution_challenge", Context.MODE_PRIVATE)
        var answerList = ArrayList<Answer>()
        answerList = ObjectSerializer.deserialize(
            data
                .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))
        ) as ArrayList<Answer>
        val sublist = ArrayList<Answer>()
        val flag = arrayOf(0, 0, 0)
        val today = LocalDate.now().toString()
        System.out.println(today)
        val calendar: Calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date : Date? = format.parse(today)

        calendar.setTime(date)
        calendar.add(Calendar.DATE, -1)
        val yesterday: String = format.format(calendar.getTime())
        calendar.add(Calendar.DATE, -1)
        val twoDaysAgo :String = format.format(calendar.getTime())
        //System.out.println(yesterdayAsString)
        //System.out.println(twoDaysAgo)
        for (a in answerList) {
            if (a.date == twoDaysAgo) {
                sublist.add(a)
                flag[0] = 1
            }
            else if (a.date == yesterday){
                sublist.add(a)
                flag[1] = 1
            }
            else if (a.date == today){
                sublist.add(a)
                flag[2] = 1
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
                if(a.question.contains("social") && (a.answer == "Always")) depressionMeter ++
                if(a.question.contains("intake") && (a.answer == "Yes")) depressionMeter ++
            }
            if (depressionMeter >= 4)
            {
                recommendedResourceURL = "https://www.everydayhealth.com/depression/guide/resources/"
                recommendedResourceButton.setText("Recommended Resource - Depression")
            }
        }


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
            R.id.recommendedResources -> {
                if(recommendedResourceURL != "") {
                    var uri: Uri =
                        Uri.parse(recommendedResourceURL) // missing 'http://' will cause crashed
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "We don't have enough data to help you yet, please" +
                            "keep answering the questions so we can better help you!", Toast.LENGTH_SHORT).show()
                }
            }


        }


    }


}
