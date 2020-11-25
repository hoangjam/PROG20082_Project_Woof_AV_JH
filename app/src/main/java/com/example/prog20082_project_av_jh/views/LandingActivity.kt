package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
//import androidx.lifecycle.ViewModel
import com.example.prog20082_project_av_jh.R

class LandingActivity : AppCompatActivity(), View.OnClickListener {

    val TAG: String = this@LandingActivity.toString()

    lateinit var btnSignUp: Button
//    lateinit var userViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignUp.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if(v != null){
            if(v.id == btnSignUp.id){
                this.goToSignUp()
            }
            // add in signin to go to the swiping card fragments
        }
    }

    private fun goToSignUp(){
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }
}