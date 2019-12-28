package git.oversadboy.anotherpictures.model.api

import git.oversadboy.anotherpictures.model.pojo.CollectionImage
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.model.pojo.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/photos")
    fun getPhotos(@Query("page") page: Int): Single<List<Image>>

    @GET("/search/photos")
    fun searchPhotos(@Query("query") query: String, @Query("page") page: Int): Single<SearchResponse>

    @GET("/collections")
    fun getCollections(@Query("page") page: Int): Single<List<CollectionImage>>

    @GET("/collections/{id}/photos")
    fun getCollectionPhotos(@Path("id") id: String, @Query("page") page: Int): Single<List<Image>>

}