package git.oversadboy.anotherpictures.utils

import android.util.Log
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

inline fun <T> Single<T>.subscribes(crossinline body: (T) -> Unit) {
    subscribe(object : DisposableSingleObserver<T>() {
        override fun onSuccess(t: T) {
            body(t)
        }

        override fun onError(e: Throwable) {
            Log.d("Error", "onError", e)
        }
    })
}