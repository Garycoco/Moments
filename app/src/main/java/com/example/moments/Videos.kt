package com.example.moments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.music.Video
import com.example.moments.music.musicAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.ArrayList

class Videos : AppCompatActivity() {
    private val videos = ArrayList<Video>()
    private lateinit var adapter : musicAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Videos"

        MobileAds.initialize(this){}

        val addRequest = AdRequest.Builder().build()
        val bannerView = findViewById<AdView>(R.id.bannerAd)
        bannerView.loadAd(addRequest)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = musicAdapter(videos)

        recyclerView.adapter = adapter

        videos.add(Video("Coin Operate","05:14",R.drawable.ic_images,"android.resource://$packageName/${R.raw.coin}"))
        videos.add(Video("Sneak Attack","04:41",R.drawable.ic_baseline_videocam_24,"android.resource://$packageName/${R.raw.attack}"))

        adapter.onItemClick = {Video ->
            val intent = Intent(this,VideoPlaying::class.java)
            intent.putExtra("Video", Video)
            this.startActivity(intent)
        }

    }
}