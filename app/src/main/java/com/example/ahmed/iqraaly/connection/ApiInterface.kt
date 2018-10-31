package com.example.ahmed.iqraaly.connection

import com.example.ahmed.iqraaly.model.MainResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by ahmed on 10/26/2018.
 */
interface ApiInterface {

    @GET("api/show/1203")
    fun getBookData(): Call<MainResponse>
}