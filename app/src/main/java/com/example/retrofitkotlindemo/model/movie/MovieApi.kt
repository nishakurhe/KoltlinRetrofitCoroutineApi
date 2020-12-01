package com.example.retrofitkotlindemo.model.movie

import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    @GET("volley_array.json")
    fun movieList(): Call<List<Movie>>
}