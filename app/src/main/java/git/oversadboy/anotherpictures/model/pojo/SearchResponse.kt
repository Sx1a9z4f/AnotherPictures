package git.oversadboy.anotherpictures.model.pojo


data class SearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<Photo>
)