package com.example.prog20082_project_av_jh

import android.R.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.swipe_half_fragment.view.*


class HalfSwipeFragment : Fragment(), View.OnClickListener {

    private lateinit var cardView: CardView
    private lateinit var fabLike: FloatingActionButton
    private lateinit var fabDislike: FloatingActionButton
    private lateinit var tvDogName: TextView

    private val TAG =  this@HalfSwipeFragment.toString()

    private lateinit var viewModel: SwipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.swipe_half_fragment, container, false)

        cardView = root.card_view
        fabLike = root.fabLike
        fabDislike = root.fabDislike
        tvDogName = root.tvDogName

        cardView.setOnClickListener(this)
        fabLike.setOnClickListener(this)
        fabDislike.setOnClickListener(this)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SwipeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                cardView.id -> {
                    Log.e(
                       TAG,"CARD FRAGMENT CLICKED ++++++++++++++++++++++++++++"
                    )
                    changeToFullFragment()
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

    private fun changeToFullFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_half_to_nav_swipe_full)
    }

    private fun likeProfile() {
        Log.e(TAG, "+++++++++++++++ LIKED ++++++++++++++++")
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
                this@HalfSwipeFragment.changeInfo()
                this@HalfSwipeFragment.slideViewIn()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                //not needed
            }
        })

        cardView.startAnimation(anim)

    }

    private fun slideViewIn() {
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.slide_in)
        cardView.startAnimation(anim)
    }

    private fun changeInfo() {
        tvDogName.setText("Fifi")
    }



}