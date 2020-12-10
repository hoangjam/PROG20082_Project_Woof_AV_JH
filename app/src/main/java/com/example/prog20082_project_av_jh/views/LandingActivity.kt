package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = this@LandingActivity.toString()
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)

        userViewModel = UserViewModel(this.application)

        this.fetchAllUsers()
        
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == btnSignIn.id) {
                Log.e(TAG, "SIGNIN CLICKED +++++++++++++++++++++++++++++++++++++++++++++++++")
                if (this.validateData()) {
                    println("inside validate data")
                    this.validateUser()
                }
            } else if (v.id == btnSignUp.id) {
                this.goToSignUp()
            }
        }
    }

    private fun validateUser(){
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        println("inside validate user")

        userViewModel.getUserByLoginInfo(email, password)?.observe(this@LandingActivity, {matchedUser ->
            if(matchedUser != null){
                println("inside matched user")
                this@LandingActivity.finishAndRemoveTask()
                this.goToMain()
            }
            else{
                Toast.makeText(this, "Incorrect email/password. Please try again.",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateData(): Boolean {
        if (edtEmail.text.isEmpty()) {
            edtEmail.error = "Email cannot be empty"
            return false
        }
        if (edtPassword.text.isEmpty()) {
            edtPassword.error = "Password cannot be empty"
            return false
        }
        return true
    }

    private fun goToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        this@LandingActivity.finishAffinity()
    }

    private fun goToSignUp() {
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }

    private fun fetchAllUsers() {
        userViewModel.allUsers.observe(this@LandingActivity, {
            for (user in it) {
                Log.e(TAG, user.toString())
            }
        })
    }

}