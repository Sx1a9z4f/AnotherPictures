package git.oversadboy.anotherpictures.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionImage(
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var publishedAt: String? = null,
    var updatedAt: String? = null,
    var curated: Boolean? = null,
    var totalPhotos: Int? = null,
    var private: Boolean? = null,
    var shareKey: String? = null
) : Parcelable
