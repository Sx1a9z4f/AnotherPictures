package git.oversadboy.anotherpictures.model.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Image(
    @SerializedName("id") val id: String,
    val created_at: String,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val likes: Int,
    val description: String?,
    val urls: Urls,
    val links: Links,
    val user: User
) : Parcelable