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

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        api.getCollectionPhotos(id, 1).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.body() != null) {
                    callback.onResult(response.body()!!, null, 2)
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
                Log.d("mLog", "onFailure: ")
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.getCollectionPhotos(id, params.key).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.body() != null) {
                    val key = if (params.key > 1) params.key - 1 else null
                    callback.onResult(response.body()!!, key)
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.getCollectionPhotos(id, params.key).enqueue(object : Callback<List<Image>> {
            override fun onResponse(call: Call<List<Image>>, response: Response<List<Image>>) {
                if (response.body() != null) {
                    val key = params.key + 1
                    callback.onResult(response.body()!!, key)
                }
            }

            override fun onFailure(call: Call<List<Image>>, t: Throwable) {
            }
        })
    }

}
