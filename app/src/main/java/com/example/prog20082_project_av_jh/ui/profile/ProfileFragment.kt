package com.example.prog20082_project_av_jh.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.preferences.SharedPreferencesManager
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), View.OnClickListener {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var edtOwnerName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtPhoneNumber: EditText
    lateinit var edtDogName: EditText
    lateinit var selectedGender: String
    lateinit var edtBreed: EditText
    lateinit var edtAge: EditText
    lateinit var edtBio: EditText
    lateinit var fabSaveEdit: FloatingActionButton
    lateinit var fabEditProfile: FloatingActionButton
    lateinit var spnGender: Spinner

    lateinit var userViewModel: UserViewModel
    lateinit var existingUser: User
    var currentUserEmail = SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        userViewModel = UserViewModel(this.requireActivity().application)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        root.fabEditProfile.setOnClickListener(this)
        root.fabSaveEdit.setOnClickListener(this)

        edtOwnerName = root.edtOwnerName

        edtEmail = root.edtEmail
        edtPhoneNumber = root.edtPhoneNumber
        edtDogName = root.edtDogName

        selectedGender = root.spnGender.toString()

        edtBreed = root.edtBreed
        edtAge = root.edtAge
        edtBio = root.edtBio

        fabSaveEdit = root.fabSaveEdit
        fabEditProfile = root.fabEditProfile

        spnGender = root.spnGender
        this.initializeSpinner()
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

        this.populateProfile()

        this.disableEdit()

        return root

    }

    private fun initializeSpinner() {
        val genderAdapter = ArrayAdapter(this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.gender_array))

        spnGender.adapter = genderAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *

         * @return A new instance of fragment ProfileFragment.
         */
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM1, param2)
                }
            }
    }

    fun populateProfile() {
        Log.e("Profile Fragment", "Inside populateProfile")
        if (currentUserEmail != null) {
            Log.e("Profile Fragment", "Retrieved currentUserEmail")

            userViewModel.getUserByEmail(currentUserEmail!!)?.observe(this.requireActivity(), { matchedUser ->
                    if (matchedUser != null) {
                        Log.e("Profile Fragment", "Matched user check")

                        this.existingUser = matchedUser

                        Log.e("Profile Fragment", "Matched user : " + matchedUser.toString())

                        edtOwnerName.setText(matchedUser.oName)
                        edtEmail.setText(matchedUser.email)
                        edtPhoneNumber.setText(matchedUser.phoneNumber)
                        edtDogName.setText(matchedUser.dName)
                        this.spnGender.setSelection(resources.getStringArray(R.array.gender_array).indexOf(matchedUser.gender))
                        edtBreed.setText(matchedUser.breed)
                        edtAge.setText(matchedUser.age.toString() + " yrs")
                        edtBio.setText(matchedUser.bio)
                    }
                else{
                        Log.e("Profile Fragment", "Matched user is null")
                    }
                })
        } else {
            Log.e("Profile Fragment", "Couldn't retrieve user email")
        }
    }

    fun enableEdit() {
        fabEditProfile.visibility = View.GONE
        fabSaveEdit.visibility = View.VISIBLE
        edtOwnerName.isEnabled = true
        edtEmail.isEnabled = true
        edtPhoneNumber.isEnabled = true
        edtDogName.isEnabled = true
        spnGender.isEnabled = true
        edtBreed.isEnabled = true
        edtAge.isEnabled = true
        edtBio.isEnabled = true

        spnGender.setAlpha(1.0F)
        edtAge.setText(edtAge.text.dropLast(4).toString())
    }

    fun disableEdit() {
        fabEditProfile.visibility = View.VISIBLE
        fabSaveEdit.visibility = View.GONE
        edtOwnerName.isEnabled = false
        edtEmail.isEnabled = false
        edtPhoneNumber.isEnabled = false
        edtDogName.isEnabled = false
        spnGender.isEnabled = false
        edtBreed.isEnabled = false
        edtAge.isEnabled = false
        edtBio.isEnabled = false

        spnGender.setAlpha(0.5F)
        edtAge.setText(edtAge.text.toString() + " yrs")
    }

    private fun saveToDB() {
        this.existingUser.oName = edtOwnerName.text.toString()
        this.existingUser.email = edtEmail.text.toString()
        this.existingUser.phoneNumber = edtPhoneNumber.text.toString()

        this.existingUser.dName = edtDogName.text.toString()
        this.existingUser.gender = spnGender.selectedItem.toString()
        this.existingUser.breed = edtBreed.text.toString()
        this.existingUser.age = edtAge.text.toString().dropLast(4).toInt()
        this.existingUser.bio = edtBio.text.toString()

        //if email was changed, change shared preferences too
        if (this.existingUser.email.equals(SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, ""))) {
            SharedPreferencesManager.write(SharedPreferencesManager.EMAIL, this.existingUser.email)
        }

        try {
            userViewModel.updateUser(existingUser)
            Toast.makeText(this.requireContext(), "Changes saved successfully.", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.d("Profile Fragment", ex.localizedMessage)
            Toast.makeText(this.requireContext(), "Save unsuccessful; changes unsaved.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            fabEditProfile.id -> {
                this.enableEdit()
            }
            fabSaveEdit.id -> {
                this.disableEdit()
                this.saveToDB()
            }
        }
    }
}