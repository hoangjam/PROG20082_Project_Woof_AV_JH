package com.example.prog20082_project_av_jh.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.prog20082_project_av_jh.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_matched_profile.view.*
import com.example.prog20082_project_av_jh.locationservices.LocationManager
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import com.example.prog20082_project_av_jh.views.DisplayMapActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.fragment_matched_profile.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MatchedProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchedProfileFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var fabToMap : FloatingActionButton
    private val TAG = this@MatchedProfileFragment.toString()
    private var matchedEmail = ""
    private lateinit var matchedUser: User
    private lateinit var userViewModel: UserViewModel

    private lateinit var tvDogName: TextView
    private lateinit var tvDogAge: TextView
    private lateinit var tvDogBreed: TextView
    private lateinit var tvDogSize: TextView
    private lateinit var tvDogGender: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvOwnerName: TextView
    private lateinit var btnCall: Button

    private lateinit var matchedPhoneHolder: TextView

    val REQUEST_CALL = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_matched_profile, container, false)

        matchedPhoneHolder = view.hiddenEmail

        var bundle = this.arguments

        if (bundle != null) {
            this.matchedEmail = bundle.get("matchedEmail").toString()
            matchedPhoneHolder.setText(this.matchedEmail)
//            Toast.makeText(this.requireContext(), this.matchedEmail, Toast.LENGTH_SHORT).show()
        } else {
            Log.e(TAG, "bundle not recieved...")
        }

        tvDogName = view.tvDogName
        tvDogAge = view.tvDogAge
        tvDogBreed = view.tvBreed
        tvDogSize = view.tvDogSize
        tvDogGender = view.tvDogGender
        tvBio = view.tvBio
        tvOwnerName = view.tvOwnerName
        btnCall = view.btnCall
        btnCall.setOnClickListener(this)

        userViewModel = UserViewModel(requireActivity().application)

        if (!matchedEmail.isNullOrEmpty()) {
            this.getMatchedUser()
        }

        //Log.e(TAG, matchedUser.toString())



        fabToMap = view.fabLocation
        fabToMap.setOnClickListener(this)

        return view
    }

    private fun getMatchedUser() {
        this.userViewModel.allUsers.observe(viewLifecycleOwner, {users ->
            if (users != null) {
                for (user in users) {
                    if (user != null && user.email.equals(this.matchedEmail)) {
                        matchedUser = user
                        populateProfile(user)
                    }
                }
            }

        })
    }

    private fun populateProfile(mUser: User) {
        tvDogName.setText(mUser.dName)
        tvDogAge.setText(mUser.age.toString())
        tvDogGender.setText(mUser.gender)
        tvDogBreed.setText(mUser.breed)
        tvDogSize.setText(mUser.dogSize.toString())
        tvBio.setText(mUser.bio)
        tvOwnerName.setText(mUser.oName)
        matchedPhoneHolder.setText(mUser.phoneNumber)
    }

    override fun onClick(v: View?) {
        if (v != null){
            when (v.id) {
                fabToMap.id -> {
                    Log.e(TAG, "LOCATION BUTTON CLICKED")
                    val intent = Intent(activity, DisplayMapActivity::class.java)
                    startActivity(intent)
                }
                btnCall.id -> {
                    this.makeCall()
                }
            }
        }
    }

    private fun makeCall() {

        val phoneNumber = matchedPhoneHolder.text.toString()
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:" + phoneNumber)
        }

        if (checkPermission()) {
            if (callIntent.resolveActivity(this.requireActivity().packageManager) != null) {
                startActivity(callIntent)
            }
        } else {
            this.requestPermission()
        }

    }

    private fun checkPermission() : Boolean {
        return (ContextCompat.checkSelfPermission(this.requireContext().applicationContext, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL)
        this.makeCall()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MatchedProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MatchedProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}