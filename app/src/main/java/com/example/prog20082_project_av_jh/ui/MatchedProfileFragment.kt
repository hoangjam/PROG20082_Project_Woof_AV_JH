package com.example.prog20082_project_av_jh.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.prog20082_project_av_jh.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_matched_profile.view.*



/**
 * A simple [Fragment] subclass.
 * Use the [MatchedProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchedProfileFragment : Fragment(), View.OnClickListener {

    lateinit var fabToMap : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_matched_profile, container, false)

        fabToMap = view.fabLocation
        fabToMap.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        if (v != null){
            when (v.id) {
                fabToMap.id -> {
                    goToLocation()
                }
            }
        }
    }

    private fun goToLocation() {
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_matched_profile_to_nav_maps)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         *
         * @return A new instance of fragment MatchedProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                MatchedProfileFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}