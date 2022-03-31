package com.example.google_solution_challenge

//import com.google.firebase.quickstart.auth.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val tapRegister = findViewById<TextView>(R.id.tapRegister)
        tapRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val btnLogIn = findViewById<Button>(R.id.logInButton)
        val emailLogIn = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordLogIn = findViewById<EditText>(R.id.editTextTextPassword)
        btnLogIn.setOnClickListener{
            when {
                TextUtils.isEmpty(emailLogIn.text.toString().trim()) -> {
                    Toast.makeText(
                        this@LogInActivity,
                        "Please enter email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(passwordLogIn.text.toString().trim()) -> {
                    Toast.makeText(
                        this@LogInActivity,
                        "Please enter password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    // TODO: log in process
                    val email: String = emailLogIn.text.toString().trim()
                    val password: String = passwordLogIn.text.toString().trim()
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener{
                            task ->
                            if(task.isSuccessful){
                                val intent = Intent(this, UniversityActivity::class.java)
                                //TODO: pass in user data
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(
                                    this@LogInActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                }
            }
        }

    }

}