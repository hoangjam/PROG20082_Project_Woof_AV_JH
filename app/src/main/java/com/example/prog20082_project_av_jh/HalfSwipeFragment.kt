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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.preferences.SharedPreferencesManager
import com.example.prog20082_project_av_jh.utils.CheckViewable
import com.example.prog20082_project_av_jh.views.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.swipe_half_fragment.*
import kotlinx.android.synthetic.main.swipe_half_fragment.view.*
import java.text.SimpleDateFormat
import java.util.*


class HalfSwipeFragment : Fragment(), View.OnClickListener, CheckViewable {

    private lateinit var cardView: CardView
    private lateinit var fabLike: FloatingActionButton
    private lateinit var fabDislike: FloatingActionButton
    private lateinit var tvDogName: TextView
    private lateinit var tvDogAge: TextView
    private lateinit var tvDogGender: TextView
    private lateinit var tvDogSize: TextView
    private lateinit var tvDogBreed: TextView
    private lateinit var mainActivity: MainActivity
    private lateinit var currentUser: User
    private lateinit var tvEndOfLine: TextView
    private lateinit var dogIdHolder: TextView

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
        tvDogAge = root.tvDogAge
        tvDogSize = root.tvDogSize
        tvDogGender = root.tvDogGender
        tvDogBreed= root.tvBreed
        tvEndOfLine = root.tvEndOfLine
        dogIdHolder = root.dog_id_holder

        cardView.setOnClickListener(this)
        fabLike.setOnClickListener(this)
        fabDislike.setOnClickListener(this)

        //get main activity for access to public fields and functions
        mainActivity = activity as MainActivity


        mainActivity.userViewModel.getUserByEmail(mainActivity.currUserEmail!!)?.observe(this.requireActivity(), {matchedUser ->

            if (matchedUser != null) {
                this.currentUser = matchedUser

                Log.e("Profile Fragment", "Matched user : " + matchedUser.toString())
            }
        })

        //only call if we're resuming this view after reaching EOL and need to check if more results have entered database
        if (mainActivity.swipeEOL) {
            Log.e(TAG, "RESUMED AND EOL")
            //check if there is another user available, if so, display it. else, make sure that display is at noMoreDogs.
            mainActivity.userViewModel.allUsers.observe(viewLifecycleOwner, {users ->
                if (mainActivity.index < users.size) {
                    moreDogs()
                } else {
                    noMoreDogs()
                }
            })
        } else {
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
        if (v != null) {
            when (v.id) {
                cardView.id -> {
                    Log.e(
                       TAG,"CARD FRAGMENT CLICKED ++++++++++++++++++++++++++++"
                    )
                    changeToFullFragment()
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

    private fun changeToFullFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_half_to_nav_swipe_full)
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
                                    this@HalfSwipeFragment.notifyOfMatch(matchedUser.dName)
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

    private fun switchCard(liked: Boolean) {
        Log.e(TAG, "++++++ switching cards.......  ++++++++++")

        //Animate out, when finished change info, when changed animate back in
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.slide_out)

        anim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                //not needed
            }

            override fun onAnimationEnd(animation: Animation?) {
                //after finished swiping out, change data, then animate in.

                if (liked) {
                    this@HalfSwipeFragment.likeProfile(dogIdHolder.text.toString(), currentUser.dogId)
                } else {
                    this@HalfSwipeFragment.dislikeProfile()
                }

                mainActivity.index += 1



                this@HalfSwipeFragment.changeInfo()

                Log.e(TAG, "Checking if EOL...")

                //as long as theres another card, slide it in again
                if (!mainActivity.swipeEOL) {
                    this@HalfSwipeFragment.slideViewIn()
                }
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

    private fun fadeViewIn() {
        var anim = AnimationUtils.loadAnimation(this.requireContext(), R.anim.fade_in)
        tvEndOfLine.startAnimation(anim)
    }

    private fun noMoreDogs() {
        Toast.makeText(this.requireActivity(), "Last dog reached!", Toast.LENGTH_SHORT).show()
        cardView.setVisibility(View.GONE)
        tvEndOfLine.setVisibility(View.VISIBLE)
        fabDislike.setVisibility(View.GONE)
        fabLike.setVisibility(View.GONE)
        fadeViewIn()
    }

    private fun moreDogs() {
        mainActivity.swipeEOL = false
        cardView.setVisibility(View.VISIBLE)
        tvEndOfLine.setVisibility(View.INVISIBLE)
        fabDislike.setVisibility(View.VISIBLE)
        fabLike.setVisibility(View.VISIBLE)
        this.changeInfo()
        //doesnt need to be called because oncreateview does it
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

    private fun notifyOfMatch(dogName: String) {
        val alertBuilder = AlertDialog.Builder(this.requireContext())
        alertBuilder.setTitle("New Match!")
        alertBuilder.setMessage("You and ${dogName} matched! Go to your matches to see more!")
        alertBuilder.setPositiveButton("See matches") { dialog, which ->
            //set action to matches fragment

            Navigation.findNavController(requireView()).navigate(R.id.action_nav_swipe_half_to_nav_matches)

//            Toast.makeText(this.requireContext(), "Going to matches fragment.", Toast.LENGTH_SHORT).show()
        }

        alertBuilder.setNegativeButton("Later") {dialog, which ->
            //do nothing
        }

        alertBuilder.show()
    }

}