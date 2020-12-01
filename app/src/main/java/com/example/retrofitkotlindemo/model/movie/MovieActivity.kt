package com.example.retrofitkotlindemo.model.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlindemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_item_lay.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MovieActivity : AppCompatActivity(){
    // https://velmm.com/apis/volley_array.json
    private val baseUrl = "https://velmm.com/apis/"
    private var movieLst = arrayListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  getMovies()
        getMov()
    }

    // Using coroutine
    private fun getMov(){
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(MovieApi::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val movie = api.movieList().await()

            movieLst = movie as ArrayList<Movie>

            progressBar.visibility = View.GONE
            main_recycler.layoutManager = LinearLayoutManager(this@MovieActivity)
            main_recycler.adapter = MovieAdapter()
        }
    }

    private fun getMovies(){
        val retrofit:Retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val api = retrofit.create(MovieApi::class.java)
        val call = api.movieList()

            call.enqueue(object : Callback<List<Movie>>{
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                val movie = response.body()
                Log.e("response==","size = ${movie!!.size}  \n $movie")

                movieLst = response.body() as ArrayList<Movie>

                main_recycler.layoutManager = LinearLayoutManager(this@MovieActivity)
                main_recycler.adapter = MovieAdapter()
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.e("response==", "EXCEPTION WHILE GET \n$t")
            }
        })
    }

    inner class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder> () {
        inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
           return  MovieViewHolder(LayoutInflater.from(this@MovieActivity).inflate(R.layout.movie_item_lay,parent,false))
        }

        override fun getItemCount() = movieLst.size

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            Picasso.get().load(movieLst[position].image).into(holder.itemView.movie_img)
            holder.itemView.movie_id.text = movieLst[position].id.toString()
            holder.itemView.movie_name.text = movieLst[position].title
        }
    }
}