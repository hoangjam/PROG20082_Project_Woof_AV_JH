package com.example.prog20082_project_av_jh

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.prog20082_project_av_jh.adapters.MatchesAdapter
import com.example.prog20082_project_av_jh.adapters.OnItemClickListener
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.ui.MatchedProfileFragment
import com.example.prog20082_project_av_jh.views.MainActivity
import kotlinx.android.synthetic.main.matches_list_fragment.view.*

/**
 * A fragment representing a list of Items.
 */
class MatchesFragment : Fragment(), OnItemClickListener {

    private var columnCount = 1

    private lateinit var rvMatches: RecyclerView
    private lateinit var matchesAdapter: MatchesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var matchesList: MutableList<User>
    private lateinit var mainActivity: MainActivity
    private lateinit var currentUser: User


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

        mainActivity = activity as MainActivity

        mainActivity.userViewModel.getUserByEmail(mainActivity.currUserEmail!!)?.observe(this.requireActivity(), {matchedUser ->

            if (matchedUser != null) {
                this.currentUser = matchedUser
                Log.e("MATCHES FRAGMENT: ", "current user: ${this.currentUser}")
            }
        })

        this.rvMatches = view.findViewById(R.id.rvMatches)
        this.matchesList = mutableListOf()
        //this.populateMatchesList()
        this.matchesAdapter = MatchesAdapter(this.requireContext(), matchesList, this)
        this.rvMatches.adapter = this.matchesAdapter
        this.viewManager = LinearLayoutManager(this.requireContext())
        this.rvMatches.layoutManager = this.viewManager
        this.rvMatches.setHasFixedSize(true)
        this.rvMatches.addItemDecoration(DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL))

        return view
    }

    override fun onResume() {
        super.onResume()

        mainActivity.userViewModel.allUsers
        this.populateMatchesList()

        this.matchesAdapter = MatchesAdapter(this.requireContext(), matchesList, this)
        this.rvMatches.adapter = this.matchesAdapter
    }

    private fun populateMatchesList() {
        Log.d("Populating",".......")
        if (this.currentUser.matchedList != null) {
            matchesList.clear()

            //get user objects by dog id
            for (dogId in this.currentUser.matchedList!!) {
                Log.e("populateMatchesList()", "dog id called: ${dogId}")
//                this.addDogNameFromDogId(dogId)
//                matchesList.add(this.dogNameFromId(dogId))
                this.addDogFromId(dogId)
            }
        }

        Log.d("populateMatchesList: " , matchesList.toString())
    }

    private fun addDogFromId(dogId: String){

        mainActivity.userViewModel.allUsers.observe(viewLifecycleOwner, {users ->

            for (user in users) {
                if (user.dogId.equals(dogId)){
                    matchesList.add(user)
                }
            }

        })

    }

    private fun goToMatchedProfile() {
        //Navigation.findNavController(requireView()).navigate(R.id.action_nav_matches_to_matched_profile)
        //TODO: obtain nav controller instead of creating new one using supportFragmentManager
        var navController = findNavController()
        navController.navigate(R.id.action_nav_matches_to_matched_profile)
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

    override fun onItemClicked(match: User) {

        var bundle = Bundle()
        bundle.putString("matchedEmail", match.email)

        var matchedProfileFragment = MatchedProfileFragment.newInstance("matchedEmail", match.email)
        matchedProfileFragment.arguments = bundle

//        var navController = findNavController()
//        navController.navigate(R.id.action_nav_matches_to_matched_profile)

        parentFragmentManager.beginTransaction().replace(R.id.matchesFragment, matchedProfileFragment).commit()
    }

}