package com.example.retrofitkotlindemo.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlindemo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.user_item_lay.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val baseUrl = "https://api.github.com/search/"
    var userLst = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // getUsers()
        getDt()
    }

    // Using coroutine
    private fun getDt(){
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(Apii::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val users = api.usersLst().await()
            val user = users.user
            Log.e("response==","size = ${user!!.size}  \n $user")
            for (i in user.indices) userLst.add(user[i])

            userLst = user as ArrayList<User>
            progressBar.visibility = View.GONE

            main_recycler.layoutManager = LinearLayoutManager(this@MainActivity)
            main_recycler.adapter = UserAdapter()
        }
    }

    private fun getUsers(){
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
         val api = retrofit.create(Apii::class.java)
         val call = api.usersLst()
        call.enqueue(object : Callback<UserList>{
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                val users = response.body()
                val user = users?.user
                Log.e("response==","size = ${user!!.size}  \n $user")
                for (i in user.indices)
                    userLst.add(user[i])

                main_recycler.layoutManager = LinearLayoutManager(this@MainActivity)
                main_recycler.adapter = UserAdapter()
            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                Log.e("EXCEPTION", "EXCEPTION WHILE GET \n$t")
            }
        })
    }

    inner class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

        inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
            return UserViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.user_item_lay,parent,false))
        }

        override fun getItemCount() = userLst.size

        override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
            holder.itemView.user_id.text = userLst[position].id.toString()
            holder.itemView.user_login.text = userLst[position].login
            holder.itemView.user_score.text = userLst[position].score.toString()
        }
    }
}