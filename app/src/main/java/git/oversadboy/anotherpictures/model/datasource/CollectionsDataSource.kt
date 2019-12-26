package git.oversadboy.anotherpictures.model.datasource

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.CollectionImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CollectionsDataSource(private val api: Api) : PageKeyedDataSource<Int, CollectionImage>() {

    companion object{
        const val START_PAGE = 1
        const val NEXT_PAGE = 2
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CollectionImage>
    ) {
        api.getCollections(START_PAGE).enqueue(object : Callback<PagedList<CollectionImage>> {
            override fun onResponse(
                call: Call<PagedList<CollectionImage>>,
                response: Response<PagedList<CollectionImage>>
            ) {
                if (response.isSuccessful)
                    callback.onResult(response.body()!!, null, NEXT_PAGE)
            }

            override fun onFailure(call: Call<PagedList<CollectionImage>>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CollectionImage>
    ) {
        api.getCollections(params.key).enqueue(object : Callback<PagedList<CollectionImage>> {
            override fun onResponse(
                call: Call<PagedList<CollectionImage>>,
                response: Response<PagedList<CollectionImage>>
            ) {
                if (response.isSuccessful)
                    callback.onResult(response.body()!!, params.key + 1)
            }

            override fun onFailure(call: Call<PagedList<CollectionImage>>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CollectionImage>
    ) {
        api.getCollections(params.key).enqueue(object : Callback<PagedList<CollectionImage>> {
            override fun onResponse(
                call: Call<PagedList<CollectionImage>>,
                response: Response<PagedList<CollectionImage>>
            ) {
                if (response.isSuccessful) {
                    val key = if (params.key > START_PAGE) params.key - 1 else null
                    callback.onResult(response.body()!!, key)
                }
            }

            override fun onFailure(call: Call<PagedList<CollectionImage>>, t: Throwable) {
            }
        })
    }


}