package git.oversadboy.anotherpictures.model.datasource

import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.model.pojo.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchDataSource (private val query: String, private val api: Api) :
    PageKeyedDataSource<Int, Image>() {

    companion object {
        const val START_PAGE = 1
        const val NEXT_PAGE = 2
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        api.searchPhotos(query, START_PAGE).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful)
                    callback.onResult(response.body()!!.results, null, NEXT_PAGE)
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.searchPhotos(query, params.key).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val key = if (params.key > START_PAGE) params.key - 1 else null
                    callback.onResult(response.body()!!.results, key)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.searchPhotos(query, params.key).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onResult(response.body()!!.results, params.key + 1)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
            }
        })
    }
}
