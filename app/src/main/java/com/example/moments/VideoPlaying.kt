package com.example.moments

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import com.example.moments.music.Video

class VideoPlaying : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playing)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Videos"


        val videoView = findViewById<VideoView>(R.id.videoView)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        val video = intent.getParcelableExtra<Video>("Video")
        val fileUri = Uri.parse(video?.video)

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(fileUri)
        videoView.requestFocus()
        videoView.start()
    }
}