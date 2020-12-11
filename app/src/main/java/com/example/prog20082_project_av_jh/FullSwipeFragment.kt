package com.example.prog20082_project_av_jh

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.swipe_full_fragment.view.*

/*
   991556560
   Angela Villadiego
   --
*/

class FullSwipeFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: SwipeViewModel

    private lateinit var currentView: ConstraintLayout
    private lateinit var scrollView: ScrollView
    private lateinit var bioTextView: TextView

    private lateinit var swipeView: ConstraintLayout
    private lateinit var fabLike: FloatingActionButton
    private lateinit var fabDislike: FloatingActionButton
    private lateinit var tvDogName: TextView

    private val TAG =  this@FullSwipeFragment.toString()

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

        fabDislike = root.fabDislike
        fabDislike.setOnClickListener(this)

        fabLike = root.fabLike
        fabLike.setOnClickListener(this)

        tvDogName = root.tvDogName

        swipeView = root.profile_info

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
                fabLike.id -> {
                    this.likeProfile()
                    this.switchCard()
                }
                fabDislike.id -> {
                    Log.e(TAG, "++++++++++++++ DISLIKED +++++++++++++++++++")
                    this.switchCard()
                }
            }
        }
    }

    private fun changeToHalfFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_full_to_nav_swipe_half)
    }

    private fun switchCard() {
        Log.e(TAG, "++++++ switching cards.......  ++++++++++")

        //Animate out, when finished change info, when changed animate back in
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.slide_out)

        anim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                //not needed
            }

            override fun onAnimationEnd(animation: Animation?) {
                //after finished, change data, then animate in.
                this@FullSwipeFragment.changeInfo()
                this@FullSwipeFragment.slideViewIn()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                //not needed
            }
        })

        swipeView.startAnimation(anim)

    }

    private fun slideViewIn() {
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.slide_in)
        swipeView.startAnimation(anim)
    }

    private fun changeInfo() {
        tvDogName.setText("Fifi")
    }

    private fun likeProfile() {
        Log.e(TAG, "+++++++++++++ Profile Liked +++++++++")
    }

}