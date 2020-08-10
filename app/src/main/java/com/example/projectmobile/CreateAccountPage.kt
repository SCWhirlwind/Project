package com.example.projectmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.create_account_page.*

private var username : String = ""
private var email : String = ""
private var password : String = ""
private var confirmPassword : String = ""
private lateinit var auth: FirebaseAuth

class CreateAccountPage : AppCompatActivity(),  View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account_page)

        createAccountButton.setOnClickListener(this)
        loginAccountTextLink.setOnClickListener(this)

        auth = Firebase.auth

    }

    override fun onClick(p0: View?) {
        when (p0)
        {
            createAccountButton -> {
                username = usernameNewAccountInputField.text.toString()
                email = emailNewAccountInputField.text.toString()
                password = passwordNewAccountInputField.text.toString()
                confirmPassword = confirmPasswordNewAccountInputField.text.toString()
                if (username.isEmpty())
                {
                    Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
                }
                else if (email.isEmpty())
                {
                    Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
                }
                else if (!email.contains("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$".toRegex())) {
                    Toast.makeText(this, "Please enter an email correctly", Toast.LENGTH_SHORT).show()
                }
                else if (password.isEmpty())
                {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                }
                else if (password.length < 5) {
                    Toast.makeText(this, "Password needs to be at least 6 characters", Toast.LENGTH_SHORT).show()
                }
                else {
                    createAccount(username, email, password)
                }
            }
            loginAccountTextLink -> {
                finish()
            }
        }
    }

    private fun createAccount(username : String, email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful)
            {
                val ref = FirebaseDatabase.getInstance().getReference("user")
                val userId = ref.push().key
                val likes : List<Int> = emptyList()
                val user = User(userId, username, email, password, likes)

                if (userId != null) {
                    ref.child(userId).setValue(user).addOnCompleteListener {
                        Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(this, "Unable to create User", Toast.LENGTH_SHORT).show()
                }

                goToMenu()
            }
            else
            {
                Toast.makeText(this, "Email is already in use", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToMenu() {
        val intent = Intent(this, MenuPage::class.java)
        startActivity(intent)
    }
}