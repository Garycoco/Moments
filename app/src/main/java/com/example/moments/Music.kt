package com.example.moments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*

class Music : AppCompatActivity() {
    private val songList = ArrayList<SongInfo>()
    private val bar : SeekBar? = null
    private var adapteer : MySongAdapter? = null
    private lateinit var button: Button
    private var player : MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        adapteer = MySongAdapter(songList)
        val recyclerView = findViewById<ListView>(R.id.recyclerView)
        recyclerView.adapter = adapteer



        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Music"


        MobileAds.initialize(this){}

        val addRequest = AdRequest.Builder().build()
        val bannerView = findViewById<AdView>(R.id.bannerAd)
        bannerView.loadAd(addRequest)









        songList.add(SongInfo("Verao","Shane lvers","https://www.silvermansound.com/music/verao.mp3"))
        songList.add(SongInfo("Uthando","NuniCky","https://lepmusic.co.za/wp-content/uploads/2021/07/NuniCky-Uthando..mp3"))
        songList.add(SongInfo("YELLOW-BONE","BLAQMOON","https://lepmusic.co.za/wp-content/uploads/2021/08/BLAQMOON-YELLOW-BONE.mp3"))


        //val adapter = musicAdapter(songList)





    }
    inner class MySongAdapter:BaseAdapter{
        var MyListSong = ArrayList<SongInfo>()
        constructor(MyListSong:ArrayList<SongInfo>):super(){
            this.MyListSong = MyListSong
        }

        override fun getCount(): Int {
            return this.MyListSong.size
        }

        override fun getItem(item: Int): Any {
            return this.MyListSong[item]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }
        val loading = Loading(this@Music)
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.music_list,null)
            val song = this.MyListSong[p0]
            val songName = myView.findViewById<TextView>(R.id.songName)
            val author = myView.findViewById<TextView>(R.id.author)
            button = myView.findViewById(R.id.buttonPlay)
            button.setOnClickListener {
                if (button.text.equals("Stop")) {
                    player!!.stop()
                    button.text = "Play"
                }else{
                    loading.startLoading()
                    player = MediaPlayer()
                    try {

                        player!!.setDataSource(song.songUrl)
                        player!!.prepare()
                        player!!.start()
                        button.text = "Stop"
                        loading.dismiss()
                        bar!!.max = player!!.duration
                    } catch (ex: Exception) {
                        Toast.makeText(this@Music, ex.message, Toast.LENGTH_SHORT).show()

                    }
                }
            }
            songName.text = song.title
            author.text = song.author

            return myView
        }

    }
}