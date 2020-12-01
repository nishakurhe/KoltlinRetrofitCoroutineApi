package com.example.retrofitkotlindemo.model.album

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlindemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.album_item_lay.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class AbbumActivity : AppCompatActivity() {

    private var albumList = arrayListOf<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // getData()
        getDt()
    }

    // Using coroutine
    private fun getDt(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = ApiClient.getClient.getPhotos().await()
            Log.e("response==","${result.size}")
            albumList = result as ArrayList<DataModel>
            progressBar.visibility = View.GONE
            main_recycler.layoutManager = LinearLayoutManager(this@AbbumActivity)
            main_recycler.adapter = AlbumAdapter()
        }
    }

    // Without coroutine
    private fun getData() {
        val call: Call<List<DataModel>> = ApiClient.getClient.getPhotos()

        call.enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>?, response: Response<List<DataModel>>?) {
                Log.e("response==","${response!!.body()!!.size}")
                albumList = response.body() as ArrayList<DataModel>

                progressBar.visibility = View.GONE
                main_recycler.layoutManager = LinearLayoutManager(this@AbbumActivity)
                main_recycler.adapter = AlbumAdapter()
            }

            override fun onFailure(call: Call<List<DataModel>>?, t: Throwable?) {
                Log.e("response==", "EXCEPTION WHILE GET \n$t")
                progressBar.visibility = View.GONE
                runOnUiThread {
                    Toast.makeText(this@AbbumActivity, "Unable to fetch data..",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    inner class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> () {
        inner class AlbumViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
            return AlbumViewHolder(LayoutInflater.from(this@AbbumActivity).inflate(R.layout.album_item_lay,parent,false))
        }

        override fun getItemCount() = albumList.size

        override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
            holder.itemView.album_id.text = albumList[position].albumid.toString()
            holder.itemView.idde.text = albumList[position].id.toString()
            holder.itemView.album_title.text = albumList[position].title

            Picasso.get().load(albumList[position].thumbnailurl).into(holder.itemView.album_img)
        }
    }
}