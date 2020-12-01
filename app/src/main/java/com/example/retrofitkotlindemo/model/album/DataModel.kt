package com.example.retrofitkotlindemo.model.album

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("albumId")
    val albumid: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailurl: String
)