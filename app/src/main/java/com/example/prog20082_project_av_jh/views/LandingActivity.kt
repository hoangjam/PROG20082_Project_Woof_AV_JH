package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel

class LandingActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = this@LandingActivity.toString()

    lateinit var btnSignUp : Button
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignUp.setOnClickListener(this)

        userViewModel = UserViewModel(this.application)
    }

    override fun onClick(v: View?){
        if(v != null){
            if(v.id == btnSignUp.id){
                this.goToSignUp()
            }
        }
    }

    private fun goToSignUp(){
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }
}