package com.example.retrofitkotlindemo.model.user

import retrofit2.Call
import retrofit2.http.GET

interface Apii{
    @GET("users?q=rokano")
    fun usersLst(): Call<UserList>
}
