package git.oversadboy.anotherpictures.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token(
    val accessToken: String,
    val tokenType: String,
    val scope: String,
    val createdAt: Int
) : Parcelable