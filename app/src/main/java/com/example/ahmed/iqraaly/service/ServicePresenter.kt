package com.example.ahmed.iqraaly.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.example.ahmed.iqraaly.R
import com.example.ahmed.iqraaly.model.Episode
import com.example.ahmed.iqraaly.utility.App
import com.example.ahmed.iqraaly.utility.ExoPlayerAction.Actions.PLAY
import com.example.ahmed.iqraaly.utility.ExoPlayerAction.Actions.STOP
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ServicePresenter {

    private lateinit var exoPlayer: SimpleExoPlayer
    private var isCreated = false

    fun exoPlayer(context: Service): SimpleExoPlayer {
        val trackSelection = AdaptiveTrackSelection.Factory(DefaultBandwidthMeter())
        val trackSelector = DefaultTrackSelector(trackSelection)
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        return exoPlayer
    }

    fun getExoPlayer(): ExoPlayer {
        return exoPlayer
    }

    fun isCreated():Boolean{
        return isCreated
    }

    fun loadData(context: Context, episodes: ArrayList<Episode>, index: Int?) {
        isCreated = true
        exoPlayer.playWhenReady = true
        val size : Int = if(index != null)
            1
        else
            episodes.size
        val mediaSources = arrayOfNulls<MediaSource>(size)
        if(index != null){
            val url = episodes[index].file
            val proxy = App.getProxy(context)
            val proxyUrl = proxy.getProxyUrl(url)
            mediaSources[0] = buildMediaSource(context, Uri.parse(proxyUrl))
        }else {
            for (i in 0 until episodes.size) {
                val url = episodes[i].file
                val proxy = App.getProxy(context)
                val proxyUrl = proxy.getProxyUrl(url)
                mediaSources[i] = buildMediaSource(context, Uri.parse(proxyUrl))
            }
        }

        val mediaSource = if (mediaSources.size == 1)
            mediaSources[0]
        else
            ConcatenatingMediaSource(*mediaSources)
        exoPlayer.prepare(LoopingMediaSource(mediaSource))
    }

    private fun buildMediaSource(context: Context, uri: Uri): MediaSource {
        val dataSource = DefaultDataSourceFactory(context, Util.getUserAgent(context, "iqraaly"))
        return ExtractorMediaSource.Factory(dataSource).createMediaSource(uri)
    }

    fun handelPlayerAction(action: Int) {
        when (action) {
            PLAY -> exoPlayer.playWhenReady = true
            STOP -> exoPlayer.playWhenReady = false
        }
    }

    fun displayNotification(service: Service) {
        val remoteView = RemoteViews(service.packageName, R.layout.notification_controller)
        val intent = PendingIntent
                .getService(service, 0, Intent(service, AudioService::class.java)
                        .apply {
                            putExtra(AudioService.PLAYER_ACTION, 1)
                        }, 0)
        remoteView.setOnClickPendingIntent(R.id.stop_player_btn, intent)

        val manager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(service, "Default")
        notificationBuilder.setContent(remoteView)
        notificationBuilder.setSmallIcon(android.R.drawable.sym_def_app_icon)
        if (Build.VERSION.SDK_INT > 26) {
            manager.createNotificationChannel(NotificationChannel("ID", "Main", NotificationManager.IMPORTANCE_DEFAULT))
            notificationBuilder.setChannelId("ID")
        }
        val notification = notificationBuilder.build()
        service.startForeground(0, notification)
        manager.notify(AudioService.NOTIFICAITON_ID, notification)
    }
}