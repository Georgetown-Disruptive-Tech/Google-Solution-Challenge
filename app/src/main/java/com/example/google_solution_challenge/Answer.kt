package com.example.hw5

import java.io.Serializable

class Answer(var date : String, var question: String, var answer: String): Serializable{
    override fun toString(): String {
        return "$question: \n$answer"
    }

    fun export(): String {
        return "$date - $question: \n$answer"
    }
}