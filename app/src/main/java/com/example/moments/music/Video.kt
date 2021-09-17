package com.example.moments.music

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Video(
    val videoTitle: String,
    val duration: String,
    val thumbNail: Int,
    val video: String
):Parcelable