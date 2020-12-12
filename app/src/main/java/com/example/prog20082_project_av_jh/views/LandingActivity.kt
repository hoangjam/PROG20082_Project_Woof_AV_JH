package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.preferences.SharedPreferencesManager
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_landing.*
import java.lang.Exception

class LandingActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = this@LandingActivity.toString()
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)

        SharedPreferencesManager.init(applicationContext)

        userViewModel = UserViewModel(this.application)
        this.fetchAllUsers()
        
    }

    private fun savePreferences(){
        SharedPreferencesManager.write(SharedPreferencesManager.EMAIL, edtEmail.text.toString())

        Log.e("SHARED PREFERENCES MANAGER", SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, "").toString())

        SharedPreferencesManager.write(SharedPreferencesManager.PASSWORD, edtPassword.text.toString())
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == btnSignIn.id) {
                Log.e(TAG, "SIGNIN CLICKED +++++++++++++++++++++++++++++++++++++++++++++++++")
                if (this.validateData()) {
                    Log.e(TAG, "--------- INSIDE ONCLICK() ---------")
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
        Log.e(TAG, "--------- INSIDE VALIDATEUSER() ---------")

        userViewModel.getUserByLoginInfo(email, password)?.observe(this@LandingActivity, {matchedUser ->
            if(matchedUser != null){
                this.savePreferences()
                Toast.makeText(this, "Signing in", Toast.LENGTH_SHORT).show()
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
        //if empty seed DB with demo data
        userViewModel.allUsers.observe(this@LandingActivity, {
            for (user in it) {
                Log.e(TAG, user.toString())
            }

            if (it.isNullOrEmpty()) {
                Log.e(TAG, "Empty db, seeding....")
                this.seedDemoData()
            }
        })
    }

    private fun seedDemoData() {

        try{
            this.userViewModel.insertAll(
                User(
                    "Angela",
                    "av@av.com",
                    "1231231234",
                    "123",
                    "Samara",
                    "Female",
                    "Beagle",
                    4,
                    "fren",
                    15
                ))

            this.userViewModel.insertAll(
                User(
                    "Jameson",
                    "jh@jh.com",
                    "2342342345",
                    "123",
                    "forget",
                    "Male",
                    "Good boyo",
                    5,
                    "a good boy",
                    25
                ))

            this.userViewModel.insertAll(
                User(
                    "Moathe",
                    "me@me.com",
                    "3453453456",
                    "123",
                    "Hera",
                    "Female",
                    "cat",
                    4,
                    "actually cat but still good",
                    9
                ))

            this.userViewModel.insertAll(
                User(
                    "Javheria",
                    "ji@ji.com",
                    "4564564567",
                    "123",
                    "Nala",
                    "Female",
                    "Cat",
                    0,
                    "smol bean",
                    2
                ))

            this.userViewModel.insertAll(
                User(
                    "Trisha",
                    "tp@tp.com",
                    "5675675678",
                    "123",
                    "Ethan",
                    "Male",
                    "Unkown",
                    12,
                    "big boy",
                    54
                ))
        }catch (ex: Exception){
            Log.e(TAG, ex.toString())
            Log.e(TAG, ex.localizedMessage)
        }


    }



}