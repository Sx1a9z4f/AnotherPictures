package git.oversadboy.anotherpictures.model.response


data class SearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<Photo>
)