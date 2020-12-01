package com.example.retrofitkotlindemo.model

import com.google.gson.annotations.SerializedName

class UserList {
    @SerializedName("items")
    var user:List<User> ?= null
}