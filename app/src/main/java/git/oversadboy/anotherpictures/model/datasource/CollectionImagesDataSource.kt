package git.oversadboy.anotherpictures.model.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

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
        api.getCollectionPhotos(id, START_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Image>>() {
                override fun onSuccess(t: List<Image>) {
                    callback.onResult(t, null, NEXT_PAGE)
                }

                override fun onError(e: Throwable) {
                    Log.d("Error", "onError", e)
                }

            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.getCollectionPhotos(id, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Image>>() {
                override fun onSuccess(t: List<Image>) {
                    val key = if (params.key > START_PAGE) params.key - 1 else null
                    callback.onResult(t, key)
                }

                override fun onError(e: Throwable) {
                    Log.d("Error", "onError", e)
                }

            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Image>) {
        api.getCollectionPhotos(id, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Image>>() {
                override fun onSuccess(t: List<Image>) {
                    callback.onResult(t, params.key + 1)
                }

                override fun onError(e: Throwable) {
                    Log.d("Error", "onError", e)
                }

            })
    }

}
