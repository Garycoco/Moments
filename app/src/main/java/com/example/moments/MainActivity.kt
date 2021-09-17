package com.example.moments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar!!.hide()

        binding.images.setOnClickListener {
            val i = Intent(this, Images::class.java)
            startActivity(i)
        }
        binding.music.setOnClickListener {
            val i = Intent(this, Music::class.java)
            startActivity(i)
        }
        binding.videos.setOnClickListener {
            val i = Intent(this, Videos::class.java)
            startActivity(i)
        }

    }
}