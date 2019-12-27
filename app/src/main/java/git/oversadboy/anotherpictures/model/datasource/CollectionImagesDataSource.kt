package git.oversadboy.anotherpictures.model.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionImagesDataSource(private val id: String, private val api: Api) :
    PageKeyedDataSource<Int, Image>() {

    companion object {
        const val START_PAGE = 1
        const val NEXT_PAGE = 2
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        api.getCollectionPhotos(id, START_PAGE).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.isSuccessful)
                    response.body()?.also {
                        callback.onResult(it, null, NEXT_PAGE)
                    }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Log.d("Fail", "onFailure", t)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.getCollectionPhotos(id, params.key).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.isSuccessful) {
                    response.body()?.also {
                        val key = if (params.key > START_PAGE) params.key - 1 else null
                        callback.onResult(it, key)
                    }
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Log.d("Fail", "onFailure", t)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.getCollectionPhotos(id, params.key).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.body() != null)
                    response.body()?.also {
                        callback.onResult(it, params.key + 1)
                    }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Log.d("Fail", "onFailure", t)
            }
        })
    }

}
