package com.example.prog20082_project_av_jh

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.matches_list_fragment.view.*

/**
 * A fragment representing a list of Items.
 */
class MatchesFragment : Fragment(), View.OnClickListener {

    private var columnCount = 1

    lateinit var btnToMatchedProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.matches_list_fragment, container, false)

        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//            }
//        }

        //for temporary nav button
        btnToMatchedProfile = view.btnToMatchedProfile
        btnToMatchedProfile.setOnClickListener(this)


        return view
    }

    override fun onClick(v: View?) {
        if (v != null){
            when (v.id) {
                btnToMatchedProfile.id -> {
                    goToMatchedProfile()
                }
            }
        }
    }

    private fun goToMatchedProfile() {
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_matches_to_matched_profile)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MatchesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}