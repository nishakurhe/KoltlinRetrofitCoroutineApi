package com.example.retrofitkotlindemo.model

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitkotlindemo.R
import com.example.retrofitkotlindemo.model.movie.MovieActivity
import kotlinx.android.synthetic.main.lay_launch_activity.*

class LaunchActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lay_launch_activity)

        api_user_btn.setOnClickListener {
            if (isNetworkReachable()) {
                val userIntent = Intent(this, MainActivity::class.java)
                startActivity(userIntent)
            }
            else Toast.makeText(this, "No Internet Connection..",Toast.LENGTH_SHORT).show()
        }

        api_album_btn.setOnClickListener {

            if (isNetworkReachable()) {
                val userIntent = Intent(this, AbbumActivity::class.java)
                startActivity(userIntent)
            }
            else Toast.makeText(this, "No Internet Connection..",Toast.LENGTH_SHORT).show()
        }

        api_movie_btn.setOnClickListener {
            if (isNetworkReachable()) {
                val userIntent = Intent(this, MovieActivity::class.java)
                startActivity(userIntent)
            }
            else Toast.makeText(this, "No Internet Connection..",Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkReachable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return true
    }
}
