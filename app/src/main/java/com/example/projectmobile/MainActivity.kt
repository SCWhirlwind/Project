package com.example.projectmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_page.*



class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var email = ""
    private var password = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)


        loginButton.setOnClickListener(this)
        createNewAccountTextLink.setOnClickListener(this)
        auth = Firebase.auth
    }

    override fun onClick(p0: View?) {
        when (p0)
        {
            loginButton -> {
                email = emailInputField.text.toString()
                password = passwordInputField.text.toString()
                if (email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                }
                else if (!email.contains("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$".toRegex()))
                {
                    Toast.makeText(this, "Please enter email correctly", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    login(email, password)
                }
            }
            createNewAccountTextLink -> {
                goToCreateAccount()
            }
        }
    }

    private fun goToMenu() {
        val intent = Intent(this, MenuPage::class.java)
        startActivity(intent)
    }

    private fun goToCreateAccount() {
        val intent = Intent(this, CreateAccountPage::class.java)
        startActivity(intent)
    }

    private fun login(loginEmail: String, loginPassword: String) {
        auth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful) {
                goToMenu()
            } else {
                Toast.makeText(this, "Email or password is invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}