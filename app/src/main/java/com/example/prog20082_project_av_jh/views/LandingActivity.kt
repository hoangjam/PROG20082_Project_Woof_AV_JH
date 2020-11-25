package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.prog20082_project_av_jh.R
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(), View.OnClickListener {

    val TAG : String = this@LandingActivity.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == btnSignIn.id) {
                //TODO: Add validation
                Log.e(TAG, "SIGNIN CLICKED ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                this.goToMain()
            } else if (v.id == btnSignUp.id) {
                this.goToSignUp()
            }
        }
    }

    private fun goToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        this@LandingActivity.finishAffinity()
    }

    private fun goToSignUp() {
        val signUpIntent = Intent (this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }


}