package com.example.retrofitkotlindemo.model.user

import com.example.retrofitkotlindemo.model.user.User
import com.google.gson.annotations.SerializedName

class UserList {
    @SerializedName("items")
    var user:List<User> ?= null
}