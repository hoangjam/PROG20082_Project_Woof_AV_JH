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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.utils.CheckViewable
import com.example.prog20082_project_av_jh.views.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.swipe_full_fragment.view.*

/*
   991556560
   Angela Villadiego
   --
*/

class FullSwipeFragment : Fragment(), View.OnClickListener, CheckViewable {

    private lateinit var viewModel: SwipeViewModel

    private lateinit var currentView: ConstraintLayout
    private lateinit var scrollView: ScrollView
    private lateinit var bioTextView: TextView

    private lateinit var swipeView: ConstraintLayout
    private lateinit var fabLike: FloatingActionButton
    private lateinit var fabDislike: FloatingActionButton
    private lateinit var tvDogName: TextView
    private lateinit var tvDogAge: TextView
    private lateinit var tvDogGender: TextView
    private lateinit var tvDogSize: TextView
    private lateinit var tvDogBreed: TextView
    private lateinit var tvDogBio: TextView
    private lateinit var dogIdHolder: TextView


    private lateinit var mainActivity: MainActivity
    private lateinit var tvEndOfLine: TextView
    private lateinit var currentUser: User

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
        tvDogAge = root.tvDogAge
        tvDogGender = root.tvDogGender
        tvDogSize = root.tvDogSize
        tvDogBreed = root.tvBreed
        tvDogBio = root.tvBio
        dogIdHolder = root.dog_id_holder

        swipeView = root.profile_info

        tvEndOfLine = root.tvEndOfLine

        mainActivity = activity as MainActivity

        mainActivity.userViewModel.getUserByEmail(mainActivity.currUserEmail!!)?.observe(this.requireActivity(), {matchedUser ->

            if (matchedUser != null) {
                this.currentUser = matchedUser

                Log.e("Profile Fragment", "Matched user : " + matchedUser.toString())
            }
        })


