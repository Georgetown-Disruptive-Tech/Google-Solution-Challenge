package com.example.google_solution_challenge

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random


class QuestionFragment : Fragment() {
    var page = 1
    var answerArray = arrayOf(-1, -1, -1, -1)
    lateinit var answer1 : Button
    lateinit var answer2 : Button
    lateinit var answer3 : Button
    lateinit var answer4 : Button
    lateinit var question1 : Button
    lateinit var question2 : Button
    lateinit var question3 : Button
    lateinit var question4 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        val daily = db.collection("daily-questions")
        val rotational = db.collection("rotational-questions")
        val triggered = db.collection("triggered-questions")
        val q = getRandomQuestion(daily)
        // TODO: retrieve and display the questions
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_question, container, false)
        answer1 = view.findViewById(R.id.answer1)
        answer2 = view.findViewById(R.id.answer2)
        answer3 = view.findViewById(R.id.answer3)
        answer4 = view.findViewById(R.id.answer4)
        question1 = view.findViewById(R.id.question1)
        question2 = view.findViewById(R.id.question2)
        question3 = view.findViewById(R.id.question3)
        question4 = view.findViewById(R.id.question4)
        question1.setTextColor(Color.BLACK)
        answer1.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                when (page){
                    1-> answerArray[0] = 0
                    2->answerArray[1] = 0
                    3->answerArray[2] = 0
                    4->answerArray[3] = 0
                }
                onAnswer()
            }
        })
        answer2.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {

                when (page){
                    1-> answerArray[0] = 1
                    2->answerArray[1] = 1
                    3->answerArray[2] = 1
                    4->answerArray[3] = 1
                }
                onAnswer()
            }
        })
        answer3.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                when (page){
                    1-> answerArray[0] = 2
                    2->answerArray[1] = 2
                    3->answerArray[2] = 2
                    4->answerArray[3] = 2
                }
                onAnswer()
            }
        })
        answer4.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                when (page){
                    1-> answerArray[0] = 3
                    2->answerArray[1] = 3
                    3->answerArray[2] = 3
                    4->answerArray[3] = 3
                }
                onAnswer()
            }
        })
        question1.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                changeQuestion(1)
            }
        })
        question2.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                changeQuestion(2)
            }
        })
        question3.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                changeQuestion(3)
            }
        })
        question4.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                changeQuestion(4)
            }
        })
        return view
    }

    fun onAnswer()
    {
        if(page == 1){
            page = 2
            question1.setTextColor(Color.WHITE)
            question2.setTextColor(Color.BLACK)
            if(answerArray[1] != -1)
            {
                resetColor()
                when(answerArray[1]){
                    0->answer1.setBackgroundColor(Color.BLUE)
                    1->answer2.setBackgroundColor(Color.BLUE)
                    2->answer3.setBackgroundColor(Color.BLUE)
                    3->answer4.setBackgroundColor(Color.BLUE)
                }
            }
        }
        else if (page == 2)
        {
            page = 3
            question2.setTextColor(Color.WHITE)
            question3.setTextColor(Color.BLACK)
            if(answerArray[2] != -1)
            {
                resetColor()
                when(answerArray[2]){
                    0->answer1.setBackgroundColor(Color.BLUE)
                    1->answer2.setBackgroundColor(Color.BLUE)
                    2->answer3.setBackgroundColor(Color.BLUE)
                    3->answer4.setBackgroundColor(Color.BLUE)
                }
            }
        }
        else if (page == 3)
        {
            page = 4
            question3.setTextColor(Color.WHITE)
            question4.setTextColor(Color.BLACK)
            if(answerArray[3] != -1)
            {
                resetColor()
                when(answerArray[3]){
                    0->answer1.setBackgroundColor(Color.BLUE)
                    1->answer2.setBackgroundColor(Color.BLUE)
                    2->answer3.setBackgroundColor(Color.BLUE)
                    3->answer4.setBackgroundColor(Color.BLUE)
                }
            }
        }
    }
    fun resetColor(){
        answer1.setBackgroundColor(Color.parseColor("#F1B100"))
        answer2.setBackgroundColor(Color.parseColor("#F1B100"))
        answer3.setBackgroundColor(Color.parseColor("#F1B100"))
        answer4.setBackgroundColor(Color.parseColor("#F1B100"))
    }
    fun changeQuestion(num: Int){
        System.out.println("lol")
        if(num == 1)
        {
                question1.setTextColor(Color.BLACK)
                question2.setTextColor(Color.WHITE)
                question3.setTextColor(Color.WHITE)
                question4.setTextColor(Color.WHITE)
                page = 1
                if(answerArray[0] != -1)
                {
                    resetColor()
                    when(answerArray[0]){
                        0->answer1.setBackgroundColor(Color.BLUE)
                        1->answer2.setBackgroundColor(Color.BLUE)
                        2->answer3.setBackgroundColor(Color.BLUE)
                        3->answer4.setBackgroundColor(Color.BLUE)
                    }
                }
        }
        else if(num == 2)
        {

                question2.setTextColor(Color.BLACK)
                question1.setTextColor(Color.WHITE)
                question3.setTextColor(Color.WHITE)
                question4.setTextColor(Color.WHITE)
                page = 2
                if(answerArray[1] != -1)
                {
                    resetColor()
                    when(answerArray[1]){
                        0->answer1.setBackgroundColor(Color.BLUE)
                        1->answer2.setBackgroundColor(Color.BLUE)
                        2->answer3.setBackgroundColor(Color.BLUE)
                        3->answer4.setBackgroundColor(Color.BLUE)
                    }
                }
        }
        else if(num == 3)
        {

                question3.setTextColor(Color.BLACK)
                question1.setTextColor(Color.WHITE)
                question2.setTextColor(Color.WHITE)
                question4.setTextColor(Color.WHITE)
                page = 3
                if(answerArray[2] != -1)
                {
                    resetColor()
                    when(answerArray[2]){
                        0->answer1.setBackgroundColor(Color.BLUE)
                        1->answer2.setBackgroundColor(Color.BLUE)
                        2->answer3.setBackgroundColor(Color.BLUE)
                        3->answer4.setBackgroundColor(Color.BLUE)
                    }
                }
        }
        else if(num == 4)
        {

            question4.setTextColor(Color.BLACK)
            question1.setTextColor(Color.WHITE)
            question2.setTextColor(Color.WHITE)
            question3.setTextColor(Color.WHITE)
            page = 4
            if(answerArray[3] != -1)
            {
                resetColor()
                when(answerArray[3]){
                    0->answer1.setBackgroundColor(Color.BLUE)
                    1->answer2.setBackgroundColor(Color.BLUE)
                    2->answer3.setBackgroundColor(Color.BLUE)
                    3->answer4.setBackgroundColor(Color.BLUE)
                }
            }
        }


    }
    private fun getRandomQuestion(collection: CollectionReference): QueryDocumentSnapshot? {
        val key = collection.document().id
        var doc : QueryDocumentSnapshot? = null
        val ls = collection.get()
            .addOnSuccessListener { querySnapshot ->
                val idx = Random.nextInt() % querySnapshot.size()
                var i = 0
                for(document in querySnapshot){
                    if(i == idx){
                        doc = document
                        break
                    }
                    i += 1
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    activity,
                    "Error getting documents $exception",
                    Toast.LENGTH_SHORT
                ).show()
            }
        return doc
    }
}