package com.example.prog20082_project_av_jh

import android.R.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.swipe_half_fragment.view.*


class HalfSwipeFragment : Fragment(), View.OnClickListener {

    lateinit var cardView: CardView

    private lateinit var viewModel: SwipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.swipe_half_fragment, container, false)

        cardView = root.card_view

        cardView.setOnClickListener(this)

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
                        this@HalfSwipeFragment.toString(),
                        "CARD FRAGMENT CLICKED ++++++++++++++++++++++++++++"
                    )
                    changeToFullFragment()
                }
            }
        }
    }

    private fun changeToFullFragment() {
//        requireFragmentManager().beginTransaction().replace(half_swipe_view, insertFragmentNameHere())
//            .addToBackStack(null).commit()
//
//        activity.toFullFragment()

        Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_half_to_nav_swipe_full)
    }

}