        if (!mainActivity.swipeEOL) {
            //only call if visiblity toggled as EOL but more results have been added (EOL will have changed from halfswipe fragment previous to this)
            if (tvEndOfLine.visibility == View.VISIBLE) {
                tvEndOfLine.visibility = View.INVISIBLE
                swipeView.visibility = View.VISIBLE
                fabLike.visibility = View.VISIBLE
                fabDislike.visibility = View.VISIBLE
            }
            this.changeInfo()
        }

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
                    this.switchCard(true)
                }
                fabDislike.id -> {
                    Log.e(TAG, "++++++++++++++ DISLIKED +++++++++++++++++++")
                    this.switchCard(false)
                }
            }
        }
    }

    private fun changeToHalfFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_full_to_nav_swipe_half)
    }

    private fun switchCard(liked: Boolean) {
        Log.e(TAG, "++++++ switching cards.......  ++++++++++")

        //Animate out, when finished change info, when changed animate back in
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.fade_out)

        anim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                //not needed
            }

            override fun onAnimationEnd(animation: Animation?) {

                //after finished, change data, then animate in.

                if (liked) {
                    this@FullSwipeFragment.likeProfile(dogIdHolder.text.toString(), currentUser.dogId)
                } else {
                    this@FullSwipeFragment.dislikeProfile()
                }

                mainActivity.index += 1


                this@FullSwipeFragment.changeInfo()

                //as long as theres another card, slide it in again
                if (!mainActivity.swipeEOL) {
                    this@FullSwipeFragment.fadeViewIn(swipeView)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {
                //not needed
            }
        })

        swipeView.startAnimation(anim)

    }

    private fun fadeViewIn(view: View) {
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.fade_in)
        view.startAnimation(anim)
    }

    private fun changeInfo() {
        Log.e(TAG, "CHANGING INFO -.......... ")

        mainActivity.userViewModel.allUsers.observe(viewLifecycleOwner, {users ->

            if (mainActivity.index >= users.size) {
                Log.e(TAG, "index: ${mainActivity.index}, size: ${users.size}")
                mainActivity.swipeEOL = true;
                this.noMoreDogs()
            } else {

                try {
                    if (users[mainActivity.index] != null) {
                        //if user is not viewable, then try again with the next user
                        if (isViewable(users[mainActivity.index], currentUser)) {
                            tvDogName.setText(users[mainActivity.index].dName)
                            tvDogAge.setText(users[mainActivity.index].age.toString() + " yrs")
                            tvDogGender.setText(users[mainActivity.index].gender)
                            tvDogBreed.setText(users[mainActivity.index].breed)
                            tvDogBio.setText(users[mainActivity.index].bio)
                            if (users[mainActivity.index].dogSize == 0 || users[mainActivity.index].dogSize == null) {
                                tvDogSize.setText("?")
                            } else {
                                tvDogSize.setText(users[mainActivity.index].dogSize.toString() + " lbs")
                            }
                            //set dog id holder text
                            dogIdHolder.setText(users[mainActivity.index].dogId)
                        } else {
                            mainActivity.index += 1
                            changeInfo()
                        }

                    }
                } catch (ex: IndexOutOfBoundsException) {
                    mainActivity.swipeEOL = true
                    this.noMoreDogs()
                }
            }

        })
    }

    private fun likeProfile(likedDogId: String, currentDogId: String) {
        Log.e(TAG, "+++++++++++++++ LIKED ++++++++++++++++")
        if (likedDogId.equals("unset")) {
            Log.e(TAG, "dogIdHolder not set.... why?")
        } else {
            currentUser.likedList?.add(likedDogId)
            mainActivity.userViewModel.updateUser(this.currentUser)
            Log.e(TAG, "Added to current dogs likes <3<3<3<3<3<3<3<3 ")

            //check if currently logged in user is on the liked dog's liked list
            //add to liked list of current dog and, if on other dogs liked list, add to matches on both dogs as well
            mainActivity.userViewModel.getUserByDogId(likedDogId)?.observe(this.requireActivity(), {matchedUser ->
                if (matchedUser != null) {
                    if (matchedUser.likedList != null) {

                        if (matchedUser.likedList!!.contains(currentDogId)) {
                            Log.e("WITHIN CONTAINS: ", "liked dog likes you back")
                            if(matchedUser.matchedList != null && currentUser.matchedList != null && matchedUser.dislikedList != null && currentUser.dislikedList != null) {
                                //duplicates can happen so make sure matchedList doesn't already contain currentdogid
                                //also not sure why the fact that theyre in the list needs to be checked again here but it does or else it performs wrong
                                if (!matchedUser.matchedList!!.contains(currentDogId)
                                    && matchedUser.likedList!!.contains(currentDogId)
                                    && !matchedUser.dislikedList!!.contains(currentDogId)) {
                                    matchedUser.matchedList!!.add(currentDogId)
                                    mainActivity.userViewModel.updateUser(matchedUser)
                                    this@FullSwipeFragment.notifyOfMatch(matchedUser.dName)
                                }
                                if (!currentUser.matchedList!!.contains(likedDogId)
                                    && currentUser.likedList!!.contains(likedDogId)
                                    && !matchedUser.dislikedList!!.contains(likedDogId)) {
                                    currentUser.matchedList!!.add(likedDogId)
                                    mainActivity.userViewModel.updateUser(currentUser)

                                }
                                Log.e(TAG, currentUser.toString())
                                Log.e(TAG, matchedUser.toString())
                            } else {
                                Log.e(TAG, "one or both matched lists are null")
                            }
                        } else {
                            Log.e(TAG, "Liked dog doesnt like back")
                        }

                    } else {
                        Log.e(TAG, "matcheduser null in likeprofile")
                    }
                } else {
                    Log.e(TAG, "user not found by dog id in likeprofile")
                }
            })

            //update database
            mainActivity.userViewModel.updateUser(this.currentUser)
        }
    }

    private fun dislikeProfile() {
        //add the currently showing profile's dog id to the currently logged in user's dislike list
        if (dogIdHolder.text.toString().equals("unset")) {
            Log.e(TAG, "dogIdHolder not set.... why?")
        } else {
            if (this.currentUser.dislikedList != null) {
                this.currentUser.dislikedList!!.add(dogIdHolder.text.toString())
                Log.e(TAG, "currDogId set ${this.dogIdHolder.text.toString()}")
            }

            //update database
            mainActivity.userViewModel.updateUser(this.currentUser)
        }
    }

    private fun noMoreDogs() {
        Toast.makeText(this.requireActivity(), "Last dog reached!", Toast.LENGTH_SHORT).show()
        swipeView.setVisibility(View.GONE)
        tvEndOfLine.setVisibility(View.VISIBLE)
        fabDislike.setVisibility(View.GONE)
        fabLike.setVisibility(View.GONE)
        fadeViewIn(tvEndOfLine)
    }

    private fun notifyOfMatch(dogName: String) {
        val alertBuilder = AlertDialog.Builder(this.requireContext())
        alertBuilder.setTitle("New Match!")
        alertBuilder.setMessage("You and ${dogName} matched! Go to your matches to see more!")
        alertBuilder.setPositiveButton("See matches") { dialog, which ->
            //set action to matches fragment

            Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_full_to_nav_matches)

//            Toast.makeText(this.requireContext(), "Going to matches fragment.", Toast.LENGTH_SHORT).show()
        }

        alertBuilder.setNegativeButton("Later") {dialog, which ->
            //do nothing
        }

        alertBuilder.show()
    }

}