package com.example.ahmed.iqraaly.model

/**
 * Created by ahmed on 10/26/2018.
 */
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Episode() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("file")
    @Expose
    var file: String? = null
    @SerializedName("duration")
    @Expose
    var duration: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("is_paid")
    @Expose
    var isPaid: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        file = parcel.readString()
        duration = parcel.readString()
        image = parcel.readString()
        isPaid = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(file)
        parcel.writeString(duration)
        parcel.writeString(image)
        parcel.writeString(isPaid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Episode> {
        override fun createFromParcel(parcel: Parcel): Episode {
            return Episode(parcel)
        }

        override fun newArray(size: Int): Array<Episode?> {
            return arrayOfNulls(size)
        }
    }

}