package com.example.prog20082_project_av_jh.locationservices

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prog20082_project_av_jh.ui.MatchedProfileFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

/*
User: hoangjam
Name: Jameson Hoang
Student ID: 991548515
Date: 2020-11-25
*/
class LocationManager(var context: Context) {
    private val TAG = this.toString()
    var fusedLocationProviderClient: FusedLocationProviderClient ?= null
    private var location : MutableLiveData<Location> = MutableLiveData()
    lateinit var locationRequest: LocationRequest

    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 101
        var locationPermissionsGranted = false
    }

    init{
        checkPermissions()
        getLocationProviderClient()
        createLocationRequest()
    }

    fun getLocationProviderClient() : FusedLocationProviderClient{
        if (fusedLocationProviderClient == null){
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.context)
        }

        return fusedLocationProviderClient!!
    }

    fun getLastLocation() : LiveData<Location>?{
        if (locationPermissionsGranted){
            try{
                this.fusedLocationProviderClient!!.lastLocation
                    .addOnSuccessListener {loc: Location? ->
                        if (loc != null){
                            location.value = loc

                            Log.e(TAG, "Last Location : " + location.value.toString())
                        }
                    }
                    .addOnFailureListener { error ->
                        Log.e(TAG, "error : " + error.localizedMessage)
                    }
            }catch (ex: SecurityException){
                Log.e(TAG, "SecurityException : " + ex.localizedMessage)
            }
            return location
        }
        return null
    }

    private fun checkPermissions(){
        locationPermissionsGranted = (ContextCompat.checkSelfPermission(this.context.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)

        Log.e(TAG, "Location Permissions Granted " + locationPermissionsGranted.toString())

        if (!locationPermissionsGranted){
            this.requestLocationPermission()
        }
    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(this.context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE)
    }

    fun createLocationRequest(){
        this.locationRequest = LocationRequest()
        this.locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        this.locationRequest.interval = 12000
        this.locationRequest.fastestInterval = 30000
    }

    fun requestLocationUpdates(locationCallback: LocationCallback){
        if (locationPermissionsGranted){
            try{

                this.fusedLocationProviderClient?.requestLocationUpdates(
                    this.locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )

            }catch (ex: SecurityException){
                Log.e(TAG, "Error : " + ex.localizedMessage)
            }
        }
    }
}