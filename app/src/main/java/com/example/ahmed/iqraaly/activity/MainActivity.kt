package com.example.ahmed.iqraaly.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ahmed.iqraaly.R
import com.example.ahmed.iqraaly.connection.ApiClient
import com.example.ahmed.iqraaly.connection.ApiInterface
import com.example.ahmed.iqraaly.model.Book
import com.example.ahmed.iqraaly.model.Data
import com.example.ahmed.iqraaly.model.MainResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var book: Book
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val apiInterface: ApiInterface = ApiClient.apiClient.create(ApiInterface::class.java)
        val call = apiInterface.getBookData()
        call.enqueue(object : Callback<MainResponse> {
            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                book = response.body()!!.data!!.book!!
                initViews(response.body()!!.data!!)
            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {


            }
        })

        coverPhoto.setOnClickListener {
            val intent = Intent(this, BookActivity::class.java)
            intent.putExtra("book", book)
            intent.putExtra("list", book.episodes)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }

    }

    fun initViews(data: Data) {
        author.text = data.book!!.author
        narrator.text = data.book!!.narratorName
        publisher.text = data.book!!.publisherName
        description.text = data.book!!.episodes!![0].description
        Glide.with(this)
                .load(data.book!!.cover)
                .into(coverPhoto)

    }
}
