package com.example.google_solution_challenge

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hw5.Answer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate


class QuestionActivity : AppCompatActivity() {
    var page = 1
    lateinit var sharedPreferences: SharedPreferences
    var answerList = ArrayList<Answer>()

    // private var temp = "What can we help you with today?"
    var questionArray = mutableListOf<String>()
    var answerIndex = arrayOf(-1, -1, -1, -1)
    var answerArray = arrayOf("null", "null", "null", "null")
    val a1List = mutableListOf<String>()
    val a2List = mutableListOf<String>()
    val a3List = mutableListOf<String>()
    val a4List = mutableListOf<String>()
    lateinit var answer1: Button
    lateinit var answer2: Button
    lateinit var answer3: Button
    lateinit var answer4: Button
    lateinit var question1: Button
    lateinit var question2: Button
    lateinit var question3: Button
    lateinit var question4: Button
    lateinit var text: TextView
    var questionType = 0 // 0 -> 4 options, 1 -> 2 options
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        val db = Firebase.firestore
        val daily = db.collection("daily-questions")
        val rotational = db.collection("rotational-questions")
        val res = mutableListOf<String>()

        daily.get().addOnSuccessListener { querySnapshot ->
            val res = querySnapshot.shuffled().take(2)
            res.forEach { document ->
                val map: Map<String, Any> = document.data
                questionArray.add(document.data["Question"] as String)
                a1List.add(document.data["A1"] as String)
                a2List.add(document.data["A2"] as String)
                if (map.containsKey("A3")) {
                    a3List.add(document.data["A3"] as String)
                } else {
                    a3List.add("null")
                }
                if (map.containsKey("A3")) {
                    a4List.add(document.data["A4"] as String)
                } else {
                    a4List.add("null")
                }
            }
            rotational.get().addOnSuccessListener { querySnapshot ->
                val res = querySnapshot.shuffled().take(2)
                res.forEach { document ->
                    val map: Map<String, Any> = document.data
                    questionArray.add(document.data["Question"] as String)
                    a1List.add(document.data["A1"] as String)
                    a2List.add(document.data["A2"] as String)
                    if (map.containsKey("A3")) {
                        a3List.add(document.data["A3"] as String)
                    } else {
                        a3List.add("null")
                    }
                    if (map.containsKey("A3")) {
                        a4List.add(document.data["A4"] as String)
                    } else {
                        a4List.add("null")
                    }
                }
                text = findViewById(R.id.promptQuestion)
                text.text = questionArray[0]
                initializeAnswers()
            }
        }





        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)
        answer4 = findViewById(R.id.answer4)
        question1 = findViewById(R.id.question1)
        question2 = findViewById(R.id.question2)
        question3 = findViewById(R.id.question3)
        question4 = findViewById(R.id.question4)
        question1.setTextColor(Color.BLACK)
        if (questionType == 1)
        {
            answer3.visibility = View.INVISIBLE
            answer4.visibility = View.INVISIBLE
        }
        else{
            answer3.visibility = View.VISIBLE
            answer4.visibility = View.VISIBLE
        }
        answer1.setOnClickListener {
            when (page) {
                1 -> {
                    answerIndex[0] = 0
                    answerArray[0] = answer1.text.toString()
                }
                2 -> {
                    answerIndex[1] = 0
                    answerArray[1] = answer1.text.toString()
                }
                3 -> {
                    answerIndex[2] = 0
                    answerArray[2] = answer1.text.toString()
                }
                4 -> {
                    answerIndex[3] = 0
                    answerArray[3] = answer1.text.toString()
                }
            }
            onAnswer()
        }
        answer2.setOnClickListener {
            when (page) {
                1 -> {
                    answerIndex[0] = 1
                    answerArray[0] = answer2.text.toString()
                }
                2 -> {
                    answerIndex[1] = 1
                    answerArray[1] = answer2.text.toString()
                }
                3 -> {
                    answerIndex[2] = 1
                    answerArray[2] = answer2.text.toString()
                }
                4 -> {
                    answerIndex[3] = 1
                    answerArray[3] = answer2.text.toString()
                }
            }
            onAnswer()
        }
        answer3.setOnClickListener {
            when (page) {
                1 -> {
                    answerIndex[0] = 2
                    answerArray[0] = answer3.text.toString()
                }
                2 -> {
                    answerIndex[1] = 2
                    answerArray[1] = answer3.text.toString()
                }
                3 -> {
                    answerIndex[2] = 2
                    answerArray[2] = answer3.text.toString()
                }
                4 -> {
                    answerIndex[3] = 2
                    answerArray[3] = answer3.text.toString()
                }
            }
            onAnswer()
        }
        answer4.setOnClickListener {
            when (page) {
                1 -> {
                    answerIndex[0] = 3
                    answerArray[0] = answer4.text.toString()
                }
                2 -> {
                    answerIndex[1] = 3
                    answerArray[1] = answer4.text.toString()
                }
                3 -> {
                    answerIndex[2] = 3
                    answerArray[2] = answer4.text.toString()
                }
                4 -> {
                    answerIndex[3] = 3
                    answerArray[3] = answer4.text.toString()
                }
            }
            onAnswer()
        }
        question1.setOnClickListener { changeQuestion(1) }
        question2.setOnClickListener { changeQuestion(2) }
        question3.setOnClickListener { changeQuestion(3) }
        question4.setOnClickListener { changeQuestion(4) }
    }

    private fun initializeAnswers() {
        answer1.text = a1List[0]
        answer2.text = a2List[0]
        if (a3List[0] == "null") {
            answer3.visibility = View.INVISIBLE
        } else {
            answer3.text = a3List[0]
        }
        if (a4List[0] == "null") {
            answer4.visibility = View.INVISIBLE
        } else {
            answer4.text = a4List[0]
        }
    }


    private fun updateQuestion() {
        text.text = questionArray[page - 1]
        answer1.text = a1List[page - 1]
        answer2.text = a2List[page - 1]
        if (a3List[page - 1] == "null") {
            answer3.visibility = View.INVISIBLE
        } else {
            answer3.text = a3List[page - 1]
        }
        if (a4List[page - 1] == "null") {
            answer4.visibility = View.INVISIBLE
        } else {
            answer4.text = a4List[page - 1]
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAnswer() {
        if (page == 1) {
            page = 2
            updateQuestion()
            question1.setTextColor(Color.WHITE)
            question2.setTextColor(Color.BLACK)
            if (answerIndex[1] != -1) {
                resetColor()
                when (answerIndex[1]) {
                    0 -> answer1.setBackgroundColor(Color.BLUE)
                    1 -> answer2.setBackgroundColor(Color.BLUE)
                    2 -> answer3.setBackgroundColor(Color.BLUE)
                    3 -> answer4.setBackgroundColor(Color.BLUE)
                }
            }
            else
            {
                resetColor()
            }
        }
        else if (page == 2)
        {
            page = 3
            updateQuestion()
            question2.setTextColor(Color.WHITE)
            question3.setTextColor(Color.BLACK)
            if(answerIndex[2] != -1)
            {
                resetColor()
                when(answerIndex[2]){
                    0->answer1.setBackgroundColor(Color.BLUE)
                    1->answer2.setBackgroundColor(Color.BLUE)
                    2->answer3.setBackgroundColor(Color.BLUE)
                    3->answer4.setBackgroundColor(Color.BLUE)
                }
            }
            else
            {
                resetColor()
            }
        }
        else if (page == 3)
        {
            page = 4
            updateQuestion()
            question3.setTextColor(Color.WHITE)
            question4.setTextColor(Color.BLACK)
            if(answerIndex[3] != -1)
            {
                resetColor()
                when(answerIndex[3]){
                    0->answer1.setBackgroundColor(Color.BLUE)
                    1->answer2.setBackgroundColor(Color.BLUE)
                    2->answer3.setBackgroundColor(Color.BLUE)
                    3->answer4.setBackgroundColor(Color.BLUE)
                }
            }
            else
            {
                resetColor()
            }
        }
        else if (page == 4)
        {
            for (i in answerIndex){
                if (i == -1){
                    return
                }
            }
            val intent = Intent(this, ResourceActivity::class.java)
            // store data
            for (i in (0..3))
            {
                val answer = Answer(LocalDate.now().toString(), questionArray[i], answerArray[i])
                answerList.add(answer)
            }
            sharedPreferences = getSharedPreferences("com.example.google_solution_challenge", MODE_PRIVATE)
            //before storing data, read them first
            var stored = ArrayList<Answer>()
            try {
                stored = ObjectSerializer.deserialize(
                    sharedPreferences
                        .getString("answers", ObjectSerializer.serialize(ArrayList<Answer>()))
                ) as ArrayList<Answer>
            } catch (e: Exception) {
                println("No data stored... proceeding")
            }
            answerList.addAll(stored)
            sharedPreferences.edit().putString("answers", ObjectSerializer.serialize(answerList))
                .apply()
            startActivity(intent)

        }
    }

    private fun resetColor() {
        answer1.setBackgroundColor(Color.parseColor("#F1B100"))
        answer2.setBackgroundColor(Color.parseColor("#F1B100"))
        answer3.setBackgroundColor(Color.parseColor("#F1B100"))
        answer4.setBackgroundColor(Color.parseColor("#F1B100"))
    }

    private fun changeQuestion(num: Int) {
        if (num == 1) {
            if(answerIndex[0] != -1 ) {
                question1.setTextColor(Color.BLACK)
                question2.setTextColor(Color.WHITE)
                question3.setTextColor(Color.WHITE)
                question4.setTextColor(Color.WHITE)
                page = 1
                updateQuestion()
                if (answerIndex[0] != -1) {
                    resetColor()
                    when (answerIndex[0]) {
                        0 -> answer1.setBackgroundColor(Color.BLUE)
                        1 -> answer2.setBackgroundColor(Color.BLUE)
                        2 -> answer3.setBackgroundColor(Color.BLUE)
                        3 -> answer4.setBackgroundColor(Color.BLUE)
                    }
                }
                else
                {
                    resetColor()
                }
            }
        } else if (num == 2) {
            if(answerIndex[1] != -1|| answerIndex[0] != -1 ) {
                question2.setTextColor(Color.BLACK)
                question1.setTextColor(Color.WHITE)
                question3.setTextColor(Color.WHITE)
                question4.setTextColor(Color.WHITE)
                page = 2
                updateQuestion()
                if (answerIndex[1] != -1) {
                    resetColor()
                    when (answerIndex[1]) {
                        0 -> answer1.setBackgroundColor(Color.BLUE)
                        1 -> answer2.setBackgroundColor(Color.BLUE)
                        2 -> answer3.setBackgroundColor(Color.BLUE)
                        3 -> answer4.setBackgroundColor(Color.BLUE)
                    }
                }
                else
                {
                    resetColor()
                }
            }
        } else if (num == 3) {
            if(answerIndex[2] != -1|| answerIndex[1] != -1 ) {
                question3.setTextColor(Color.BLACK)
                question1.setTextColor(Color.WHITE)
                question2.setTextColor(Color.WHITE)
                question4.setTextColor(Color.WHITE)
                page = 3
                updateQuestion()
                if (answerIndex[2] != -1) {
                    resetColor()
                    when (answerIndex[2]) {
                        0 -> answer1.setBackgroundColor(Color.BLUE)
                        1 -> answer2.setBackgroundColor(Color.BLUE)
                        2 -> answer3.setBackgroundColor(Color.BLUE)
                        3 -> answer4.setBackgroundColor(Color.BLUE)
                    }
                }
                else
                {
                    resetColor()
                }
            }
        }
        else if(num == 4)
        {
            if(answerIndex[3] != -1 || answerIndex[2] != -1) {
                question4.setTextColor(Color.BLACK)
                question1.setTextColor(Color.WHITE)
                question2.setTextColor(Color.WHITE)
                question3.setTextColor(Color.WHITE)
                page = 4
                updateQuestion()
                if (answerIndex[3] != -1) {
                    resetColor()
                    when (answerIndex[3]) {
                        0 -> answer1.setBackgroundColor(Color.BLUE)
                        1 -> answer2.setBackgroundColor(Color.BLUE)
                        2 -> answer3.setBackgroundColor(Color.BLUE)
                        3 -> answer4.setBackgroundColor(Color.BLUE)
                    }
                }
                else
                {
                    resetColor()
                }
            }
        }
    }

    private fun getQuestions(res: MutableList<String>) {
        val db = Firebase.firestore
        val daily = db.collection("daily-questions")
        val rotational = db.collection("rotational-questions")
        res.clear()

        daily.get().addOnSuccessListener { querySnapshot ->
            val questions = mutableListOf<String>()
            querySnapshot.forEach { document ->
                questions.add(document.data["Question"] as String)
            }
            res.addAll(questions.shuffled().take(2))
            Log.d("COLLECTION", questions.shuffled().take(2).toString())
            rotational.get().addOnSuccessListener { querySnapshot ->
                val questions = mutableListOf<String>()
                querySnapshot.forEach { document ->
                    questions.add(document.data["Question"] as String)
                }
                res.addAll(questions.shuffled().take(2))
            }
        }
    }
}