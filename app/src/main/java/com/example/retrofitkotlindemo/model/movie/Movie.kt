package com.example.retrofitkotlindemo.model.movie

import com.google.gson.annotations.SerializedName
data class Movie(
    @SerializedName("id")
    val id:Int,

    @SerializedName("image")
    val image:String,

    @SerializedName("title")
    val title:String
)