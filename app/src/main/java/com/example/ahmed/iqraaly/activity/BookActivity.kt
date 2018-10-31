package com.example.ahmed.iqraaly.activity

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.ahmed.iqraaly.R
import com.example.ahmed.iqraaly.adapter.EpisodesAdapter
import com.example.ahmed.iqraaly.model.Book
import com.example.ahmed.iqraaly.model.Episode
import com.example.ahmed.iqraaly.service.AudioService
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*


class BookActivity : AppCompatActivity(), EpisodeViewListener {

    private lateinit var mService: AudioService
    private var mBound: Boolean = false
    private lateinit var book: Book
    private lateinit var episodes: ArrayList<Episode>
    private lateinit var connection: ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        getDataFromIntent()
        connectToService()
        initVies()

    }

    private fun getDataFromIntent() {
        book = intent.getParcelableExtra("book")
        episodes = intent.getParcelableArrayListExtra("list")
    }

    private fun initVies() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        episodeRecycler.layoutManager = layoutManager
        episodeRecycler.adapter = EpisodesAdapter(episodes, this)

        upArrow.setOnClickListener {
            episodeContainer.visibility = View.VISIBLE
        }

        downArrow.setOnClickListener {
            episodeContainer.visibility = View.GONE
        }
    }

    private fun connectToService() {

        connection = object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as AudioService.ServiceBinder
                mService = binder.getService()
                player_view.player = mService.getPlayer()
                if (!mService.isCreated()) {
                    mService.getPlayer().playWhenReady
                    mService.loadData(episodes, null)
                }
                mBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                mBound = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, AudioService::class.java).also { intent ->
            if (!isMyServiceRunning(AudioService::class.java)) {
                startService(intent)
            }
            bindService(intent, connection, Context.BIND_AUTO_CREATE)

        }
    }

    private fun isMyServiceRunning(serviceClass: Class<AudioService>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    override fun onEpisodeClicked(index: Int) {
        mService.getPlayer().stop()
        mService.loadData(episodes, index)
    }

}
