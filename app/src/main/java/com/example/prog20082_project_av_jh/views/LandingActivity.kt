package com.example.prog20082_project_av_jh.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.preferences.SharedPreferencesManager
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_landing.*
import java.lang.Exception

class LandingActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    val TAG: String = this@LandingActivity.toString()
    lateinit var userViewModel: UserViewModel
//    lateinit var rememberMeSwitch : Switch
    private var rememberMe = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
        swRememberMe.setOnCheckedChangeListener(this)

        SharedPreferencesManager.init(applicationContext)

        userViewModel = UserViewModel(this.application)
        this.fetchAllUsers()

        //check if saved preferences contains a password, if so remember me was checked so log in user automatically
        if (!SharedPreferencesManager.read(SharedPreferencesManager.PASSWORD, "").equals("")) {
            this.populateAndLogin()
        }
        
    }

    private fun populateAndLogin() {

        edtEmail.setText(SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, ""))
        edtPassword.setText(SharedPreferencesManager.read(SharedPreferencesManager.PASSWORD, ""))

        if (this.validateData()){
            this.validateUser()
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        rememberMe = isChecked
    }

    private fun savePreferences(){
        SharedPreferencesManager.write(SharedPreferencesManager.EMAIL, edtEmail.text.toString())

        if (rememberMe) {
            SharedPreferencesManager.write(
                SharedPreferencesManager.PASSWORD,
                edtPassword.text.toString()
            )
        }
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
                    "Samara is beagle mutt rescue. She's gots lots of energy and just wants to chase a ball (or her doggy playmate) around!",
                    25,
                    43.467518,
                    -79.687668
                ))

            this.userViewModel.insertAll(
                User(
                    "Jameson",
                    "jh@jh.com",
                    "2342342345",
                    "123",
                    "Rosco",
                    "Male",
                    "Great Dane",
                    8,
                    "Good boy who enjoys long walks and sunbathing at the park",
                    55,
                    43.589046,
                    -79.644119
                ))

            this.userViewModel.insertAll(
                User(
                    "Moathe",
                    "me@me.com",
                    "3453453456",
                    "123",
                    "Hera",
                    "Female",
                    "Whippet",
                    2,
                    "Hera is a speed demon for about a few minutes before she collapses on the couch for cuddles!",
                    13,
                    43.836338,
                    -79.874481
                ))

            this.userViewModel.insertAll(
                User(
                    "Javheria",
                    "ji@ji.com",
                    "4564564567",
                    "123",
                    "Nala",
                    "Female",
                    "Yorkie",
                    0,
                    "Nala is a tiny puppy, only a few months old! She would do best with other smaller or calm dogs so she doesn't get scared.",
                    2,
                    43.642567,
                    -79.387054
                ))

            this.userViewModel.insertAll(
                User(
                    "Trisha",
                    "tp@tp.com",
                    "5675675678",
                    "123",
                    "Hunter",
                    "Male",
                    "Bulldog",
                    7,
                    "Hunter may look mean, but he's just a big lovebug. He loves to play tug of war and wrestle with his friends.",
                    42,
                    43.6205,
                    79.5132
                ))
        }catch (ex: Exception){
            Log.e(TAG, ex.toString())
            Log.e(TAG, ex.localizedMessage)
        }


    }



}