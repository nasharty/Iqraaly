package com.example.ahmed.iqraaly.model

/**
 * Created by ahmed on 10/26/2018.
 */
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class Book() : Parcelable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("author_id")
    @Expose
    var authorId: String? = null
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("publisher_name")
    @Expose
    var publisherName: String? = null
    @SerializedName("about")
    @Expose
    var about: String? = null
    @SerializedName("is_paid")
    @Expose
    var isPaid: String? = null
    @SerializedName("narrator_id")
    @Expose
    var narratorId: String? = null
    @SerializedName("narrator_name")
    @Expose
    var narratorName: String? = null
    @SerializedName("cover")
    @Expose
    var cover: String? = null
    @SerializedName("episodes")
    @Expose
    lateinit var episodes: ArrayList<Episode>

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        authorId = parcel.readString()
        author = parcel.readString()
        publisherName = parcel.readString()
        about = parcel.readString()
        isPaid = parcel.readString()
        narratorId = parcel.readString()
        narratorName = parcel.readString()
        cover = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(authorId)
        parcel.writeString(author)
        parcel.writeString(publisherName)
        parcel.writeString(about)
        parcel.writeString(isPaid)
        parcel.writeString(narratorId)
        parcel.writeString(narratorName)
        parcel.writeString(cover)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}