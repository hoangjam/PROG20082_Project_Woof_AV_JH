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
            if (!currUser.likedList.isNullOrEmpty()) {
                for (dogId in currUser.likedList!!) {
                    if (toCheck.dogId.equals(dogId)) {
                        return false
                    }
                }
            }

            if (!currUser.matchedList.isNullOrEmpty()) {
                for (dogId in currUser.matchedList!!) {
                    if (toCheck.dogId.equals(dogId)) {
                        return false
                    }
                }
            }

            if (!currUser.dislikedList.isNullOrEmpty()) {
                for (dogId in currUser.dislikedList!!) {
                    if (toCheck.dogId.equals(dogId)) {
                        return false
                    }
                }
            }

        }
        return true
    }

}