package com.example.ahmed.iqraaly.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.ahmed.iqraaly.model.Episode
import com.google.android.exoplayer2.SimpleExoPlayer

class AudioService : Service() {

    companion object {
        const val EPISODE_LIST = "episode_list"
        const val PLAYER_ACTION = "playPauseAction"
        const val NOTIFICAITON_ID = 0
    }

    private val servicePresenter = ServicePresenter()
    private lateinit var myExoPlayer: SimpleExoPlayer

    override fun onCreate() {
        super.onCreate()
        myExoPlayer = servicePresenter.exoPlayer(this)
        servicePresenter.displayNotification(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return ServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            servicePresenter.handelPlayerAction(it.getIntExtra(PLAYER_ACTION, -1))
        }
        return super.onStartCommand(intent, flags, startId)
    }

    inner class ServiceBinder : Binder() {
        fun getService(): AudioService = this@AudioService
    }

    fun getPlayer() = servicePresenter.getExoPlayer()

    fun isCreated() = servicePresenter.isCreated()

    fun loadData(episodes: ArrayList<Episode>, index: Int?) {
        servicePresenter.loadData(this, episodes, index)
    }
}
