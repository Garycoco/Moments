package com.example.moments.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import java.util.ArrayList

class musicAdapter(val video:ArrayList<Video>):RecyclerView.Adapter<musicAdapter.ViewHolder>(){
    var onItemClick:((Video) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): musicAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: musicAdapter.ViewHolder, position: Int) {
        holder.bindItems(video[position])
    }

    override fun getItemCount(): Int {
        return video.size
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(video[adapterPosition])
            }
        }
        fun bindItems(video:Video){
            val videoName = itemView.findViewById<TextView>(R.id.videoTitle)
            val duration = itemView.findViewById<TextView>(R.id.videoDuration)
            val thumbNail = itemView.findViewById<ImageView>(R.id.thumbNail)

            videoName.text= video.videoTitle
            duration.text=video.duration
            thumbNail.setImageResource(video.thumbNail)

        }
    }

}