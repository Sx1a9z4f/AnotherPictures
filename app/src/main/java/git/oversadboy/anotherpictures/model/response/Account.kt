package git.oversadboy.anotherpictures.model.response

data class Account(
    val client_id: Int,
    val redirect_uri: String,
    val response_type: String,
    val scope: String
)
