package com.example.ahmed.iqraaly.model

/**
 * Created by ahmed on 10/26/2018.
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("book")
    @Expose
    var book: Book? = null

}