package com.example.prog20082_project_av_jh.utils

import com.example.prog20082_project_av_jh.model.User

/*
   991556560
   Angela Villadiego
   /
*/

interface CheckViewable {

    fun isViewable(toCheck: User, currUser: User) : Boolean {

        if (toCheck.email.equals(currUser.email)) {
            return false
        } else {
            if (currUser.likedList!= null) {
                for (userId in currUser.likedList!!) {
                    if (userId.equals(currUser.dogId)) {
                        return false
                    }
                }
            }

            if (currUser.matchedList!= null) {
                for (userId in currUser.likedList!!) {
                    if (userId.equals(currUser.dogId)) {
                        return false
                    }
                }
            }

            if (currUser.dislikedList!= null) {
                for (userId in currUser.likedList!!) {
                    if (userId.equals(currUser.dogId)) {
                        return false
                    }
                }
            }

        }
        return true
    }

}