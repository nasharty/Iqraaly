package com.example.ahmed.iqraaly.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ahmed.iqraaly.R
import com.example.ahmed.iqraaly.activity.EpisodeViewListener
import com.example.ahmed.iqraaly.adapter.EpisodesAdapter
import com.example.ahmed.iqraaly.model.Episode
import kotlinx.android.synthetic.main.episode_list.*
import java.util.*

class EpisodeFragment : DialogFragment(), EpisodeViewListener {
    override fun onEpisodeClicked(index: Int) {

    }

    private var episodeList: ArrayList<Episode>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodeList = arguments!!.getParcelableArrayList(EPISODE_LIST)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.window!!.attributes.windowAnimations
        return inflater.inflate(R.layout.episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        episodeRecycler.layoutManager = layoutManager
        episodeRecycler.adapter = EpisodesAdapter(episodeList!!,this)

        downArrow.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        activity!!.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }


    companion object {
        const val EPISODE_LIST = "episode_list"

        fun getInstance(episodeList: ArrayList<Episode>): EpisodeFragment {
            val episodeFragment = EpisodeFragment()
            val args = Bundle()
            args.putParcelableArrayList(EPISODE_LIST, episodeList)
            episodeFragment.arguments = args
            return episodeFragment
        }
    }
}