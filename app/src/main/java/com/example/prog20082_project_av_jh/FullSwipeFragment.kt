package com.example.prog20082_project_av_jh

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.swipe_full_fragment.view.*
import kotlinx.android.synthetic.main.swipe_half_fragment.view.*

/*
   991556560
   Angela Villadiego
   --
*/

class FullSwipeFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: SwipeViewModel

    lateinit var currentView: ConstraintLayout
    lateinit var scrollView: ScrollView
    lateinit var bioTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.swipe_full_fragment, container, false)

        currentView = root.full_profile_layout
        currentView.setOnClickListener(this)

        scrollView = root.full_profile_scroll
        scrollView.setOnClickListener(this)

        bioTextView = root.tvBio
        bioTextView.setOnClickListener(this)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SwipeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        //Have to add clicks for each of the views because otherwise click only is heard in upper half of fragment
        if (v != null) {
            when (v.id) {
                currentView.id -> {
                    Log.e(
                        this@FullSwipeFragment.toString(),
                        "FULL PROFILE FRAGMENT CLICKED ++++++++++++++++++++++++++++"
                    )
                    changeToHalfFragment()
                }
                scrollView.id -> {
                    Log.e(
                        this@FullSwipeFragment.toString(),
                        "SCROLLEVIEW CLICKED +++++++++++"
                    )
                    changeToHalfFragment()
                }
                bioTextView.id -> {
                    Log.e(
                        this@FullSwipeFragment.toString(),
                        "TEXTVIEW CLICKED +++++++++++"
                    )
                    changeToHalfFragment()
                }
            }
        }
    }

    private fun changeToHalfFragment() {
//        requireFragmentManager().beginTransaction().replace(half_swipe_view, insertFragmentNameHere())
//            .addToBackStack(null).commit()
//
//        activity.toFullFragment()

        Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_full_to_nav_swipe_half)
    }

}