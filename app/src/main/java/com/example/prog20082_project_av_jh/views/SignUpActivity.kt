package com.example.prog20082_project_av_jh.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    val TAG: String = this@SignUpActivity.toString()
    var selectedGender: String = ""

    companion object {
        var user = User()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        this.initialSetup()
        this.initializeSpinner()
        selectedGender = resources.getStringArray(R.array.gender_array).get(0)

        spnGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                selectedGender = resources.getStringArray(R.array.gender_array).get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedGender = resources.getStringArray(R.array.gender_array).get(2)
            }
        }
        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                btnSignUp.id ->{
                    goToMain()
                }
            }
        }
    }

    fun initializeSpinner() {
        val genderAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.gender_array))

        spnGender.adapter = genderAdapter
    }

    fun initialSetup() {
        edtOwnerName.setAutofillHints(View.AUTOFILL_HINT_NAME)
        edtEmail.setAutofillHints(View.AUTOFILL_HINT_EMAIL_ADDRESS)
        edtPhoneNumber.setAutofillHints(View.AUTOFILL_HINT_PHONE)
        edtPassword.setAutofillHints(View.AUTOFILL_HINT_PASSWORD)
        edtDogName.setAutofillHints(View.AUTOFILL_HINT_NAME)
    }

    private fun goToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        this@SignUpActivity.finishAffinity()
    }

}