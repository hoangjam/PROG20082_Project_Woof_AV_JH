package com.example.prog20082_project_av_jh.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.preferences.SharedPreferencesManager
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.spnGender
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.lang.Exception
import java.text.SimpleDateFormat

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
    lateinit var btnSave: Button
    lateinit var fabEditProfile: FloatingActionButton

    lateinit var userViewModel: UserViewModel
    lateinit var existingUser: User
    var currentUserEmail = SharedPreferencesManager.read(SharedPreferencesManager.EMAIL, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        userViewModel = UserViewModel(this.requireActivity().application)
        selectedGender = resources.getStringArray(R.array.gender_array).get(0)

        spnGender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        root.fabEditProfile.setOnClickListener(this)
        root.btnSave.setOnClickListener(this)
        root.spnGender.setOnClickListener(this)

        edtOwnerName = root.edtOwnerName
        edtEmail = root.edtEmail
        edtPhoneNumber = root.edtPhoneNumber
        edtDogName = root.edtDogName
        selectedGender = root.spnGender.toString()
        edtBreed = root.edtBreed
        edtAge = root.edtAge
        edtBio = root.edtBio
        btnSave = root.btnSave
        fabEditProfile = root.fabEditProfile

        this.disableEdit()

        return root

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

    fun populateProfile(){
        if (currentUserEmail != null){
            userViewModel.getUserByEmail(currentUserEmail!!)?.observe(this.requireActivity(), {matchedUser ->

                if (matchedUser != null) {

                    this.existingUser = matchedUser

                    Log.d("Profile Fragment", "Matched user : " + matchedUser.toString())

                    edtOwnerName.setText(matchedUser.oName)
                    edtEmail.setText(matchedUser.email)
                    edtPhoneNumber.setText(matchedUser.phoneNumber)
                    edtDogName.setText(matchedUser.dName)
                    spnGender.defaultFocusHighlightEnabled
                    edtBreed.setText(matchedUser.breed)
                    edtAge.setText(matchedUser.age.toString())
                    edtBio.setText(matchedUser.bio)
                }
            })
        }
    }

    fun enableEdit(){
        edtOwnerName.isEnabled = true
        edtEmail.isEnabled = true
        edtPhoneNumber.isEnabled = true
        edtDogName.isEnabled = true
        spnGender.isEnabled = true
        edtBreed.isEnabled = true
        edtAge.isEnabled = true
        edtBio.isEnabled = true
    }

    fun disableEdit(){
        edtOwnerName.isEnabled = false
        edtEmail.isEnabled = false
        edtPhoneNumber.isEnabled = false
        edtDogName.isEnabled = false
        spnGender.isEnabled = false
        edtBreed.isEnabled = false
        edtAge.isEnabled = false
        edtBio.isEnabled = false
    }

    private fun saveToDB(){
        this.existingUser.oName = edtOwnerName.text.toString()
        this.existingUser.email = edtEmail.text.toString()
        this.existingUser.phoneNumber = edtPhoneNumber.text.toString()

        this.existingUser.dName = edtDogName.text.toString()
        this.existingUser.gender = spnGender.toString()
        this.existingUser.breed = edtBreed.text.toString()
        this.existingUser.age = edtAge.text.toString().toInt()
        this.existingUser.bio = edtBio.text.toString()

        try{
            userViewModel.updateUser(existingUser)
        }catch (ex: Exception){
            Log.d("Profile Fragment", ex.localizedMessage)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            fabEditProfile.id -> {
                this.enableEdit()
                fabEditProfile.visibility = View.GONE
                btnSave.visibility = View.VISIBLE
            }
            fabEditProfile.id -> {
                this.disableEdit()
                fabEditProfile.visibility = View.VISIBLE
                btnSave.visibility = View.GONE

                this.saveToDB()
            }
        }
    }
}