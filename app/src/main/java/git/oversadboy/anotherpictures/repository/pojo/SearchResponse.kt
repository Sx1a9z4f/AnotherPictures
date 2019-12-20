package git.oversadboy.anotherpictures.model.pojo

import git.oversadboy.anotherpictures.repository.pojo.Image


data class SearchResponse(
    val total: Int,
    val total_pages: Int,
    val results: List<Image>
)