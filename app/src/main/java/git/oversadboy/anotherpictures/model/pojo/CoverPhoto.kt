package git.oversadboy.anotherpictures.model.pojo


data class CoverPhoto(
    var id: String? = null,
    var width: Int? = null,
    var height: Int? = null,
    var color: String? = null,
    var likes: Int? = null,
    var liked_by_user: Boolean? = null,
    var user: User? = null,
    var urls: Urls? = null
)