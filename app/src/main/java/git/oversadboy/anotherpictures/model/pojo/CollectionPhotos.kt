package git.oversadboy.anotherpictures.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionPhotos(
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var published_at: String? = null,
    var updated_at: String? = null,
    var curated: Boolean? = null,
    var total_photos: Int? = null,
    var private: Boolean? = null,
    var share_key: String? = null,
    var cover_photo: CoverPhoto? = null,
    var user: User? = null
) : Parcelable