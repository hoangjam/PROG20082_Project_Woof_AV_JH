package com.example.prog20082_project_av_jh.views

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.locationservices.LocationManager
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.lang.Exception

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    val TAG: String = this@SignUpActivity.toString()
    var selectedGender: String = ""
    private var lat: Double = 0.0
    private var lng: Double = 0.0

    private lateinit var locationManager: LocationManager
    private lateinit var location: Location
    private lateinit var locationCallback: LocationCallback

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

        this.locationManager = LocationManager(this@SignUpActivity)
        this.lat = 128.0
        this.lng = 64.0

        if(LocationManager.locationPermissionsGranted){
            locationManager.getLastLocation()
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                for (location in locationResult.locations) {
                    lat = location.latitude
                    lng = location.longitude
                }
            }
        }
        locationManager.requestLocationUpdates(locationCallback)
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                btnSignUp.id ->{
                    if(this.validateData()){
                        this.fetchData(lat, lng)
                        this.saveUserToDB()
                        this.goToMain()
                    }
                }
            }
        }
    }

    fun saveUserToDB(){
        try{
            var userViewModel = UserViewModel(this.application)
            userViewModel.insertAll(user)
        }catch (ex: Exception){
            Log.e(TAG, ex.toString())
            Log.e(TAG, ex.localizedMessage)
        }
    }

    fun fetchData(lat: Double, lng: Double){
        user.oName = edtOwnerName.text.toString()
        user.email = edtEmail.text.toString()
        user.phoneNumber = edtPhoneNumber.text.toString()
        user.password = edtPassword.text.toString()
        user.dName = edtDogName.text.toString()
        user.gender = selectedGender
        user.breed = edtBreed.text.toString()
        user.age = edtAge.text.toString().toInt()
        user.bio = edtBio.text.toString()

        user.lat = lat
        user.lng = lng

        Log.d(TAG, "User : " + user.toString())
    }

    fun validateData() : Boolean{
        if(edtOwnerName.text.toString().isEmpty()){
            edtOwnerName.error = "Please enter your name"
            return false
        }
        if(edtEmail.text.toString().isEmpty()){
            edtEmail.error = "Please enter your name"
            return false
        }
        if(edtPhoneNumber.text.toString().isEmpty()){
            edtPhoneNumber.error = "Please enter your phone number"
            return false
        }
        if(edtPassword.text.toString().isEmpty()){
            edtPassword.error = "Please enter a password"
            return false
        }
        if(edtConfirmPassword.text.toString().isEmpty() || edtConfirmPassword.text.toString() != edtPassword.text.toString()){
            edtConfirmPassword.error = "Password must match"
            return false
        }
        if(edtDogName.text.toString().isEmpty()){
            edtDogName.error = "Please enter doge name"
            return false
        }
        if(edtBreed.text.toString().isEmpty()){
            edtBreed.error = "Please enter dog breed"
            return false
        }
        if(edtAge.text.toString().isEmpty()){
            edtAge.error = "Please enter dog age"
            return false
        }
        return true
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