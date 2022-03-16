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
        return view
    }

    fun onAnswer()
    {
        if(page == 1){
            page = 2
            question1.setTextColor(Color.WHITE)
            question2.setTextColor(Color.BLACK)

        }
        else if (page == 2)
        {
            page = 3
            question2.setTextColor(Color.WHITE)
            question3.setTextColor(Color.BLACK)
        }
        else if (page == 3)
        {
            page = 4
            question3.setTextColor(Color.WHITE)
            question4.setTextColor(Color.BLACK)
        }
    }
    fun changeQuestion(view: View){
        if(view.id == R.id.answer1)
        {
            if( page != 1){
                page = 1
                if(answerArray[0] != -1)
                {
                    when(answerArray[0]){
                        0->answer1.setBackgroundColor(Color.BLUE)
                        1->answer2.setBackgroundColor(Color.BLUE)
                        2->answer3.setBackgroundColor(Color.BLUE)
                        3->answer4.setBackgroundColor(Color.BLUE)
                    }
                }
            }
        }
        else if(view.id == R.id.answer2)
        {
            if(page >= 2)
            {
                page = 2
                if(answerArray[1] != -1)
                {
                    when(answerArray[1]){
                        0->answer1.setBackgroundColor(Color.BLUE)
                        1->answer2.setBackgroundColor(Color.BLUE)
                        2->answer3.setBackgroundColor(Color.BLUE)
                        3->answer4.setBackgroundColor(Color.BLUE)
                    }
                }
            }
        }
        else if(view.id == R.id.answer3)
        {
            if(page >= 3)
            {
                page = 3
                if(answerArray[2] != -1)
                {
                    when(answerArray[2]){
                        0->answer1.setBackgroundColor(Color.BLUE)
                        1->answer2.setBackgroundColor(Color.BLUE)
                        2->answer3.setBackgroundColor(Color.BLUE)
                        3->answer4.setBackgroundColor(Color.BLUE)
                    }
                }
            }
        }


    }

}