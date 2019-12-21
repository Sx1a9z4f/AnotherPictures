package git.oversadboy.anotherpictures.model.api

import git.oversadboy.anotherpictures.model.pojo.CollectionPhotos
import git.oversadboy.anotherpictures.model.pojo.SearchResponse
import git.oversadboy.anotherpictures.model.pojo.Token
import git.oversadboy.anotherpictures.model.pojo.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/photos")
    suspend fun getPhotos(@Query("page") page: Int): List<Image>

    @GET("/search/photos")
    fun searchPhotos(@Query("query") query: String, @Query("page") page: Int): Call<SearchResponse>

    @GET("/collections")
    fun getCollections(@Query("page") page: Int): Call<List<CollectionPhotos>>

    @GET("/collections/{id}/photos")
    fun getCollectionPhotos(@Path("id") id: String, @Query("page") page: Int): Call<List<Image>>

    @GET("photos/{id}")
    fun getPhoto(@Path("id") id: String): Call<Image>


    @POST("/oauth/token")
    fun getAccessToken(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("code") code: String,
        @Query("grant_type") grant_type: String
    ): Call<Token>

    @GET("/users/{username}/likes")
    fun getUserLikes(
        @Path("username") username: String,
        @Query("page") page: Int?
    ): Call<List<Image>>
}