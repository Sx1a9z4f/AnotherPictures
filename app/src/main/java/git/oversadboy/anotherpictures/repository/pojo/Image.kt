package git.oversadboy.anotherpictures.repository.pojo

import android.os.Parcelable
import git.oversadboy.anotherpictures.model.pojo.Links
import git.oversadboy.anotherpictures.model.pojo.Urls
import git.oversadboy.anotherpictures.model.pojo.User
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Image(
    val id: String,
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