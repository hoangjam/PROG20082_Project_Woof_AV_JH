package com.example.prog20082_project_av_jh.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prog20082_project_av_jh.R
import com.example.prog20082_project_av_jh.model.User
import com.example.prog20082_project_av_jh.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.match_fragment.view.*

/*
   991556560
   Angela Villadiego
   /
*/

class MatchesAdapter (
    val context: Context,
    val matchesList: MutableList<User>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchesAdapter.MatchViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.match_fragment, null)

        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchesAdapter.MatchViewHolder, position: Int) {
        holder.bind(matchesList[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return matchesList.size
    }


    inner class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvMatchHeader: TextView = itemView.tvMatchHeader

        fun bind(match: User, clickListener: OnItemClickListener) {
            tvMatchHeader.setText("You and ${match.dName} matched!")

            itemView.setOnClickListener{
                clickListener.onItemClicked(match)
            }

        }

    }
}

interface OnItemClickListener {
    fun onItemClicked(match: User)
}