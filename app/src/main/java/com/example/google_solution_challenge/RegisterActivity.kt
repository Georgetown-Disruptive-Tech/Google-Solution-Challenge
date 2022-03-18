package com.example.google_solution_challenge

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tapLogIn = findViewById<TextView>(R.id.tapLogIn)
        tapLogIn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        val emailReg = findViewById<EditText>(R.id.newEmail)
        val newPassword = findViewById<EditText>(R.id.enterPassword)
        val repeatPassword = findViewById<EditText>(R.id.repeatPassword)
        val btn = findViewById<Button>(R.id.registerButton)
        btn.setOnClickListener {
            if(newPassword.text != repeatPassword.text){
                Toast.makeText(
                    this@RegisterActivity,
                    "Password Mismatch",
                    Toast.LENGTH_SHORT
                ).show()
            }
            when{
                TextUtils.isEmpty(emailReg.text.toString().trim()) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(newPassword.text.toString().trim()) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = emailReg.text.toString().trim()
                    val password: String = newPassword.text.toString().trim()
                    registerUser(email, password)
                }
            }
        }



    }

    private fun registerUser(email: String ,password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    val intent = Intent(this, FirstActivity::class.java)
                    //TODO: pass in user data
                    startActivity(intent)
                }
                else{
                    Toast.makeText(
                        this@RegisterActivity,
                        task.exception!!.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }



}