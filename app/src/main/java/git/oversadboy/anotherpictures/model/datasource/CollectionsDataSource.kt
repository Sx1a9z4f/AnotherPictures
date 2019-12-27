package git.oversadboy.anotherpictures.model.datasource

import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.CollectionImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CollectionsDataSource(private val api: Api) : PageKeyedDataSource<Int, CollectionImage>() {

    companion object {
        const val START_PAGE = 1
        const val NEXT_PAGE = 2
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CollectionImage>
    ) {
        api.getCollections(START_PAGE).enqueue(object : Callback<List<CollectionImage>> {
            override fun onResponse(
                call: Call<List<CollectionImage>>,
                response: Response<List<CollectionImage>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.also {
                        callback.onResult(it, null, NEXT_PAGE)
                    }
                }
            }

            override fun onFailure(call: Call<List<CollectionImage>>, t: Throwable) {
                //TODO
            }
        })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CollectionImage>
    ) {
        api.getCollections(params.key).enqueue(object : Callback<List<CollectionImage>> {
            override fun onResponse(
                call: Call<List<CollectionImage>>,
                response: Response<List<CollectionImage>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.also {
                        callback.onResult(it, params.key + 1)
                    }
                }
            }

            override fun onFailure(call: Call<List<CollectionImage>>, t: Throwable) {
                //TODO
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CollectionImage>
    ) {
        api.getCollections(params.key).enqueue(object : Callback<List<CollectionImage>> {
            override fun onResponse(
                call: Call<List<CollectionImage>>,
                response: Response<List<CollectionImage>>
            ) {
                if (response.isSuccessful) {
                    val key = if (params.key > START_PAGE) params.key - 1 else null
                    response.body()?.also {
                        callback.onResult(it, key)
                    }
                }
            }

            override fun onFailure(call: Call<List<CollectionImage>>, t: Throwable) {
            }
        })
    }


}