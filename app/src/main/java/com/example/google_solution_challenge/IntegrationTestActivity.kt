package com.example.google_solution_challenge

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class IntegrationTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integration_testing)

        val addDataBtn = findViewById<Button>(R.id.addDataBtn)
        val retrieveDataBtn = findViewById<Button>(R.id.retrieveDataBtn)
        addDataBtn.setOnClickListener {
            addDataPressed()
        }
        retrieveDataBtn.setOnClickListener {
            retrieveDataPressed()
        }
    }

    private fun retrieveDataPressed() {
        val db = Firebase.firestore
        val surveyQuestions = db.collection("survey-questions")
        val dataTextBox = findViewById<EditText>(R.id.retrievedDataText)
        var str = " "
        surveyQuestions.get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    str += document.data["Question"]
                }
                dataTextBox.setText(str)
            }
            .addOnFailureListener { exception ->
                str += "Error getting documents $exception"
                dataTextBox.setText(str)
            }
    }

    private fun addDataPressed() {
        val db = Firebase.firestore
        val uniRes = db.collection("university-resources")

        val sampleData = hashMapOf(
            "Address" to "77 Massachusetts Ave, Cambridge, MA 02139",
            "Name" to "Massachusetts Institute of Technology",
            "Resources" to "JSON PLACEHOLDER"
        )

        uniRes.document().set(sampleData)
    }
}