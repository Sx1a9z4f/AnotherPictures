package git.oversadboy.anotherpictures.model.datasourse

import android.util.Log
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.api.Unsplash
import git.oversadboy.anotherpictures.model.response.Photo
import git.oversadboy.anotherpictures.ui.MainActivity.Companion.authToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ImageDataSource : PageKeyedDataSource<Int, Photo>() {


    private val api = Unsplash.getRetrofitPostInstance(authToken).create<Api>(Api::class.java)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        api.getPhotos(Random.nextInt(0, 100)).enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (!response.body().isNullOrEmpty())
                    Log.d("Log", response.body()!![1]?.description)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        api.getPhotos(params.key).enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (!response.body().isNullOrEmpty())
                    Log.d("Log", response.body()!![1]?.description)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        api.getPhotos(params.key).enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (!response.body().isNullOrEmpty())
                    Log.d("Log", response.body()!![1]?.description)
            }
        })
    }
}