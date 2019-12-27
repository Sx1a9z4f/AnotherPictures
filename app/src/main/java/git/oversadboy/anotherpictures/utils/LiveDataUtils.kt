package git.oversadboy.anotherpictures.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, crossinline body: (T) -> Unit) =
    observe(lifecycleOwner, Observer { body(it) })

