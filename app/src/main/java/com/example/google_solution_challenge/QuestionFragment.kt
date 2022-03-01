package com.example.google_solution_challenge

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class QuestionFragment : Fragment() {
    var page = 1;
    lateinit var answer1 : Button
    lateinit var answer2 : Button
    lateinit var answer3 : Button
    lateinit var answer4 : Button
    lateinit var question1 : TextView
    lateinit var question2 : TextView
    lateinit var question3 : TextView
    lateinit var question4 : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        question1.setTextColor(Color.WHITE)
        answer1.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onAnswer()
            }
        })
        answer2.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onAnswer()
            }
        })
        answer3.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onAnswer()
            }
        })
        answer4.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onAnswer()
            }
        })
        return view
    }

    fun onAnswer()
    {
        if(page == 1){
            page = 2
            question1.setTextColor(Color.BLACK)
            question2.setTextColor(Color.WHITE)

        }
        else if (page == 2)
        {
            page = 3
            question2.setTextColor(Color.BLACK)
            question3.setTextColor(Color.WHITE)
        }
        else if (page == 3)
        {
            page = 4
            question3.setTextColor(Color.BLACK)
            question4.setTextColor(Color.WHITE)
        }
    }

}