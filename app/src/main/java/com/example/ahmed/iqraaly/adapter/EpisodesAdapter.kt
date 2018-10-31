package com.example.ahmed.iqraaly.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ahmed.iqraaly.R
import com.example.ahmed.iqraaly.activity.EpisodeViewListener
import com.example.ahmed.iqraaly.model.Episode
import java.util.ArrayList

class EpisodesAdapter(private val episodeList : ArrayList<Episode>, private val episodeViewListener: EpisodeViewListener) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        val holder = EpisodesAdapter.ViewHolder(view)
        holder.playIcon.setOnClickListener {
            episodeViewListener.onEpisodeClicked(holder.playIcon.tag as Int)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = episodeList[position].title
        Glide.with(holder.coverImageView.context)
                .load(episodeList[position].image)
                .into(holder.coverImageView)

        holder.playIcon.tag = position
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val playIcon: ImageView = view.findViewById(R.id.playIcon)
        val downloadIcon: ImageView = view.findViewById(R.id.downloadIcon)
        val titleTextView: TextView = view.findViewById(R.id.title)
        val coverImageView: ImageView = view.findViewById(R.id.bookCover)
    }
}