package com.example.ahmed.iqraaly.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by ahmed on 10/26/2018.
 */
class MainResponse {
    @SerializedName("code")
    @Expose
    var code: Int = 0
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null

}
