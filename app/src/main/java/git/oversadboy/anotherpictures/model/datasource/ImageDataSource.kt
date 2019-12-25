package git.oversadboy.anotherpictures.model.datasource

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageDataSource(val api: Api) : PageKeyedDataSource<Int, Image>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Image>
    ) {
        api.getPhotos(1).enqueue(object : Callback<PagedList<Image>> {
            override fun onResponse(
                call: Call<PagedList<Image>>,
                response: Response<PagedList<Image>>
            ) {
                if (response.isSuccessful)
                    callback.onResult(response.body()!!, null, 2)
            }

            override fun onFailure(call: Call<PagedList<Image>>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Image>
    ) {
        api.getPhotos(params.key).enqueue(object : Callback<PagedList<Image>> {
            override fun onResponse(
                call: Call<PagedList<Image>>,
                response: Response<PagedList<Image>>
            ) {
                if (response.isSuccessful)
                    callback.onResult(response.body()!!, params.key + 1)
            }

            override fun onFailure(call: Call<PagedList<Image>>, t: Throwable) {
            }
        })
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Image>
    ) {

        api.getPhotos(params.key).enqueue(object : Callback<PagedList<Image>> {
            override fun onResponse(
                call: Call<PagedList<Image>>,
                response: Response<PagedList<Image>>
            ) {
                if (response.isSuccessful) {
                    val key = if (params.key > 1) params.key - 1 else null
                    callback.onResult(response.body()!!, key)
                }
            }

            override fun onFailure(call: Call<PagedList<Image>>, t: Throwable) {
            }
        })
    }
}